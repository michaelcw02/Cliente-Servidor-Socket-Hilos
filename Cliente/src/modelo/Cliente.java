/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import adaptadores.AdaptadorSubject;
import interfaces.Observer;
import java.io.BufferedWriter;
import java.io.DataInputStream;
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

        while (activo) {
                       
            try {
                
                socket = servidor.accept();
                setMsg("LLego el servidor, analizando numeros..");
                outputData = new PrintStream(socket.getOutputStream());
                inputData = new DataInputStream(socket.getInputStream());
                /*
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                
                msg = input.next();
                bw.write(msg);
                bw.flush();
                */
                String mensajeOriginal;
                if ( (mensajeOriginal = inputData.readLine()) != null) {

                    setMsg("> " + mensajeOriginal + "\n");

                    // CALCULAR_PRIMO 23
                    String[] separado = mensajeOriginal.split(" ");
                    int numero = Integer.parseInt(separado[1]);

                    if (separado[0].equals("CALCULAR_PRIMO")) {
                        int contador = 2;
                        boolean primo = true;
                        while ((primo) && (contador != numero)) {
                            if (numero % contador == 0) {
                                primo = false;
                            }
                            contador++;
                        }
                        if (primo) {
                            outputData.print("EL NUMERO: " + numero + " ES PRIMO" + "\n");
                            setMsg("EL NUMERO: " + numero + " ES PRIMO" + "\n");
                        } else {
                            outputData.print("EL NUMERO: " + numero + " NO ES PRIMO" + "\n");
                            setMsg("EL NUMERO: " + numero + " NO ES PRIMO" + "\n");
                        }
                    } else if (separado[0].equals("CALCULAR_INVERSO")) {
                        int num_inv = 0;
                        int div_entera = numero;
                        int resto_div = 0;
                        while (div_entera != 0) {
                            resto_div = div_entera % 10;
                            div_entera = div_entera / 10;
                            num_inv = num_inv * 10 + resto_div;
                        }

                        outputData.print("El numero " + numero + " invertido es " + num_inv + "\n");
                        //setMsg("El numero " + numero + " invertido es " + num_inv + "\n");
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
}
