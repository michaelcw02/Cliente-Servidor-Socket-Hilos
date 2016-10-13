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

/**
 *
 * @author Michael Chen W.
 */
public class EscuchaCliente extends Thread {
    
    public EscuchaCliente(int port) {
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
                    if (mensaje != " ") {
                        System.out.println(">" + mensaje);
                        resultados = resultados + (mensaje + "\n");
                    }
                }

            } catch (Exception ex) {
                resultados = resultados + ("ERROR ON CLIENT\n");
            }
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public String getResultados() {
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
    String resultados = "\n";
    private int PUERTO;
}
