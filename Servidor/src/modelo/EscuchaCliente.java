/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaz.Respuestas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author Michael Chen W.
 */
public class EscuchaCliente extends Thread {
    
    public EscuchaCliente() {
        activo = true;
        respuesta = new Respuestas();
        txtArea = respuesta.getTxtArea();
    }
    
    public void showTxtArea() {
        respuesta.show();
    }
    
    @Override
    public void run() {
        try {
            servidor = new ServerSocket(PUERTO_ENTRADA);
        } catch (Exception e) {
            txtArea.append("NO SE PUDO CONECTAR AL SERVIDOR..\n");
            System.out.println("NO SE PUDO CONECTAR AL SERVIDOR..");
            activo = false;
        }

        txtArea.append("SERVIDOR ESCUCHANDO..\n");        
        System.out.println("SERVIDOR ESCUCHANDO..");
        while (activo) {
            try {

                socket = servidor.accept();
                txtArea.append("SERVIDOR CONECTADO..\n");
                System.out.println("SERVIDOR CONECTADO..");
                dataInput = new InputStreamReader(socket.getInputStream());
                input = new BufferedReader(dataInput);
                String mensaje;
                while ((mensaje = input.readLine()) != null) {
                    if (mensaje != " ") {
                        System.out.println(">" + mensaje);
                        txtArea.append(mensaje + "\n");
                    }
                }

            } catch (Exception ex) {
                txtArea.append("ERROR ON CLIENT\n");
            }
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
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
    InputStreamReader dataInput;
    BufferedReader input;
    private final int PUERTO_ENTRADA = 8585;
    Respuestas respuesta;
    JTextArea txtArea;
}
