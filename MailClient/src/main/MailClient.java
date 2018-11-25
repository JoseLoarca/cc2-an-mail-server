/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Window;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ventanas.*;

/**
 *
 * @author JoséCarlos
 */
public class MailClient {
    public static DataInputStream flujoDatosEntrada;
    public static DataOutputStream flujoDatosSalida;
    static Login login;
    static Socket conexion = null;
    
    static TimerTask timerTask = new TimerTask() {
        public void run() {
            
            String reqStr = "NOOP";
            
            try {
                flujoDatosSalida.writeUTF(reqStr);
                
                String response = flujoDatosEntrada.readUTF();
             
                switch(response) {
                    case "SERVER : OK NOOP":
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la conexion al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                        login.disableLoginButton();
                        conexion.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la conexion al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                login.disableLoginButton();
                try {
                    conexion.close();
                } catch (IOException ex1) {
                    System.out.println("Error al cerrar conexion.");
                }
                System.out.println("Error al enviar datos");
            }
        }
    };
    
    public static void main(String[] args) {
        Timer timer = new Timer();
        String leo;
        String ip = "192.168.1.6";
        int PUERTO = 1400;
         
        try{
            conexion = new Socket(ip,PUERTO);
            flujoDatosEntrada = new DataInputStream(conexion.getInputStream());
            flujoDatosSalida = new DataOutputStream(conexion.getOutputStream());//Creamos objeto para enviar
            
            login = new Login();
            login.setInfo(flujoDatosEntrada, flujoDatosSalida, conexion);
            login.setVisible(true);
            
            timer.scheduleAtFixedRate(timerTask, 0, 15000);
            
        } catch (Exception e){
            System.out.println("No se pudo crear la conexion.");
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al conectar al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            try {
                conexion.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar la conexion");
            }
        }
        
      
       
    }
}
