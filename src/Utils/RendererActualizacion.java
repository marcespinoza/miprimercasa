/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererActualizacion extends DefaultTableCellRenderer{
    
    Color verde = new Color(0, 151, 0);
    Color violeta= new Color(167, 33, 199);
    private JLabel c;
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
     c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
        if(table.getValueAt(row, 19).toString().equals("Cemento")){
           c.setForeground(Color.blue);          
        }else if (table.getValueAt(row, 19).toString().equals("C. Fija")){
            c.setForeground(Color.DARK_GRAY); 
        }else if (table.getValueAt(row, 19).toString().equals("C. fija vble.")){
            c.setForeground(violeta); 
        }else{
            c.setForeground(verde);
        }
        if(isSelected){
            c.setBackground(Color.YELLOW);
        }else{
            c.setBackground(Color.WHITE);
        }
        //---Pinto de rosado si el cliente no tiene propiedad asignada---//
        if(table.getValueAt(row, 11)==null){
            c.setBackground(Color.PINK);
        }
        //-----Pinto de rojo si el cliente esta dado de baja--//
        if(table.getValueAt(row, 10)!=null){
            if(table.getValueAt(row, 10).toString().equals("1")){
                c.setBackground(Color.RED);
            }
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }   
  }