/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Chen W.
 */
public class EscuchaCliente extends Thread {
    
    public EscuchaCliente(int port) {
        resultados = new LinkedList<>();
        activo = true;
        PUERTO = port;
    }
    
    @Override
    public void run() {
        
        try {
            servidor = new ServerSocket(PUERTO+1);
        } catch (Exception e) {
            System.out.println("NO SE PUDO CONECTAR AL SERVIDOR..");
            activo = false;
        }
        
        System.out.println("SERVIDOR ESCUCHANDO..");
        while (activo) {
            try {

                socket = servidor.accept();
                System.out.println("SERVIDOR CONECTADO..");
                dataInput = new DataInputStream(socket.getInputStream());
                String mensaje;
                while ((mensaje = dataInput.readLine()) != null) {
                    System.out.println(">" + mensaje);
                    resultados.add(mensaje);
                }

            } catch (Exception ex) {
                resultados.add("ERROR ON CLIENT");
            }
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public LinkedList<String> getResultados() {
        return resultados;
    }

    public void setActivo(boolean activo) {
            this.activo = activo;
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }
    
    boolean activo;
    private Socket socket;
    private ServerSocket servidor;
    DataInputStream dataInput;
    LinkedList<String> resultados;
    private int PUERTO;
}
