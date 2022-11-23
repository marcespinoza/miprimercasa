/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererTablaCliente extends DefaultTableCellRenderer{
private JLabel component;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
          
        if(table.getValueAt(row, 9) == null && !isSelected){
           component.setBackground(Color.cyan);
        }else{
            component.setBackground(null);
        }
        if(isSelected){
           component.setBackground(Color.yellow);
        }
        if(table.getValueAt(row, 23) != null){
           component.setHorizontalAlignment(SwingConstants.CENTER);
        }
        //---Pinto de rosado si el cliente no tiene propiedad asignada---//
        if(table.getValueAt(row, 11)==null){
            component.setBackground(Color.PINK);
        }
        //-----Pinto de rojo si el cliente esta dado de baja--//
        if(table.getValueAt(row, 10)!=null){
            if(table.getValueAt(row, 10).toString().equals("1")){
                component.setBackground(Color.RED);
            }
        }
         return component;
    }
    
    
    
}
