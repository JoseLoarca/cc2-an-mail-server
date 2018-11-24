import java.io.*;
import java.net.*;

class Cliente{

	public static void main(String[] args){
	BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	String leo;
	 String ip = "localhost";
	 Socket conexion = null;
	 int PUERTO = 1400;

		try{
			conexion = new Socket(ip,PUERTO);
			DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
			DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());//Creamos objeto para enviar
			do{
				System.out.println("ingrese informacion a enviar al servidor");
				leo = buffer.readLine();
				flujoDatosSalida.writeUTF(leo); 
				System.out.println("Eco: " + flujoDatosEntrada.readUTF());
			}while(!leo.equals("EXIT")); //Mandamos el mensaje al servidor

		}catch(Exception e){
			System.out.println("No se puedo crear la conexion");
		}
	}

}