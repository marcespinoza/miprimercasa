/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.DialogClientes;
import Vista.Frame.Ventana;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marceloi7
 */
public class ControladorPanelClientes implements ActionListener{
    
    DialogClientes vc;
    ClienteDAO cd = new ClienteDAO();
    private Object [] clientes;
    FichaControlDAO fd = new FichaControlDAO();
    int id_control = 0;
    int documento = 0;

    public ControladorPanelClientes(Ventana ventana, int documento, int id_control) {        
        vc = new DialogClientes(ventana, true);
        this.id_control=id_control;
        this.documento=documento;
        vc.aceptar.addActionListener(this);
        vc.cancelar.addActionListener(this);
        llenarTabla();
        vc.setLocationRelativeTo(null);
        vc.setVisible(true);        
    }
    
    private void llenarTabla(){
        ResultSet rs;
        rs = cd.clientes();
        DefaultTableModel model = (DefaultTableModel) vc.tablaDialogClientes.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String apellido = rs.getString(2);
                String nombre = rs.getString(3);
                String documento = rs.getString(1);
                clientes = new Object[] {apellido, nombre, documento};
                model.addRow(clientes); 
            }
        }
            catch(Exception e){
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vc.cancelar){
            vc.dispose();
        }
        if(e.getSource()==vc.aceptar){
            int row = vc.tablaDialogClientes.getSelectedRow();
              //--------Verifico que haya seleccionado alguna fila----------//
              if(row != -1){
                  fd.cambiarPropietario(Integer.parseInt(vc.tablaDialogClientes.getModel().getValueAt(row, 2).toString()), documento, id_control);
                  vc.dispose();
              }else{
                  JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);  
              }
            
        }
    }
}
