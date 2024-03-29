/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import adaptadores.AdaptadorSubject;
import interfaces.Observer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Michael Chen W.
 */
public class Cliente extends Thread {

    public Cliente(){
        activo = true;
        subject = new AdaptadorSubject();
        numeros = new LinkedList<>();
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void run() {

        try {
            servidor = new ServerSocket(PUERTO_ENTRADA);
        } catch (Exception e) {
            setMsg("Couldn't connect..\n");
            activo = false;
        }
        setMsg("Conectado, esperando servidor..\n");
        
        while (activo) {
            try {

                socket = servidor.accept();
                ip = socket.getInetAddress();
                dataInput = new InputStreamReader(socket.getInputStream());
                input = new BufferedReader(dataInput);

                String mensajeOriginal;
                while ((mensajeOriginal = input.readLine()) != null) {

                    setMsg("> " + mensajeOriginal + "\n");
                    numeros.add(new Numero(mensajeOriginal));

                }

            } catch (Exception e) {
            }

            try {
                socket.close();
            } catch (Exception e) {
            }
            startSending();
        }
    }
    
    private void startSending() {
        try {
            
            socket = new Socket(ip, PUERTO_SALIDA);
            outputData = new DataOutputStream(socket.getOutputStream());
            
            iniciarTodos();
            while(algunoVivo());
            
            for(Numero numero : numeros) {
                outputData.writeBytes(numero.getRespuesta());
                setMsg(numero.getRespuesta());
            }
        } catch(Exception e) {            
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }
    
    private void iniciarTodos() {        
        for (Numero actual : numeros) {
            actual.start();
        }
    }
    private boolean algunoVivo() {
        try {
            for (Numero actual : numeros) {
                if (actual.isAlive()) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public void agregar(Observer o) {
        subject.agregar(o);
    }
    public void notificar() {
        subject.notificar();
    }

    public void setMsg(String msg) {
        this.msg = msg;
        notificar();
    }
    public String getMsg() {
        return msg;
    }
    
    
    private ServerSocket servidor;
    private DataOutputStream outputData;
    InputStreamReader dataInput;
    BufferedReader input;
    
    
    private String msg;
    private Socket socket;
    private boolean activo;
    private InetAddress ip;
    private final int PUERTO_ENTRADA = 8500;
    private final int PUERTO_SALIDA = 8585;
    private AdaptadorSubject subject;
    LinkedList<Numero> numeros = null;
}
