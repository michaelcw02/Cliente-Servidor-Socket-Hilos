/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import control.Control;

/**
 *
 * @author Michael Chen W.
 */
public class Main_Cliente {
    
    public static void main(String[] args) {
        
        Control c = new Control();
        c.mostrarVentana();
        
    }
    
    //andy guapo... jaja
    
    /*public static InetAddress getdir(String ar[]) {
    InetAddress tem = null;
    try {
    // si no hay argumentos tomar localhost
    if (ar.length == 0) {
    tem = InetAddress.getLocalHost();
    }
    // si hay convertirla de string a inetaddress
    if (ar.length == 1) {
    tem = InetAddress.getByName(ar[0]);
    }
    // error en la direccion
    } catch (UnknownHostException e) {
    System.out.println("Error en la direccion.");
    }
    return (tem);
    }*/

}
