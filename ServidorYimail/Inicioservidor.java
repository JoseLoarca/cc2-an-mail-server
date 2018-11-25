import java.net.*;
import java.io.*;

public class Inicioservidor extends Thread {
	private final int puerto1 = 1500;
	private ServerSocket cs1;
    private Socket sc1;
    public void run(){
    	try{
            cs1 = new ServerSocket(puerto1);
            sc1 = new Socket();
            System.out.println("Esperando conexion de servidor");
            while(true){
	            sc1 = cs1.accept();
	            ServerToServer server = new ServerToServer();
	            server.start();
	        }
        }catch(Exception e){
            System.out.println("Ocurrio un error inesperado en N/");
            System.out.println(e.getMessage());
        }
    }
}