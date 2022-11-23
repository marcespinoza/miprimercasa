/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Controlador.ControladorLogin;
import Vista.Frame.Ventana;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
        
public class Main {
    
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new ControladorLogin();
       // ventana.setVisible(true);
    }
    
}
