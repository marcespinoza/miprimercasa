/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.LimitadorCaracteres;
import Modelo.UsuarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorUsuario implements MouseListener, ActionListener, KeyListener{
    
    Configuracion vistaConfiguracion;
    UsuarioDAO ud = new UsuarioDAO();
    private Object [] usuarios;
      File configFile = new File("config.properties");

    public ControladorUsuario(Configuracion vistaConfiguracion) {
        this.vistaConfiguracion = vistaConfiguracion;
        vistaConfiguracion.usuario.tablaUsuarios.addMouseListener(this);
        vistaConfiguracion.usuario.agregar.addActionListener(this);
        vistaConfiguracion.usuario.eliminar.addActionListener(this);
        vistaConfiguracion.usuario.editar.addActionListener(this);
        vistaConfiguracion.usuario.limpiar.addActionListener(this);
        vistaConfiguracion.usuario.usuarioTxf.addKeyListener(this);
        vistaConfiguracion.usuario.usuarioTxf.setDocument(new LimitadorCaracteres(15));
        vistaConfiguracion.usuario.contraseñaTxf.addKeyListener(this);
        vistaConfiguracion.usuario.contraseñaTxf.setDocument(new LimitadorCaracteres(15));
        vistaConfiguracion.usuario.apellidoTxf.addKeyListener(this);
        vistaConfiguracion.usuario.apellidoTxf.setDocument(new LimitadorCaracteres(30));
        vistaConfiguracion.usuario.nombreTxf.addKeyListener(this);
        vistaConfiguracion.usuario.nombreTxf.setDocument(new LimitadorCaracteres(30));
        vistaConfiguracion.usuario.tipo_operador.addMouseListener(this);
        vistaConfiguracion.usuario.editar.setEnabled(false);
        vistaConfiguracion.setLocationRelativeTo(null);
        llenarTabla();
        
    }
    
    public void llenarTabla(){
        ResultSet rs = ud.obtenerUsuarios();
        DefaultTableModel model = (DefaultTableModel) vistaConfiguracion.usuario.tablaUsuarios.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String usuario = rs.getString(1);
                String contraseña = rs.getString(2);
                String apellidos = rs.getString(3);  
                String nombres = rs.getString(4);
                String tipo_usuario = rs.getString(5);
                usuarios = new Object[] {usuario, contraseña, apellidos, nombres, tipo_usuario};
                model.addRow(usuarios);
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
          int row = vistaConfiguracion.usuario.tablaUsuarios.getSelectedRow();
          vistaConfiguracion.usuario.usuarioTxf.setText(vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,0).toString());
          vistaConfiguracion.usuario.contraseñaTxf.setText(vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,1).toString());
          vistaConfiguracion.usuario.apellidoTxf.setText(vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,2).toString());
          vistaConfiguracion.usuario.nombreTxf.setText(vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,3).toString());
          switch(vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,4).toString()){              
              case "operador":vistaConfiguracion.usuario.tipo_operador.setSelectedIndex(2);
              break;
              case "administrador":vistaConfiguracion.usuario.tipo_operador.setSelectedIndex(1);
              break;
              default:vistaConfiguracion.usuario.tipo_operador.setSelectedIndex(1);
              break;
          }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaConfiguracion.usuario.limpiar){
           limpiarCampos();
        }
        
        if(e.getSource()==vistaConfiguracion.usuario.editar){
           ud.actualizarUsuario(vistaConfiguracion.usuario.usuarioTxf.getText(), vistaConfiguracion.usuario.contraseñaTxf.getText(), vistaConfiguracion.usuario.apellidoTxf.getText(), vistaConfiguracion.usuario.nombreTxf.getText(), vistaConfiguracion.usuario.tipo_operador.getSelectedItem().toString());
           llenarTabla();
        }
        
        if(e.getSource()==vistaConfiguracion.usuario.eliminar){
            int row = vistaConfiguracion.usuario.tablaUsuarios.getSelectedRow();
            if(row==-1){
              JOptionPane.showMessageDialog(null, "Seleccione un usuario", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }else{
              ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row, 2)+" "+""+" "+vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row, 3)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
               String usuario = vistaConfiguracion.usuario.tablaUsuarios.getModel().getValueAt(row,0).toString();
               ud.eliminarUsuario(usuario);
               limpiarCampos();
               llenarTabla();
                 }                
            }
        }
        
           if(e.getSource()==vistaConfiguracion.usuario.agregar){
             if(validarCampos()){
              ud.agregarUsuario(vistaConfiguracion.usuario.usuarioTxf.getText(), vistaConfiguracion.usuario.contraseñaTxf.getText(), vistaConfiguracion.usuario.apellidoTxf.getText(), vistaConfiguracion.usuario.nombreTxf.getText(), String.valueOf(vistaConfiguracion.usuario.tipo_operador.getSelectedItem()));
              llenarTabla();
              limpiarCampos();
             }
           }
    }
    
    private void cargarNroRecibo(){
         try {
          FileReader reader = new FileReader(configFile);
          Properties props = new Properties();
          props.load(reader); 
          String nroRecibo = props.getProperty("pathMinuta");
          vistaConfiguracion.propietarios.nroRecibo.setText(nroRecibo);
          reader.close();
         } catch (FileNotFoundException ex) {
        // file does not exist
        } catch (IOException ex) {
        // I/O error
         }
    }    
        
    private void limpiarCampos(){
         vistaConfiguracion.usuario.usuarioTxf.setText("");
         vistaConfiguracion.usuario.contraseñaTxf.setText("");
         vistaConfiguracion.usuario.apellidoTxf.setText("");
         vistaConfiguracion.usuario.nombreTxf.setText("");
    }
    
     public boolean validarCampos(){
        boolean bandera = true;
       
        if(vistaConfiguracion.usuario.usuarioTxf.getText().isEmpty()){
         vistaConfiguracion.usuario.usuarioTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.usuario.usuarioTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.usuario.contraseñaTxf.getText().isEmpty()){
         vistaConfiguracion.usuario.contraseñaTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.usuario.contraseñaTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.usuario.apellidoTxf.getText().isEmpty()){
         vistaConfiguracion.usuario.apellidoTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.usuario.apellidoTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.usuario.nombreTxf.getText().isEmpty()){
         vistaConfiguracion.usuario.nombreTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.usuario.nombreTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
           vistaConfiguracion.usuario.editar.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
