/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.Cumpleaños;
import Vista.Frame.Ventana;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorCumpleaños {
    
    Cumpleaños cumple;

    public ControladorCumpleaños(Ventana ventana) {
        cumple = Cumpleaños.getInstance(ventana, true);
        cumple.setLocationRelativeTo(null);
        cumple.setVisible(true);
    }
    
}
