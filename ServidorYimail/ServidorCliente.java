/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jair
 */

import java.net.*;
import java.io.*;

public class ServidorCliente extends Thread{
    Socket cliente = null;
    //InetAddress a = null;
    SocketAddress a = null;
    String ingreso = null;

    public ServidorCliente(Socket cliente){
        this.cliente = cliente;
        try{
            a = this.cliente.getRemoteSocketAddress();
        }catch(Exception e){
            System.out.println(e.getMessage());
            a = null;
        }
    }
    
    public void deServ(DataOutputStream salida,String valor){
        try{
            salida.writeUTF("SERVER : " + valor);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        try{
            DataOutputStream salida = new DataOutputStream(this.cliente.getOutputStream());
            System.out.print("conectado al servidor, direccion: ");
            System.out.println(a);
            DataInputStream entrada = new DataInputStream(this.cliente.getInputStream());
            String ingreso = null;
            DB myDb = new DB("servidorcorreo.db");
            do{
                ingreso = entrada.readUTF();
                String[] valores = ingreso.split(" ");
                String accion = valores[0].trim();
                switch (accion) {
                case "VERIFYSERVER":
                    /*------------------------------------------------------------------------*/
                    String user_caption = "";
                    String servidor_caption = "";
                    if (valores.length == 3) {
                        user_caption = valores[1].trim();
                        servidor_caption = valores[2].trim();
                    }else{
                        deServ(salida,"Parametros enviados incorrectos");
                    }
                    if (!myDb.connect()) {
                        deServ(salida,"ERROR_DB_CONECTIONS");
                    } else {
                        try {
                            String query = "select count(*) as existe from servidor ";
                            query += "where nombre = '" + servidor_caption + "';";
                            myDb.executeQuery(query, "rs1");
                            while (myDb.next("rs1")) {
                                System.out.println()
                                String respuesta = myDb.getString("existe", "rs1") + "";
                                if (Integer.parseInt(respuesta) >= 1) {
                                    deServ(salida,"OK SERVER VERIFICATE");
                                }else{
                                    deServ(salida,"ERROR NOT SERVER FOUND");   
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        } finally {
                            myDb.close();
                        }
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "LOGIN":
                    /*------------------------------------------------------------------------*/
                    String user_caption_log = "";
                    String pasword_caption_log = "";
                    if (valores.length == 3) {
                        user_caption_log = valores[1].trim();
                        pasword_caption_log = valores[2].trim();
                    }else{
                        deServ(salida,"Parametros enviados incorrectos");
                    }
                    if (!myDb.connect()) {
                        deServ(salida,"ERROR_DB_CONECTIONS");
                    } else {
                        try {
                            String query = "select count(*) as existe from usuario ";
                            query += "where idusuario = '" + user_caption_log + "';";
                            myDb.executeQuery(query, "rs1");
                            while (myDb.next("rs1")) {
                                String respuesta = myDb.getString("existe", "rs1") + "";
                                if (Integer.parseInt(respuesta) >= 1) {
                                    String query2 = "select count(*) as existe from usuario ";
                                    query2 += "where idusuario = '" + user_caption_log + "' and ";
                                    query2 += " password = '" + pasword_caption_log + "';";
                                    myDb.executeQuery(query2, "rsl2");
                                    while (myDb.next("rsl2")) {
                                        String respuesta_2 = myDb.getString("existe", "rsl2") + "";
                                        if (Integer.parseInt(respuesta_2) >= 1) {
                                            boolean respuesta_3 = myDb.executeNonQuery(
                                                    "update usuario set estado=1 where idusuario='" + user_caption_log
                                                            + "' and password='" + pasword_caption_log + "';");
                                            if (respuesta_3) {
                                                deServ(salida,"OK LOGIN");
                                            } else {
                                                deServ(salida,"LOGIN ERROR UNKNOWN");
                                            }
                                        } else {
                                            deServ(salida,"LOGIN ERROR 102");
                                        }
                                    }
                                } else {
                                    deServ(salida,"LOGIN ERROR 101");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            deServ(salida,"LOGIN ERROR UNKNOWN");
                        } finally {
                            myDb.close();
                        }
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "CLIST":
                    /*------------------------------------------------------------------------*/
                    String user_caption_log_list = "";
                    if (valores.length == 2) {
                        user_caption_log_list = valores[1].trim();
                    }else{
                        deServ(salida,"Parametros enviados incorrectos");
                    }
                    if (!myDb.connect()) {
                        deServ(salida,"ERROR_DB_CONECTIONS");
                    } else {
                        try {
                            String query = "select count(*) as cantidad_contactos from contacto ";
                            query += "where idusuario = '" + user_caption_log_list + "';";
                            myDb.executeQuery(query, "rsl_cantidad");
                            while (myDb.next("rsl_cantidad")) {
                                String respuesta_3 = myDb.getString("cantidad_contactos", "rsl_cantidad") + "";
                                if (Integer.parseInt(respuesta_3) > 0) {
                                    String query2 = "select * from contacto ";
                                    query2 += "where idusuario = '" + user_caption_log_list + "';";
                                    myDb.executeQuery(query2, "rsl_contactos");
                                    Integer cont_aux = 1;
                                    while (myDb.next("rsl_cantidad")) {
                                        if (cont_aux.equals(Integer.parseInt(respuesta_3))) {

                                            deServ(salida,"OK CLIST " + myDb.getString("idusuario", "rsl_contactos")
                                                    + "@" + myDb.getString("servidor", "rsl_contactos") + " *");
                                        } else {
                                            deServ(salida,"OK CLIST " + myDb.getString("idusuario", "rsl_contactos")
                                                    + "@" + myDb.getString("servidor", "rsl_contactos"));
                                        }
                                        cont_aux++;
                                    }
                                } else {
                                    deServ(salida,"CLIST ERROR 103");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            deServ(salida,"CLIST ERROR UNKNOWN");
                        } finally {
                            myDb.close();
                        }
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "GETNEWMAILS":
                    /*------------------------------------------------------------------------*/
                    String user_caption_log_mails = "";
                    if (valores.length == 2) {
                        user_caption_log_mails = valores[1].trim();
                    }else{
                        deServ(salida,"Parametros enviados incorrectos");
                    }
                    if (!myDb.connect()) {
                        deServ(salida,"ERROR_DB_CONECTIONS");
                    } else {
                        try {
                            String query = "select count(*) as cantidad_correos from correo ";
                            query += "where idusuarioremitente = '" + user_caption_log_mails + "';";
                            myDb.executeQuery(query, "rsl_cantidad");
                            while (myDb.next("rsl_cantidad")) {
                                String respuesta_3 = myDb.getString("cantidad_correos", "rsl_cantidad") + "";
                                if (Integer.parseInt(respuesta_3) > 0) {
                                    String query2 = "select correo.idusuarioremitente as sender, correo.asunto as asunto, correo.cuerpo as cuerpo"; 
                                    query2 += " from correo, destinatario where correo.idcorreo = destinatario.idcorreo ";
                                    query2 += "and destinatario.idusuarioreceptor = '" + user_caption_log_mails + "';";
                                    myDb.executeQuery(query2, "rsl_newmails");
                                    Integer cont_aux = 1;
                                    while (myDb.next("rsl_newmails")) {
                                        if (cont_aux.equals(Integer.parseInt(respuesta_3))) {
                                            deServ(salida,"OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails")
                                                    + " '" + myDb.getString("asunto", "rsl_newmails")
                                                    + "' '" + myDb.getString("cuerpo", "rsl_newmails") + "' *");
                                        } else {
                                            deServ(salida,"OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails")
                                                    + " '" + myDb.getString("asunto", "rsl_newmails")
                                                    + "' '" + myDb.getString("cuerpo", "rsl_newmails") + "'");
                                        }
                                        cont_aux++;
                                    }
                                } else {
                                    deServ(salida,"OK GETNEWMAILS NOMAILS");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            deServ(salida,"GETNEWMAILS ERROR UNKNOWN");
                        } finally {
                            myDb.close();
                        }
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "EXIT":
                    /*------------------------------------------------------------------------*/
                    ingreso = accion;
                    deServ(salida,"Finalisando conexion");
                    myDb.close();
                    /*------------------------------------------------------------------------*/
                    break;
                default:
                    deServ(salida,"Comando enviado incorrecto");
                    break;
                }
            }while(!ingreso.equals("EXIT"));
        }catch(Exception e){
            System.out.println("Ocurrio un error inesperado");
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("cerrando..");
            cliente.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
