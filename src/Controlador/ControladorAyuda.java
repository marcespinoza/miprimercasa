/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Frame.Ventana;
import help.Help;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;

/**
 *
 * @author Marceloi7
 */
public class ControladorAyuda {
    
    Ventana ventana;

    public ControladorAyuda(Ventana ventana) {
        this.ventana=ventana;
        init();
    }
    
    private void init() {
    Help help = new Help();
    help.setVisible(true);
}
    
}
