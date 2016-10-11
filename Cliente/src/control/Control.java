/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import interfaz.VentanaPrincipal;
import modelo.Cliente;

/**
 *
 * @author Michael Chen W.
 */
public class Control {
    
    public Control() {
        ventana = new VentanaPrincipal(this);
        cliente = new Cliente();
    }
    
    public void mostrarVentana() {
        ventana.show();
    }
    
    public void conectarse() {
        cliente.setActivo(true);
        cliente.start();
    }
    public void desconectarse() {
        cliente.setActivo(false);
        cliente.stop();
    }
    
    Cliente cliente;
    VentanaPrincipal ventana;
}
