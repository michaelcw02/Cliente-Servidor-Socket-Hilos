/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import interfaz.TablaIPs;
import interfaz.VentanaPrincipal;
import java.io.PrintStream;
import modelo.Servidor;

/**
 *
 * @author Estudiante
 */
public class Control {
    
    public Control() {
        ventana = new VentanaPrincipal(this);
        ventanaSecundaria = new TablaIPs();
        servidor = new Servidor();
        
    }
    
    public void muestraVentana() {
        ventanaSecundaria.show();
        ventana.show();        
    }
    
    public void agregarIp(String ip) {
        servidor.agregarClientes(ip);
        ventanaSecundaria.addIP(ip);
    }
    public void agregarCantidad(int cant) {
        cantidad = cant;
    }
    public void calcular(String cmd) {
        //ps = new PrintStream(/*servidor.getOutputStream()*/);
        servidor.ejecutar(cmd, cantidad);
    }
    public void respuesta() {
        servidor.respuestas();
    }
    
    
    VentanaPrincipal ventana;
    TablaIPs ventanaSecundaria;
    Servidor servidor;
    int cantidad = 0;
    PrintStream ps;
}
