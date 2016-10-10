/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Michael Chen W.
 */
public class Cliente extends Thread {

    public Cliente(){
        activo = true;
    }
    
    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        while (activo) {
            try {
                socket = new Socket(HOST, PORT);
            }
            catch (Exception e) {
                System.out.println("Couldn't connect to server..");
            }
            
            try {
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                String msg = input.next();
                bw.write(msg);
                bw.flush();
                System.out.println("> " + msg);
                
                // CALCULAR_PRIMO 23
                String[] separado = msg.split(" "); 
                int numero = Integer.parseInt(separado[1]);
                if(separado[0].equals("CALCULAR_PRIMO")){
                    int cont = 0;
                    for(int i = 0; i < numero; i++) {
                        if(numero % i == 0)
                            cont++;                                  
                    }
                    if(cont == 1) 
                        System.out.println("EL NUMERO: " + numero + " ES PRIMO");
                    else
                        System.out.println("EL NUMERO: " + numero + " NO ES PRIMO");
                }
                else if(separado[0].equals("CALCULAR_INVERSO")){
                    int num_inv = 0;
                    int div_entera = numero;
                    int resto_div = 0;  
                    while (div_entera != 0) {
                        resto_div = div_entera % 10;
                        div_entera = div_entera / 10;
                        num_inv = num_inv * 10 + resto_div;
                    }
                    System.out.println("El numero " + numero + " invertido es " + num_inv);
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
    
    private Socket socket;
    private boolean activo;
    private final int PORT = 1234;
    private final String HOST = "127.0.0.1";
}
