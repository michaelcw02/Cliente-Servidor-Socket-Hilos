/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author Michael Chen W.
 */
public class Servidor {

    public Servidor() {
        listaClientes = new LinkedList<>();
    }
    public void agregarClientes(String ip) {
        listaClientes.add(new InetSocketAddress(ip, PUERTO_SALIDA));
    }
    public void ejecutar(String cmd, int cantidad) {
        
        cmd = CMD_INICIAL + cmd + " ";
        
        ListIterator<InetSocketAddress> iter = listaClientes.listIterator();
        String auxCmd;
        for(int i = 1; i <= cantidad; i++) {
            auxCmd = cmd + String.valueOf(i);
            if(iter.hasNext()) {
                InetSocketAddress client = iter.next();
                sendMessage(client, cmd);
            }
            else {
                iter = listaClientes.listIterator();
            }
        }
    }
    
    private boolean sendMessage(InetSocketAddress cliente, String str) {
        boolean ok = false;
        
        try {
            socket = new Socket(cliente.getAddress(), cliente.getPort());
            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            osw.write(str, 0, str.length());
            ok = true;
        }
        catch (Exception e) {    
        }finally {
            try{
                socket.close();
            }
            catch(Exception e){
                ok = false;
            }
        }
        return ok;
    }
    
    
    private Socket socket = null;
    OutputStreamWriter osw = null;
    private List<InetSocketAddress> listaClientes = null;
    private final int PUERTO_SALIDA = 8585;
    private final String CMD_INICIAL = "CALCULAR_";
                                      //CALCULAR_PRIMO
                                      //CALCULAR_INVERSO

}
