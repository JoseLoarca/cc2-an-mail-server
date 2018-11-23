import java.util.*;
import java.io.*;

public class ServidorYimail {

    // sirve para printear lo que devuelva el server
    public static void clientServer(String print) throws Exception {
        System.out.print("SERVER : ");
        System.out.println(print);
    }

    public static void main(String[] args) throws Exception {
        String finish = "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DB myDb = new DB("servidorcorreo.db");

        do {
            System.out.print("CLIENT : ");
            String informacion = buffer.readLine();
            String[] valores = informacion.split(" ");
            String accion = valores[0].trim();
            switch (accion) {
            case "VERIFYSERVER":
                /*------------------------------------------------------------------------*/
                String user_caption = "";
                String servidor_caption = "";
                if (valores.length == 3) {
                    user_caption = valores[1].trim();
                    servidor_caption = valores[2].trim();
                }
                if (!myDb.connect()) {
                    clientServer("ERROR_DB_CONECTIONS");
                } else {
                    try {

                        String query = "select count(*) as existe from servidor ";
                        query += "where nombre = '" + servidor_caption + "';";
                        myDb.executeQuery(query, "rs1");
                        while (myDb.next("rs1")) {
                            String respuesta = myDb.getString("existe", "rs1") + "";
                            if (Integer.parseInt(respuesta) >= 1) {
                                System.out.println("OK SERVER VERIFICATE");
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
                }
                if (!myDb.connect()) {
                    clientServer("ERROR_DB_CONECTIONS");
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
                                            System.out.println("OK LOGIN");
                                        } else {
                                            System.out.println("LOGIN ERROR UNKNOWN");
                                        }
                                    } else {
                                        System.out.println("LOGIN ERROR 102");
                                    }
                                }
                            } else {
                                System.out.println("LOGIN ERROR 101");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("LOGIN ERROR UNKNOWN");
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
                }
                if (!myDb.connect()) {
                    clientServer("ERROR_DB_CONECTIONS");
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

                                        System.out.println("OK CLIST " + myDb.getString("idusuario", "rsl_contactos")
                                                + "@" + myDb.getString("servidor", "rsl_contactos") + " *");
                                    } else {
                                        System.out.println("OK CLIST " + myDb.getString("idusuario", "rsl_contactos")
                                                + "@" + myDb.getString("servidor", "rsl_contactos"));
                                    }
                                    cont_aux++;
                                }
                            } else {
                                System.out.println("CLIST ERROR 103");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("CLIST ERROR UNKNOWN");
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
                }
                if (!myDb.connect()) {
                    clientServer("ERROR_DB_CONECTIONS");
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
                                        System.out.println("OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails")
                                                + " '" + myDb.getString("asunto", "rsl_newmails")
                                                + "' '" + myDb.getString("cuerpo", "rsl_newmails") + "' *");
                                    } else {
                                        System.out.println("OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails")
                                                + " '" + myDb.getString("asunto", "rsl_newmails")
                                                + "' '" + myDb.getString("cuerpo", "rsl_newmails") + "'");
                                    }
                                    cont_aux++;
                                }
                            } else {
                                System.out.println("OK GETNEWMAILS NOMAILS");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("GETNEWMAILS ERROR UNKNOWN");
                    } finally {
                        myDb.close();
                    }
                }
                /*------------------------------------------------------------------------*/
                break;
            case "EXIT":
                /*------------------------------------------------------------------------*/
                finish = accion;
                System.out.println("Saliendo...");
                myDb.close();
                /*------------------------------------------------------------------------*/
                break;
            default:
                System.out.println("");
                break;
            }
        } while (!finish.equals("EXIT"));

    }
}