/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.Registro;
import Vista.Frame.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorRegistro implements ActionListener{

    Registro registro;
    
    public ControladorRegistro(Ventana ventana) {
      this.registro = new Registro(ventana, true);
      registro.setLocationRelativeTo(ventana);
      registro.cerrar.addActionListener(this);
      cargarLog();
      registro.areaTexto.setEditable(false);
      registro.setVisible(true);
    }
    
    private void cargarLog() {
        try {
           String storeAllString = null;
           FileReader read = new FileReader("registro.log");
           Scanner scan = new Scanner(read);
           while(scan.hasNextLine()){
              String temp=scan.nextLine()+"\n";
              storeAllString =temp + storeAllString;
             }
            registro.areaTexto.setText(storeAllString);
        } catch (IOException ex) {
            Logger.getLogger(ControladorRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(registro.cerrar)){
            registro.dispose();
        }
    }
    
}
