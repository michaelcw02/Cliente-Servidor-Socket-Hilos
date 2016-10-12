/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import adaptadores.AdaptadorSubject;
import interfaces.Observer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Michael Chen W.
 */
public class Cliente extends Thread {

    public Cliente(){
        activo = true;
        subject = new AdaptadorSubject();
        calc = Numero.getInstancia();
    }
    
    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        
        try {
            servidor = new ServerSocket(PORT);
        } catch (Exception e) {
            setMsg("Couldn't connect..\n");
            activo = false;
        }
        setMsg("Conectado, esperando servidor..\n");

        while (activo) {
                       
            try {
                
                socket = servidor.accept();
                outputData = new PrintStream(socket.getOutputStream());
                inputData = new DataInputStream(socket.getInputStream());
                
                String mensajeOriginal;
                if ( (mensajeOriginal = inputData.readLine()) != null) {

                    setMsg("> " + mensajeOriginal + "\n");

                    // CALCULAR_PRIMO 23
                    String[] separado = mensajeOriginal.split(" ");
                    int numero = Integer.parseInt(separado[1]);

                    if (separado[0].equals("CALCULAR_PRIMO")) {
                        boolean primo = calc.calcularPrimo(numero);                        
                        if (primo == true) {
                            setMsg("EL NUMERO: " + numero + " ES PRIMO" + "\n");
                            outputData.print("EL NUMERO: " + numero + " ES PRIMO" + "\n");
                        } else {
                            setMsg("EL NUMERO: " + numero + " NO ES PRIMO" + "\n");
                            outputData.print("EL NUMERO: " + numero + " NO ES PRIMO" + "\n");
                        }
                    } else if (separado[0].equals("CALCULAR_INVERSO")) {
                        int num_inv = calc.calcularInverso(numero);
                        setMsg("El numero " + numero + " invertido es " + num_inv + "\n");
                        outputData.print("El numero " + numero + " invertido es " + num_inv + "\n");
                    }
                }

                //Get the return message from the server
                /*
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                System.out.println("Message received from the server : " + message);
                */
            } catch (Exception e) {
            }
        }
        try{
            socket.close();
        }
        catch(Exception e){
        }
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
    private PrintStream outputData;
    private DataInputStream inputData;
    
    
    private String msg;
    private Socket socket;
    private boolean activo;
    private final int PORT = 8585;
    AdaptadorSubject subject;
    private Numero calc;
}
