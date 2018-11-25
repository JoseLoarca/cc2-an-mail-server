import java.io.*;
import java.net.*;

public class ServidorYimail {
    public void inicia(){
        InicioCliente cliente = new InicioCliente();
        cliente.start();
        Inicioservidor server = new Inicioservidor();
        server.start();
    }
    public static void main(String[] args) {
       ServidorYimail io = new ServidorYimail();
       io.inicia();
    }
    
}
