package Main;

import java.net.*;
import java.io.*;
import java.util.*;

public class DNSes extends Thread {
    private String ip = null;
    private Socket conexion = null;
    private String leo = null;
    private final int puerto = 1200;
    public boolean asigIP(String ip){
        boolean retorno = false;
        try{
            this.ip = ip;
            retorno = true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return retorno;
    }


    public void online(){
        try{
            conexion = new Socket(ip,puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "ONLINE yimail " + ip;
            flujoDatosSalida.writeUTF(leo); 
            String vuelta = flujoDatosEntrada.readUTF();
            if(vuelta.equals("OK ONLINE miaumail")){
                System.out.println("Se ha conectado al DNS");
                }else if(vuelta.equals("ONLINE ERROR 301")){
                    System.out.println("ip no es válida");
                }  
            conexion.close();
        }catch(Exception e){
            System.out.println("No se puedo crear la conexion");
        }

    }

    public void offline(){
        try{
            conexion = new Socket(ip,puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "OFFLINE yimail";
            flujoDatosSalida.writeUTF(leo); 
            String vueltaoff = flujoDatosEntrada.readUTF();
            if(vueltaoff.equals("OFFLINE ERROR 302")){
            System.out.println("Error offline dns");
            }else if(vueltaoff.equals("OK OFFLINE miaumail")){
                System.out.println("Desconectado del dns");
            }
            conexion.close();
        }catch(Exception e){
            System.out.println("No se puedo crear la conexion");
        }
    }

    public void GETIPTABLE() {
        try {
            DB myDb2 = new DB("servidorcorreo.db");
            conexion = new Socket(ip, puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "GETIPTABLE";
            flujoDatosSalida.writeUTF(leo);

            String ips = flujoDatosEntrada.readUTF();
            String[] valores = ips.split(" ");

            try {
                if (!myDb2.connect()) {
                    System.out.println("ERROR DB CONNECT");
                } else {
                    if (valores.length == 5 && valores[0].equals("OK") && valores[1].equals("IPTABLE") && valores[4].equals("*")) {
                        String query_delete = "delete from servidor where lower(nombre) != 'yimail';";
                        boolean respuesta_delete = myDb2.executeNonQuery(query_delete);
                        if (respuesta_delete) {
                            if (!valores[2].equals("yimail") || !valores[2].equals("Yimail")) {
                                String query_insert_newcont = "insert into contacto (idusuariocontacto,ip,estado)";
                                query_insert_newcont += "values(" + valores[2] + "," + valores[3]
                                        + ",1);";
                                boolean respuesta_7 = myDb2.executeNonQuery(query_insert_newcont);
                                if (respuesta_7) {
                                    System.out.println("IPS OBTENIDA");
                                }
                            }
                        }
                    } else if (valores.length == 4 && valores[0].equals("OK") && valores[1].equals("IPTABLE")) {
                        boolean bandera = true;

                        String query_delete = "delete from servidor where lower(nombre) != 'yimail';";
                        boolean respuesta_delete = myDb2.executeNonQuery(query_delete);
                        String query_insert_newcont = "insert into contacto (idusuariocontacto,ip,estado)";
                        query_insert_newcont += "values(" + valores[2] + "," + valores[3] + ",1);";
                        boolean respuesta_7 = myDb2.executeNonQuery(query_insert_newcont);
                        if (respuesta_delete) {
                            while (bandera) {
                                String ips2 = flujoDatosEntrada.readUTF();
                                String[] valores2 = ips2.split(" ");
                                if (valores2.length == 5 && valores2[0].equals("OK") && valores2[1].equals("IPTABLE") && valores2[4].equals("*")) {
                                        if (!valores[2].equals("yimail") || !valores[2].equals("Yimail")) {
                                            String query_insert_newcont2 = "insert into contacto (idusuariocontacto,ip,estado)";
                                            query_insert_newcont2 += "values(" + valores[2] + "," + valores[3]
                                                    + ",1);";
                                            boolean respuesta_72 = myDb2.executeNonQuery(query_insert_newcont2);
                                            if (respuesta_72) {
                                                System.out.println("IPS OBTENIDAS");
                                            }
                                            bandera = false;
                                        }
                                } else if (valores.length == 4 && valores[0].equals("OK") && valores[1].equals("IPTABLE")) {
                                    String query_insert_newcont2 = "insert into contacto (idusuariocontacto,ip,estado)";
                                    query_insert_newcont2 += "values(" + valores[2] + "," + valores[3] + ",1);";
                                    boolean respuesta_72 = myDb2.executeNonQuery(query_insert_newcont2);                                    
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("NOOP ERROR UNKNOWN");
            } finally {
                myDb2.close();
            }

            conexion.close();
        } catch (Exception e) {
            System.out.println("No se puedo crear la conexion");
        }
    }


    public String checkContact(String verify){
        String retorno = null;
        try{
            conexion = new Socket(ip,puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "verify";
            flujoDatosSalida.writeUTF(leo); 
            retorno = flujoDatosEntrada.readUTF();
            conexion.close();
        }catch(Exception e){
            System.out.println("No se puedo crear la conexion");
            retorno = "CHECK ERROR DNS_FAIL";
        }
        return retorno;
    }

    TimerTask timerTask = new TimerTask(){ 
        public void run()  
        { 
            GETIPTABLE();
        } 
    }; 

    public void run() {
        Timer timer = new Timer();
        online();
        timer.scheduleAtFixedRate(timerTask, 0, 20000); 
        System.out.println("llega a servidor");
    }
}
