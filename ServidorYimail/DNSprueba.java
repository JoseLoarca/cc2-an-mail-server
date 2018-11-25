import java.net.*;
import java.io.*;

public class DNSprueba {
	private final int puerto1 = 1200;
	private ServerSocket cs1;
    private Socket sc1;
    String ingreso = null;
    public void inicia(){
    	try{
            cs1 = new ServerSocket(puerto1);
            sc1 = new Socket();
            System.out.println("Esperando conexion");
            while(true){
	            sc1 = cs1.accept();
	            DataOutputStream salida = new DataOutputStream(sc1.getOutputStream());
	            DataInputStream entrada = new DataInputStream(sc1.getInputStream());

                ingreso = entrada.readUTF();
                String[] valores = ingreso.split(" ");
                String accion = valores[0].trim();
                switch(accion){
                	case "ONLINE":
                		salida.writeUTF("OK ONLINE servername");
                	break;
                	case "OFFLINE":
                		salida.writeUTF("OK OFFLINE servername");
                	break;
                	case "GETIPTABLE":
                		salida.writeUTF("OK IPTABLE servername ip *");
                	break;
                }
	        }
        }catch(Exception e){
            System.out.println("Ocurrio un error inesperado en N/");
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
       DNSprueba io = new DNSprueba();
       io.inicia();
    }
}