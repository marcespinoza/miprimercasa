/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.LimitadorCaracteres;
import Clases.Usuario;
import Modelo.UsuarioDAO;
import Vista.Dialogs.Login;
import Vista.Frame.Ventana;
import conexion.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorLogin implements ActionListener, KeyListener{
    
   Login login;  
   Ventana frame;
   UsuarioDAO ud = new UsuarioDAO();
   FileWriter writer;
   
   static org.apache.log4j.Logger registroLogger= org.apache.log4j.Logger.getLogger("registro"); 
   static org.apache.log4j.Logger errorLogger= org.apache.log4j.Logger.getLogger("error");
   public ControladorLogin() {        
        login = new Login( true);  
        login.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                login.dispose();
            }            
        });
        login.usuario.addKeyListener(this);
        login.usuario.setDocument(new LimitadorCaracteres(15));
        login.contraseña.addKeyListener(this);
        login.contraseña.setDocument(new LimitadorCaracteres(15));
        login.cancelar.addActionListener(this);
        login.iniciar_sesion.addActionListener(this);
        login.tipo_operador.add(login.administradorChk);
        login.tipo_operador.add(login.operadorChk);
        login.operadorChk.setActionCommand("operador");
        login.administradorChk.setActionCommand("administrador");
        login.operadorChk.addActionListener(this);
        login.administradorChk.addActionListener(this);
        login.usuario.requestFocusInWindow();
        login.usuario.setCaretColor(Color.WHITE);
        login.contraseña.setCaretColor(Color.WHITE);
        login.operadorChk.setSelected(true);
        Calendar cal = Calendar.getInstance();
        login.eyePass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                login.contraseña.setEchoChar((char)0);
            }            
             @Override
            public void mouseReleased(MouseEvent e) {
             login.contraseña.setEchoChar('*');
             }
            });
             login.setVisible(true);
             }   

           @Override
           public void actionPerformed(ActionEvent e) {
           Usuario usuario = null;
            if(e.getSource() == login.iniciar_sesion){
                String contraseña= new String(login.contraseña.getPassword());
              if(login.usuario.getText().equals("") || contraseña.equals("")){
                  login.aviso.setText("* Ingrese todos los campos");
              }else{
                usuario = ud.validarUsuario(login.usuario.getText(), contraseña, login.tipo_operador.getSelection().getActionCommand());
                        if (usuario!=null){
                            frame = new Ventana();
                            registroLogger.info(usuario.getNombres()+" "+usuario.getApellidos()+ " - Inicio sesión");
                            Ventana.labelUsuario.setText(usuario.getUsuario());
                            Ventana.labelTipoUsuario.setText(usuario.getTipoUsuario());
                            Ventana.nombreUsuario.setText(usuario.getNombres());
                            Ventana.apellidoUsuario.setText(usuario.getApellidos());    
                            //-------Oculto ventana login y muestro el frame----------//
                            login.dispose();  
                            frame.desactivarBotones();
                            frame.setVisible(true);
                        }else{
                            login.aviso.setText("* Usuario y/o contraseña incorrectos");              
                        }  
                     }
            }
            if(e.getSource() == login.cancelar){
                login.dispose();
            }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
            login.iniciar_sesion.doClick();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
