/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jair
 */

import java.net.*;
import java.io.*;

public class InicioCliente extends Thread {
	private final int puerto1 = 1400;
	private ServerSocket cs1;
    private Socket sc1;
    public void run(){
    	try{
            cs1 = new ServerSocket(puerto1);
            sc1 = new Socket();
            System.out.println("Esperando conexion de cliente");
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
}