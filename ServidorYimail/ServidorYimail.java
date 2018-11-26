import java.io.*;
import java.net.*;
//Clase que inicia el servidor, esta clase no funciona sin antes habilitar un DNS
public class ServidorYimail {
    public void inicia(){
        //instancia thread que recibe y envia los datos correspondientes al DNS
        DNSes dns = new DNSes();
        //inicia thread correspondientes al DNS
        dns.start();
        //instancia thread que recibe y envia los datos correspondientes a otros servidores
        Inicioservidor server = new Inicioservidor();
        //inicia thread correspondientes a servidores
        server.start();
        //instancia thread que recibe y envia los datos correspondientes al cliente
        InicioCliente cliente = new InicioCliente();
        //inicia thread correspondientes a clientes
        cliente.start();
    }
    public static void main(String[] args) {
        //instancia la clase actual
       ServidorYimail io = new ServidorYimail();
       //inicia servidor
       io.inicia();
    }
    
}
