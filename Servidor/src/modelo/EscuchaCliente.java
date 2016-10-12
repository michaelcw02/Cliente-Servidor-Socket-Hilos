/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Michael Chen W.
 */
public class EscuchaCliente extends Thread {
    
    public EscuchaCliente(InetSocketAddress client) {
        cliente = client;
        resultados = new LinkedList<>();
    }
    
    @Override
    public void run() {
        try {
            socket = new Socket(cliente.getAddress(), cliente.getPort());
            dataInput = new DataInputStream(socket.getInputStream());
            String mensaje;
            while( (mensaje = dataInput.readLine()) != null) {
                resultados.add(mensaje);
            }
        } catch (IOException ex) {
            resultados.add("ERROR ON CLIENT");
        }        
    }
    
    public LinkedList<String> getResultados() {
        return resultados;
    }
    
    private InetSocketAddress cliente;
    private Socket socket;
    DataInputStream dataInput;
    LinkedList<String> resultados;
}
