/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.Configuracion;
import Vista.Frame.Ventana;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorConfiguracion {
    
    Configuracion configuracion;

    public ControladorConfiguracion(Ventana parent) {
        configuracion = new Configuracion(parent, true);
        new ControladorPropietarios(configuracion);
        new ControladorUsuario(configuracion);
        new ControladorPropiedades(configuracion);
        new ControladorVendedor(configuracion);
        desactivarBotones();
        configuracion.setVisible(true);        
    }
    
    public void desactivarBotones(){        
        if(Ventana.labelTipoUsuario.getText().equals("operador")){
            configuracion.pestañas.setEnabledAt(0, false);
            configuracion.pestañas.setSelectedIndex(1);
//          configuracion.pestañas.setEnabledt(2, false);
//          configuracion.pestañas.setSelectedIndex(1);
        }
    }
    
}
