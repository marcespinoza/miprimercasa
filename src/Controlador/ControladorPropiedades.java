/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DepartamentoDAO;
import Clases.LimitadorCaracteres;
import Clases.Lote;
import Clases.Propietario;
import Modelo.LoteDAO;
import Modelo.PropiedadesDAO;
import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropiedades implements ActionListener{
    
    Configuracion vista;
    PropietarioDAO pd = new PropietarioDAO();
    PropiedadesDAO prod = new PropiedadesDAO();
    LoteDAO ld = new LoteDAO();
    DepartamentoDAO dd = new DepartamentoDAO();
    private Object [] propiedades;
    String apellidos, nombres, cuit, propiedad, barrio, mz, pc, observaciones;
    int  idPropietario;

    public ControladorPropiedades(Configuracion vistaConfiguracion) {
        this.vista=vistaConfiguracion;
        this.vista.propiedades.comboApellido.addActionListener(this);
        this.vista.propiedades.comboNombres.addActionListener(this);
        this.vista.propiedades.agregar.addActionListener(this);
        this.vista.propiedades.eliminar.addActionListener(this);
        this.vista.propiedades.guardar.addActionListener(this);
        this.vista.propiedades.barrio.setDocument(new LimitadorCaracteres(25));
        this.vista.propiedades.mz.setDocument(new LimitadorCaracteres(5));
        this.vista.propiedades.pc.setDocument(new LimitadorCaracteres(5));
        this.vista.propiedades.observaciones.setDocument(new LimitadorCaracteres(30));
        this.vista.propiedades.comboPropiedad.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                llenarComboApellidos();                
            }
        });
        //======KeyListener sobre el campo parcela========//
        this.vista.propiedades.pc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
             if(e.getKeyCode()==KeyEvent.VK_ENTER){
               if(validarCampos()){
                 if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
                  if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){  
                    switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){   
                        case "Terreno": ld.agregarLote(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText(), String.valueOf(idPropietario)); break;
                        case "Departamento":dd.agregarDepartamento(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText()); break;    
                 }
               }
             }
              llenarTabla();}
             }
            }
         });
        //-------Cuando el usuario clickea sobre una fila, rellena los campos con esos datos---//
         this.vista.propiedades.tablaPropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override
         public void mouseClicked(java.awt.event.MouseEvent evt) {
           int row = vista.propiedades.tablaPropiedades.getSelectedRow();
           vista.propiedades.barrio.setText(vista.propiedades.tablaPropiedades.getModel().getValueAt(row,0).toString());
           vista.propiedades.mz.setText(vista.propiedades.tablaPropiedades.getModel().getValueAt(row,1).toString());
           vista.propiedades.pc.setText(vista.propiedades.tablaPropiedades.getModel().getValueAt(row,2).toString());
           vista.propiedades.observaciones.setText(vista.propiedades.tablaPropiedades.getModel().getValueAt(row,3).toString());
           barrio = vista.propiedades.tablaPropiedades.getModel().getValueAt(row,0).toString();
           mz = vista.propiedades.tablaPropiedades.getModel().getValueAt(row,1).toString();
           pc = vista.propiedades.tablaPropiedades.getModel().getValueAt(row,2).toString();
           observaciones = vista.propiedades.tablaPropiedades.getModel().getValueAt(row,3).toString();
         }
         });
         llenarComboApellidos();
         inhabilitarCampos();
         }
    
     public void llenarComboApellidos(){
        List<Propietario> propietarios = null;
        ResultSet rs = null;
        propietarios = pd.obtenerApellidos();
        vista.propiedades.comboApellido.removeAllItems();
        vista.propiedades.comboApellido.addItem("Seleccione");
        if(propietarios!=null){
            for (int i = 0; i < propietarios.size(); i++) {
                vista.propiedades.comboApellido.addItem(propietarios.get(i).getApellidos());
            }
        }   
      }   
      public void llenarComboNombres(String apellidos){
           List<Propietario>propietarios = null;
            propietarios= pd.obtenerNombres(apellidos);
            vista.propiedades.comboNombres.removeAllItems();
            vista.propiedades.comboNombres.addItem("Seleccione");
            for(int i = 0; i < propietarios.size(); i++) {
                vista.propiedades.comboNombres.addItem(propietarios.get(i).getNombres());
            }   
      }

    
    public void llenarTabla(){
      List<Lote>lotes = null;
      List<Propietario>propietarios = null;
      try {
        propietarios = pd.obtenerCuit(apellidos, nombres);
        vista.propiedades.cuit.setText(propietarios.get(0).getCuit());
        vista.propiedades.nroRecibo.setText(String.valueOf(propietarios.get(0).getNro_recibo()));
        idPropietario = propietarios.get(0).getIdPropeietario();
        switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){
            case"Terreno":lotes = ld.obtenerLotes(apellidos, nombres);break;
            case"Departamento":lotes = dd.obtenerDepartamentos(apellidos, nombres);break;}
        DefaultTableModel model = (DefaultTableModel) vista.propiedades.tablaPropiedades.getModel();
        model.setRowCount(0);        
            if(lotes!=null){
                for (int i = 0; i < lotes.size(); i++) {
                     String barrio = lotes.get(i).getBarrio();
                     String manzana = lotes.get(i).getManzana();
                     String parcela = lotes.get(i).getParcela();
                     String observaciones = lotes.get(i).getObservaciones();                
                               
                if(lotes.get(i).getVendido()==0){
                  propiedades = new Object[] {barrio, manzana, parcela, observaciones, "Libre"};}
                else{
                   propiedades = new Object[] {barrio, manzana, parcela, observaciones, "Vendido"};  
                }
                model.addRow(propiedades);
              }
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        if(e.getSource()==vista.propiedades.comboApellido){
          if(vista.propiedades.comboApellido.getItemCount()!=0){
            if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
                apellidos =vista.propiedades.comboApellido.getSelectedItem().toString();
                llenarComboNombres(apellidos);
            }else{
                vista.propiedades.comboNombres.setSelectedItem("Seleccione");
                inhabilitarCampos();
                limpiarCampos();
            }
          } 
        }
         if(e.getSource()==vista.propiedades.comboNombres){
           if(vista.propiedades.comboNombres.getItemCount()!=0){
            if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){ 
                nombres = vista.propiedades.comboNombres.getSelectedItem().toString();
                llenarTabla();
                habilitarCampos();
            }else{
                inhabilitarCampos();
                limpiarCampos();
            }
            }
        }
       //--------Boton para guardar cambios------//  
        if(e.getSource()==vista.propiedades.guardar){
            int flag = 0;
          if(validarCampos()){
            flag = ld.editarLote(barrio, mz, pc, vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.observaciones.getText());
            if(flag>0){
             limpiarCampos();
             llenarTabla();}
            else{
             JOptionPane.showMessageDialog(null, "Error al guardar cambios. Revise los campos", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
            }
          }
        } 
        //------Boton agregar propiedades------------//
        if(e.getSource()==vista.propiedades.agregar){
          if(validarCampos()){
            if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
               if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){ 
                int i = 0;   
                switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){   
                        case "Terreno": i = ld.agregarLote(vista.propiedades.barrio.getText().toString(), vista.propiedades.mz.getText().toString(), vista.propiedades.pc.getText().toString(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText(), String.valueOf(idPropietario)); break;
                        case "Departamento":dd.agregarDepartamento(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText()); break;    
                }
                if(i==0){
                 JOptionPane.showMessageDialog(null, "Lote duplicado", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                }
             }
            }
            llenarTabla();
           }
        }
        //------Boton eliminar propiedades--------------//
        if(e.getSource()==vista.propiedades.eliminar){
            int row=vista.propiedades.tablaPropiedades.getSelectedRow();
            if(row != -1){
               ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
               int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 0)+" "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 1)+" "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 2)+"?",
               "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
               ld.eliminarLote(vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 0).toString(),
                               vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 1).toString(),
                               vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 2).toString());
               limpiarCampos();                 
               llenarTabla();}
            }else{
              JOptionPane.showMessageDialog(null, "Seleccione una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
    }
    //-----Valida que todos los campos esten rellenos antes de agregar-------------//
    public boolean validarCampos(){
        boolean bandera = true;       
        if(vista.propiedades.barrio.getText().isEmpty()){
         vista.propiedades.barrio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
        if(vista.propiedades.mz.getText().isEmpty()){
         vista.propiedades.mz.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.mz.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
        if(vista.propiedades.pc.getText().isEmpty()){
         vista.propiedades.pc.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.pc.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
         return bandera;
    }
    
    //-------Limpia los labels y la tabla-----------------//
    private void limpiarCampos(){
       ((DefaultTableModel)vista.propiedades.tablaPropiedades.getModel()).setNumRows(0);
       vista.propiedades.cuit.setText("");
       vista.propiedades.nroRecibo.setText("");
       vista.propiedades.barrio.setText("");
       vista.propiedades.mz.setText(""); 
       vista.propiedades.pc.setText("");
    }
    
    //------Habilita los edittext-------//
    private void habilitarCampos(){
        vista.propiedades.barrio.setEnabled(true);
        vista.propiedades.pc.setEnabled(true);
        vista.propiedades.mz.setEnabled(true);
        vista.propiedades.observaciones.setEnabled(true);
    }
    
    //-----------Inhabilita los edittext----------------//
    private void inhabilitarCampos(){
        vista.propiedades.barrio.setEnabled(false);
        vista.propiedades.pc.setEnabled(false);
        vista.propiedades.mz.setEnabled(false);
        vista.propiedades.observaciones.setEnabled(false);
    }
    
}
