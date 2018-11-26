package Main;

import java.net.*;
import java.io.*;
import java.util.*;

public class DNSes extends Thread {

    private String ip = null;
    private Socket conexion = null;
    private String leo = null;
    private final int puerto = 1200;

    public boolean ip_configure(String ip_nuevo) {
        boolean retorno = true;

        try {
            this.ip = ip_nuevo;
        } catch (Exception e) {
            System.out.println("No se pudo configurar la IP del DNS");
            retorno = false;
        }

        return retorno;
    }

    public String obtener_ip() {
        return ip;
    }

    public void online() {
        try {
            conexion = new Socket(ip, puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "ONLINE yimail " + ip;
            flujoDatosSalida.writeUTF(leo);
            System.out.println(flujoDatosEntrada.readUTF());
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se puedo crear la conexion");
        }
    }

    public void offline() {
        try {
            conexion = new Socket(ip, puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "OFFLINE yimail";
            flujoDatosSalida.writeUTF(leo);
            System.out.println(flujoDatosEntrada.readUTF());
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se puedo crear la conexion");
        }
    }

    public void GETIPTABLE() {
        try {
            conexion = new Socket(ip, puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "GETIPTABLE";
            flujoDatosSalida.writeUTF(leo);
            System.out.println(flujoDatosEntrada.readUTF());
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se puedo crear la conexion");
        }
    }

    public String checkContact(String verify) {
        String retorno = null;
        try {
            conexion = new Socket(ip, puerto);
            DataInputStream flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            DataOutputStream flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());
            leo = "verify";
            flujoDatosSalida.writeUTF(leo);
            retorno = flujoDatosEntrada.readUTF();
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se puedo crear la conexion");
            retorno = "CHECK ERROR DNS_FAIL";
        }
        return retorno;
    }

    TimerTask timerTask = new TimerTask() {
        public void run() {
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
