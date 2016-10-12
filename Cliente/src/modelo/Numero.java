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
public class Numero {
    
    private Numero() {
        
    }
    public static Numero getInstancia() {
        if(instancia == null)
            instancia = new Numero();
        return instancia;
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
        int contador = 2;
        boolean primo = true;
        while ((primo) && (contador != numero)) {
            if (numero % contador == 0) {
                primo = false;
            }
            contador++;
        }
        return primo;
    }
    
    private static Numero instancia = null;
}
