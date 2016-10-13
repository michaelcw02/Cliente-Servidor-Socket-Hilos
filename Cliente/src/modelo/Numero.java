/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Michael Chen W.
 */
public class Numero extends Thread {
    
    public Numero(String cmd) {
        comando = cmd;
    }
    
    @Override 
    public void run() {
        
        String[] separado = comando.split(" ");
        int numero = Integer.parseInt(separado[1]);

        if (separado[0].equals("CALCULAR_PRIMO")) {
            boolean primo = calcularPrimo(numero);
            if (primo)
                respuesta = ("EL NUMERO: " + numero + " ES PRIMO\n");
            else
                respuesta = ("EL NUMERO: " + numero + " NO ES PRIMO\n");
            
        } else if (separado[0].equals("CALCULAR_INVERSO")) {
            int num_inv = calcularInverso(numero);
            respuesta = ("El numero " + numero + " invertido es " + num_inv + "\n");
        }

    }
    
    public int calcularInverso(int numero) {
        int num_inv = 0;
        int div_entera = numero;
        int resto_div = 0;
        while (div_entera != 0) {
            resto_div = div_entera % 10;
            div_entera = div_entera / 10;
            num_inv = num_inv * 10 + resto_div;
        }
        return num_inv;
    }
    
    public boolean calcularPrimo(int numero) {
        for(int i = 2; i < numero/2; i++)
            if( (numero%i) == 0)
                return true;
        return false;
    }

    public String getRespuesta() {
        return respuesta;
    }
    
    String comando;
    String respuesta;
}
