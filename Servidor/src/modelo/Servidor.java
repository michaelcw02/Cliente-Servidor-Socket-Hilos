/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Michael Chen W.
 */
public class Servidor {

    public Servidor() {
        listaClientes = new LinkedList<>();
        clienteEscuchando = new EscuchaCliente();
    }
    public void agregarClientes(String ip) {
        listaClientes.add(new InetSocketAddress(ip, PUERTO_SALIDA));
    }
    public void ejecutar(String cmd, int cantidad) {
        
        int cantPerClient = cantidad / listaClientes.size();
        cmd = CMD_INICIAL + cmd + " ";
        
        int counter = 1;
        readMessages(); //initializes a thread to start reading any message
        for (InetSocketAddress cliente : listaClientes) {
            try {
                socket = new Socket(cliente.getAddress(), cliente.getPort());
                sendMessage(cmd, cantPerClient, counter++);
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
            outputData = new DataOutputStream(socket.getOutputStream());
            String cmdToSend;
            for(int i = start; i <= end; i++) {
                cmdToSend = cmd + String.valueOf(i) + "\n";
                outputData.writeBytes(cmdToSend);
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
        
        clienteEscuchando = new EscuchaCliente();
        clienteEscuchando.start();
         
    }
           
    private DataOutputStream outputData;
    private Socket socket = null;
    private List<InetSocketAddress> listaClientes = null;
    private EscuchaCliente clienteEscuchando = null;
    private final int PUERTO_SALIDA = 8500;
    private final String CMD_INICIAL = "CALCULAR_";
                                      //CALCULAR_PRIMO
                                      //CALCULAR_INVERSO

}
