/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import interfaz.VentanaPrincipal;
import java.io.PrintStream;
import modelo.Servidor;

/**
 *
 * @author Estudiante
 */
public class Control {
    
    public void Control() {
        ventana = new VentanaPrincipal(this);
        servidor = new Servidor();
    }
    
    public void muestraVentana() {
        ventana.show();
    }
    
    public void agregarIp(String ip) {
        servidor.agregarClientes(ip);
    }
    public void agregarCantidad(int cant) {
        cantidad = cant;
    }
    public void calcular(String cmd) {
        //ps = new PrintStream(/*servidor.getOutputStream()*/);
        servidor.ejecutar(cmd);
    }
    
    
    VentanaPrincipal ventana;
    Servidor servidor;
    int cantidad = 0;
    PrintStream ps;
}
