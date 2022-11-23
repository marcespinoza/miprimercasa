/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.Calculadora;
import Vista.Frame.Ventana;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorBotones implements ActionListener{
    
    Ventana ventana;
 
    public ControladorBotones(Ventana ventana) {
           this.ventana=ventana;
           this.ventana.btnMinuta.addActionListener(this);
           this.ventana.btnClientes.addActionListener(this);
           this.ventana.btnResumen.addActionListener(this);
           this.ventana.btnCumpleaños.addActionListener(this);
           this.ventana.btnCumpleaños.setVisible(false);
           this.ventana.cerrarSesion.addActionListener(this);
           this.ventana.calculadora.addActionListener(this);
           this.ventana.ayuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
           this.ventana.ayuda.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                try {
                Desktop.getDesktop().browse(new URI("https://sites.google.com/view/sistema-miprimercasa/p%C3%A1gina-principal"));
            } catch (URISyntaxException ex) {
                Logger.getLogger(ControladorBotones.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorBotones.class.getName()).log(Level.SEVERE, null, ex);
            }               
            }

               @Override
               public void mouseEntered(MouseEvent e) {                  
               }
             
           });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==ventana.btnMinuta){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "Minuta");
        }
               
        
        
        if(e.getSource()==ventana.btnResumen){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "Resumen");
        }
        
        if(e.getSource()==ventana.btnClientes){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "Clientes");
        }
        
        if(e.getSource()==ventana.btnCumpleaños){
         new ControladorCumpleaños(ventana);
        }
        
        if(e.getSource()==ventana.cerrarSesion){
          ventana.dispose();
          new ControladorLogin();
        }
                
        if(e.getSource()==ventana.calculadora){
            Calculadora calculadora = new Calculadora(ventana, true);
            calculadora.setVisible(true);
        }
    }    
    
}
