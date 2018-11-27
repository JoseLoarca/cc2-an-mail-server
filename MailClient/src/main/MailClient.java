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

    /**
     * Main
     *
     * @param String[] args
     */
    public static void main(String[] args) {
  
        Ip ipSelect = new Ip();
        ipSelect.setVisible(true);
    }
}
