import java.net.*;
import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;
import java.util.Scanner;

public class ServidorCliente extends Thread {
    Socket cliente = null;
    // InetAddress a = null;
    SocketAddress a = null;
    String ingreso = null;
    String usuario_actual = null;
    String id_usuario_actual = null;

    public ServidorCliente(Socket cliente) {
        this.cliente = cliente;
        try {
            a = this.cliente.getRemoteSocketAddress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            a = null;
        }
    }

    public void deServ(DataOutputStream salida, String valor) {
        try {
            salida.writeUTF("SERVER : " + valor);
            System.out.println("SERVER : " + valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            DataOutputStream salida = new DataOutputStream(this.cliente.getOutputStream());
            System.out.print("conectado al servidor, direccion: ");
            System.out.println(a);
            DataInputStream entrada = new DataInputStream(this.cliente.getInputStream());
            String ingreso = null;
            DB myDb = new DB("servidorcorreo.db");
            do {
                ingreso = entrada.readUTF();                
                System.out.println("CLIENT : " + ingreso);
                String[] valores = ingreso.split(" ");
                String accion = valores[0].trim();
                switch (accion) {
                case "VERIFYUSRSERVER":
                    /*------------------------------------------------------------------------*/
                    String user_caption = "";
                    String servidor_caption = "";
                    if (valores.length == 3) {
                        user_caption = valores[1].trim();
                        servidor_caption = valores[2].trim();
                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                if (servidor_caption.equals("yimail")) {
                                    String query = "select count(*) as existe from servidor ";
                                    query += "where lower(nombre) = '" + servidor_caption + "';";
                                    String query2 = "select count(*) as existe from usuario ";
                                    query2 += "where correo = '" + user_caption + "';";
                                    myDb.executeQuery(query, "rs1");
                                    myDb.executeQuery(query2, "rs2");
                                    String respuesta = "";
                                    String respuesta2 = "";
                                    while (myDb.next("rs1")) {
                                        respuesta = myDb.getString("existe", "rs1") + "";
                                    }
                                    while (myDb.next("rs2")) {
                                        respuesta2 = myDb.getString("existe", "rs2") + "";
                                    }
                                    if (Integer.parseInt(respuesta) >= 1 && Integer.parseInt(respuesta2) >= 1) {
                                        usuario_actual = valores[1].trim();
                                        deServ(salida, "OK SERVER VERIFICATE");
                                    } else {
                                        deServ(salida, "ERROR NOT SERVER FOUND");
                                    }
                                }
                                //agregar la consulta a otro servidor
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
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

                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String query = "select count(*) as existe from usuario ";
                                query += "where correo = '" + user_caption_log + "' and ";
                                query += "password = '" + pasword_caption_log + "';";
                                myDb.executeQuery(query, "rs1");
                                String respuesta = "";
                                while (myDb.next("rs1")) {
                                    respuesta = myDb.getString("existe", "rs1") + "";
                                }
                                if (Integer.parseInt(respuesta) >= 1) {
                                    String query2 = "select count(*) as existe from usuario ";
                                    query2 += "where correo = '" + user_caption_log + "' and ";
                                    query2 += " password = '" + pasword_caption_log + "';";
                                    myDb.executeQuery(query2, "rsl2");
                                    String respuesta_2 = "";
                                    while (myDb.next("rsl2")) {
                                        respuesta_2 = myDb.getString("existe", "rsl2") + "";
                                    }
                                    if (Integer.parseInt(respuesta_2) >= 1) {
                                        String query3 = "update usuario set estado='1' where correo='"
                                                + user_caption_log + "' and password='" + pasword_caption_log + "';";
                                        boolean respuesta_3 = myDb.executeNonQuery(query3);
                                        if (respuesta_3) {
                                            deServ(salida, "OK LOGIN");
                                        } else {
                                            deServ(salida, "LOGIN ERROR UNKNOWN");
                                        }
                                    } else {
                                        deServ(salida, "LOGIN ERROR 102");
                                    }
                                } else {
                                    deServ(salida, "LOGIN ERROR 101");
                                }

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "LOGIN ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "CLIST":
                    /*------------------------------------------------------------------------*/
                    String user_caption_log_list = "";
                    if (valores.length == 2) {
                        user_caption_log_list = valores[1].trim();

                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String query3 = "";
                                String query = "select count(*) as cantidad_contactos from contacto ";
                                query += "where idusuariopersonal = '" + user_caption_log_list + "';";
                                myDb.executeQuery(query, "rsl_cantidad");
                                String respuesta_3 = "";
                                while (myDb.next("rsl_cantidad")) {
                                    respuesta_3 = myDb.getString("cantidad_contactos", "rsl_cantidad") + "";
                                }
                                if (Integer.parseInt(respuesta_3) > 0) {
                                    String query2 = "select usuario.correo as user_correo, servidor.nombre as nombre_server  from contacto ";
                                    query2 += "inner join usuario, servidor";
                                    query2 += " on contacto.idusuariocontacto = usuario.idusuario ";
                                    query2 += "and contacto.idservidor = servidor.idservidor ";
                                    query2 += "and contacto.idusuariopersonal = '" + user_caption_log_list + "';";
                                    myDb.executeQuery(query2, "rsl_contactos");
                                    Integer cont_aux = 1;
                                    while (myDb.next("rsl_contactos")) {
                                        if (cont_aux.equals(Integer.parseInt(respuesta_3))) {
                                            deServ(salida, "OK CLIST " + myDb.getString("user_correo", "rsl_contactos")
                                                    + "@" + myDb.getString("nombre_server", "rsl_contactos") + " *");
                                        } else {
                                            deServ(salida, "OK CLIST " + myDb.getString("user_correo", "rsl_contactos")
                                                    + "@" + myDb.getString("nombre_server", "rsl_contactos"));
                                        }
                                        cont_aux++;
                                    }
                                } else {
                                    deServ(salida, "CLIST ERROR 103");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "CLIST ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "GETNEWMAILS":
                    /*------------------------------------------------------------------------*/
                    String user_caption_log_mails = "";
                    if (valores.length == 2) {
                        user_caption_log_mails = valores[1].trim();
                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String query = "select count(*) as cantidad_correos from destinatario ";
                                query += "where idusuarioreceptor = '" + user_caption_log_mails + "';";
                                myDb.executeQuery(query, "rsl_cantidad");
                                String respuesta_3 = "";
                                while (myDb.next("rsl_cantidad")) {
                                    respuesta_3 = myDb.getString("cantidad_correos", "rsl_cantidad") + "";
                                }
                                if (Integer.parseInt(respuesta_3) > 0) {
                                    String query2 = "select usuario.correo || '@' || servidor.nombre as sender, correo.asunto as asunto, correo.cuerpo as cuerpo";
                                    query2 += " from correo";
                                    query2 += " inner join destinatario, servidor, usuario";
                                    query2 += " on correo.idcorreo = destinatario.idcorreo";
                                    query2 += " and correo.idservidor = servidor.idservidor";
                                    query2 += " and correo.idusuarioremitente = usuario.idusuario";
                                    query2 += " and destinatario.idusuarioreceptor = '" + user_caption_log_mails + "';";
                                    myDb.executeQuery(query2, "rsl_newmails");
                                    Integer cont_aux = 1;
                                    while (myDb.next("rsl_newmails")) {
                                        if (cont_aux.equals(Integer.parseInt(respuesta_3))) {
                                            deServ(salida,
                                                    "OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails") + " '"
                                                            + myDb.getString("asunto", "rsl_newmails") + "' '"
                                                            + myDb.getString("cuerpo", "rsl_newmails") + "' *");
                                        } else {
                                            deServ(salida,
                                                    "OK GETNEWMAILS " + myDb.getString("sender", "rsl_newmails") + " '"
                                                            + myDb.getString("asunto", "rsl_newmails") + "' '"
                                                            + myDb.getString("cuerpo", "rsl_newmails") + "'");
                                        }
                                        cont_aux++;
                                    }
                                } else {
                                    deServ(salida, "OK GETNEWMAILS NOMAILS");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "GETNEWMAILS ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "GETID":
                    /*------------------------------------------------------------------------*/
                    String user_caption_id = "";
                    String password_caption_id = "";
                    if (valores.length == 3) {
                        user_caption_id = valores[1].trim();
                        password_caption_id = valores[2].trim();

                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String query = "select count(*) as cantidad_usuario from usuario ";
                                query += "where correo = '" + user_caption_id + "' and ";
                                query += " password = '" + password_caption_id + "';";
                                myDb.executeQuery(query, "rsl_cantidad");
                                String respuesta_3 = "";
                                while (myDb.next("rsl_cantidad")) {
                                    respuesta_3 = myDb.getString("cantidad_usuario", "rsl_cantidad") + "";
                                }
                                if (Integer.parseInt(respuesta_3) > 0) {
                                    String query2 = "select * from usuario ";
                                    query2 += "where correo = '" + user_caption_id + "' and ";
                                    query2 += " password = '" + password_caption_id + "';";
                                    myDb.executeQuery(query2, "rsl_data");
                                    while (myDb.next("rsl_data")) {
                                        String result_1 = "" + myDb.getString("idusuario", "rsl_data");
                                        id_usuario_actual = result_1;
                                        deServ(salida, result_1);
                                        String result_2 = "" + myDb.getString("nombre", "rsl_data");
                                        deServ(salida, result_2);
                                    }
                                } else {
                                    deServ(salida, "GETID NOT FOUND");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "GETID ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "LOGOUT":
                    /*------------------------------------------------------------------------*/
                    String user_caption_logout = "";
                    if (valores.length == 2) {
                        user_caption_logout = valores[1].trim();

                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String query3 = "update usuario set estado='0' where idusuario='" + user_caption_logout
                                        + "';";
                                boolean respuesta_3 = myDb.executeNonQuery(query3);
                                if (respuesta_3) {
                                    deServ(salida, "OK LOGOUT");
                                } else {
                                    deServ(salida, "LOGOUT ERROR UNKNOWN");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "LOGOUT ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }

                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "NEWCONT":
                    /*------------------------------------------------------------------------*/
                    String correo_caption = "";
                    if (valores.length == 2) {
                        correo_caption = valores[1].trim();
                        if (!myDb.connect()) {
                            deServ(salida, "ERROR_DB_CONECTIONS");
                        } else {
                            try {
                                String[] parts = correo_caption.split("@");
                                if (parts[1].equals("yimail")) {
                                    String query_string = "select count(*) as existe from usuario";
                                    query_string += " where correo = '" + parts[0] + "';";
                                    myDb.executeQuery(query_string, "rsl_existe");
                                    String respuesta_3 = "";
                                    while (myDb.next("rsl_existe")) {
                                        respuesta_3 = myDb.getString("existe", "rsl_existe") + "";
                                    }
                                    if (Integer.parseInt(respuesta_3) > 0) {
                                        String query_string_2 = "select count(*) as existe from contacto";
                                        query_string_2 += " inner join usuario";
                                        query_string_2 += " on contacto.idusuariocontacto = usuario.idusuario";
                                        query_string_2 += " and contacto.idusuariopersonal = '" + id_usuario_actual
                                                + "';";
                                        myDb.executeQuery(query_string_2, "rsl_existe_3");
                                        String respuesta_5 = "";
                                        while (myDb.next("rsl_existe_3")) {
                                            respuesta_5 = myDb.getString("existe", "rsl_existe_3") + "";
                                        }
                                        if (Integer.parseInt(respuesta_5) <= 0) {
                                            String query_obtener_idcontacto = "select idusuario from usuario";
                                            query_obtener_idcontacto += " where correo = '" + parts[0] + "';";
                                            myDb.executeQuery(query_obtener_idcontacto, "rsl_idusuario_contact");
                                            String respuesta_6 = "";
                                            while (myDb.next("rsl_idusuario_contact")) {
                                                respuesta_6 = myDb.getString("idusuario", "rsl_idusuario_contact") + "";
                                            }

                                            String query_insert_newcont = "insert into contacto (idusuariopersonal,idusuariocontacto,idservidor)";
                                            query_insert_newcont += "values(" + id_usuario_actual + "," + respuesta_6
                                                    + ",1);";
                                            boolean respuesta_7 = myDb.executeNonQuery(query_insert_newcont);
                                            if (respuesta_7) {
                                                deServ(salida, "OK NEWCONT " + correo_caption);
                                            }
                                        } else {
                                            deServ(salida, "OK NEWCONT " + correo_caption);
                                        }
                                    } else {
                                        deServ(salida, "NEWCONT ERROR 109 " + correo_caption);
                                    }
                                } else {
                                    String query_string_two = "select count(*) as existe from servidor";
                                    query_string_two += " where lower(nombre) = '" + parts[1] + "';";
                                    myDb.executeQuery(query_string_two, "rsl_existe_2");
                                    String respuesta_4 = "";
                                    while (myDb.next("rsl_existe_2")) {
                                        respuesta_4 = myDb.getString("existe", "rsl_existe_2") + "";
                                    }
                                    if (Integer.parseInt(respuesta_4) == 0) {
                                        deServ(salida, "NEWCONT ERROR 110 " + correo_caption);
                                    } else {
                                        deServ(salida, "OK NEWCONT " + correo_caption);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                deServ(salida, "NEWCONT ERROR UNKNOWN");
                            } finally {
                                myDb.close();
                            }
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    /*------------------------------------------------------------------------*/
                    break;
                case "SEND":
                    String send_instrunction = "";
                    if (valores.length == 2) {
                        send_instrunction = valores[1].trim();
                        if (send_instrunction.equals("MAIL")) {
                            String ingreso_segunda_cadena = entrada.readUTF();
                            String[] valores_segunda_cadena = ingreso_segunda_cadena.split(" ");
                            if (valores_segunda_cadena.length == 4) {
                                if (valores_segunda_cadena[0].equals("MAIL") && valores_segunda_cadena[1].equals("TO")
                                        && valores_segunda_cadena[4].equals("*")) {
                                            String[] correo_local = valores_segunda_cadena[2].split("@");
                                            if (correo_local[1].equals("yimail")){

                                            } else {

                                            }
                                } else {
                                    deServ(salida, "SEND ERROR 106");
                                }
                            }
                        } else {
                            deServ(salida, "INVALID COMMAND ERROR");
                        }
                    } else {
                        deServ(salida, "INVALID COMMAND ERROR");
                    }
                    break;
                case "EXIT":
                    /*------------------------------------------------------------------------*/
                    ingreso = accion;
                    deServ(salida, "Finalizando conexion");
                    myDb.close();
                    /*------------------------------------------------------------------------*/
                    break;
                default:
                    deServ(salida, "INVALID COMMAND ERROR");
                    break;
                }
            } while (!ingreso.equals("EXIT"));
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado");
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("cerrando..");
            cliente.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
