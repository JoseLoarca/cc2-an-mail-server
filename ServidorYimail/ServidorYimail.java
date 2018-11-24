import java.io.*;
import java.net.*;

public class ServidorYimail {
    private final int puerto1 = 1400;
//    private final int puerto2 = 1500;
    private ServerSocket cs1;
    private Socket sc1;
//    private ServerSocket cs2;
//    private Socket sc2;
    private DataOutputStream salida;
    public void inicia(){
        DataInputStream entrada;
        try{
            cs1 = new ServerSocket(puerto1);
            sc1 = new Socket();
            System.out.println("Esperando conexion");
            while(true){
	            sc1 = cs1.accept();
	            ServidorCliente cliente = new ServidorCliente(sc1);
	            cliente.start();
	        }
        }catch(Exception e){
            System.out.println("Ocurrio un error inesperado en N/");
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
       ServidorYimail io = new ServidorYimail();
       io.inicia();
    }
    
}
