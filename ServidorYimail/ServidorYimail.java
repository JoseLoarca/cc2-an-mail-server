import java.io.*;
import java.net.*;

public class ServidorYimail {
    public void inicia(){
        /*Inicioservidor server = new Inicioservidor();
        server.start();*/
        DNSes dns = new DNSes();
        dns.start();
        InicioCliente cliente = new InicioCliente();
        cliente.start();
    }
    public static void main(String[] args) {
       ServidorYimail io = new ServidorYimail();
       io.inicia();
    }
    
}
