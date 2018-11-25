import java.net.*;
import java.io.*;

public class ServerToServer extends Thread {
	Socket server = null;
    // InetAddress a = null;
    SocketAddress a = null;
    String ingreso = null;

	public ServerToServer(Socket server) {
        this.server = server;
        try {
            a = this.server.getRemoteSocketAddress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            a = null;
        }
    }

    public void online(){
	private String ip = "localhost";
	private Socket conexion = null;
	private int PUERTO = 1200;
		try{
			conexion = new Socket(ip,PUERTO);
			DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
			DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
			private String leo = "ONLINE yimail " + ip;
			flujoDatosSalida.writeUTF(leo); 
			System.out.println("Eco: " + flujoDatosEntrada.readUTF());
			conexion.close();
		}catch(Exception e){
			System.out.println("No se puedo crear la conexion");
		}
    }

    public void offline(){
	private String ip = "localhost";
	private Socket conexion = null;
	private int PUERTO = 1200;
		try{
			conexion = new Socket(ip,PUERTO);
			DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
			DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
			private String leo = "ONLINE yimail ";
			flujoDatosSalida.writeUTF(leo); 
			System.out.println("Eco: " + flujoDatosEntrada.readUTF());
			conexion.close();
		}catch(Exception e){
			System.out.println("No se puedo crear la conexion");
		}
    }

    public void GETIPTABLE(){
	private String ip = "localhost";
	private Socket conexion = null;
	private int PUERTO = 1200;
		try{
			conexion = new Socket(ip,PUERTO);
			DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
			DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
			private String leo = "GETIPTABLE";
			flujoDatosSalida.writeUTF(leo); 
			System.out.println("Eco: " + flujoDatosEntrada.readUTF());
			conexion.close();
		}catch(Exception e){
			System.out.println("No se puedo crear la conexion");
		}
    }

	public void run() {
		online();
		System.out.println(a);
		System.out.println("llega a servidor");
	}
}