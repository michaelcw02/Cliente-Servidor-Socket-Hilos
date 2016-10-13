/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Chen W.
 */
public class Servidor {

    public Servidor() {
        listaClientes = new LinkedList<>();
        clienteEscuchando = new EscuchaCliente(PUERTO_SALIDA);
    }
    public void agregarClientes(String ip) {
        listaClientes.add(new InetSocketAddress(ip, PUERTO_SALIDA));
    }
    public void ejecutar(String cmd, int cantidad) {
        
        int cantPerClient = cantidad / listaClientes.size();
        cmd = CMD_INICIAL + cmd + " ";
        
        int counter = 1;
        for (InetSocketAddress cliente : listaClientes) {
            try {
                socket = new Socket(cliente.getAddress(), cliente.getPort());
                sendMessage(cmd, cantPerClient, counter++);
                readMessages();
            } catch (Exception ex) {
            }            
        }
        try {
        } catch (Exception ex) {
        }
    }
    
    private boolean sendMessage(String cmd, int cant, int number) {
        boolean ok = false;
        int start = (cant * number) - cant + 1;
        int end = cant * number;
        
        try {
            outputData = new PrintStream(socket.getOutputStream());
            String cmdToSend;
            for(int i = start; i <= end; i++) {
                cmdToSend = cmd + String.valueOf(i);
                outputData.println(cmdToSend);
            }
            ok = true;
        } catch (Exception e) {
        }
        finally {
            try{
                socket.close();
            }
            catch(Exception e){
                ok = false;
            }
        }
        return ok;
    }
    
    private void readMessages() {
        
        EscuchaCliente clienteEscuchando = new EscuchaCliente(PUERTO_SALIDA);
        clienteEscuchando.start();
         
    }
       
    public void respuestas() {
        
        clienteEscuchando.stop();
        System.out.println(clienteEscuchando.getResultados());
    }
    
    PrintStream outputData;
    private Socket socket = null;
    private List<InetSocketAddress> listaClientes = null;
    private EscuchaCliente clienteEscuchando = null;
    private final int PUERTO_SALIDA = 8585;
    private final String CMD_INICIAL = "CALCULAR_";
                                      //CALCULAR_PRIMO
                                      //CALCULAR_INVERSO

}
