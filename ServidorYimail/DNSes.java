import java.net.*;
import java.io.*;
import java.util.*;

public class DNSes extends Thread {
    private String ip = null;
    private Socket conexion = null;
    private String leo = null;
    private final int puerto = 1200;
    HashMap<String,String> tablaIP = new HashMap<>();
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

    public void GETIPTABLE(){
        try{
            conexion = new Socket(ip,puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "GETIPTABLE";
            flujoDatosSalida.writeUTF(leo); 
            String datos = flujoDatosEntrada.readUTF();
            while(datos.startsWith("OK IPTABLE")){
                            if(!datos.endsWith("*")){
                            String servername = datos.substring(12,datos.indexOf(" ",12));
                            String ip = datos.substring(datos.indexOf(" ",12)+1);
                            
                            tablaIP.put(servername, ip);
                            datos = flujoDatosEntrada.readLine();
                            
                            }else if (datos.endsWith("*")){
                                String servername = datos.substring(11,datos.indexOf(" ",12));
                String ip = datos.substring(datos.indexOf(" ",12)+1,datos.indexOf("*")-1);
                                tablaIP.put(servername, ip);
                            }
                        }
                        if(datos.equals("GETIPTABLE ERROR 303")){
                            System.out.println("No hay servidores en el DNS");
                            tablaIP = null;
                        }
            conexion.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static HashMap getDns(){
        return tablaIP;
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