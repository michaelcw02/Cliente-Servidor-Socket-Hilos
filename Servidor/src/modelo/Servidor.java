/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                messages(client, auxCmd);
            }
            else {
                iter = listaClientes.listIterator();
                i--;
            }
        }
    }
    
    private boolean messages(InetSocketAddress cliente, String str) {
        boolean ok = false;
        String mensajeOriginal;
        
        try {
            socket = new Socket(cliente.getAddress(), cliente.getPort());
            
            outputData = new PrintStream(socket.getOutputStream());
            inputData = new DataInputStream(socket.getInputStream());
            
            outputData.print(str);
            Thread.sleep(1000);
            mensajeOriginal = readMessages(socket);
            System.out.println("> " + mensajeOriginal + "\n");
            
            /*
            Thread.sleep(1000);
            
            if ((mensajeOriginal = inputData.readLine()) != null)
                System.out.println("> " + mensajeOriginal + "\n");
            */
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
    
    private String readMessages(Socket clientSocket) {
        int red = -1;
        byte[] buffer = new byte[5 * 1024]; // a read buffer of 5KiB
        byte[] redData;
        StringBuilder clientData = new StringBuilder();
        String redDataText;
        try {
            while ((red = clientSocket.getInputStream().read(buffer)) > -1) {
                redData = new byte[red];
                System.arraycopy(buffer, 0, redData, 0, red);
                redDataText = new String(redData, "UTF-8"); // assumption that client sends data UTF-8 encoded
                System.out.println("message part recieved:" + redDataText);
                clientData.append(redDataText);
            }
        } catch (IOException ex) {
        }
        return clientData.toString();
    }
    
    PrintStream outputData;
    DataInputStream inputData;
    private Socket socket = null;
    OutputStreamWriter osw = null;
    private List<InetSocketAddress> listaClientes = null;
    private final int PUERTO_SALIDA = 8585;
    private final String CMD_INICIAL = "CALCULAR_";
                                      //CALCULAR_PRIMO
                                      //CALCULAR_INVERSO

}
