/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererTablaCuota extends DefaultTableCellRenderer{
private JLabel component;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
          
        if (row==0 && column==2) {
             setBackground(Color.green);
        }else{
             setBackground(null);
        }
         //-----Pinto de rojo si el cliente esta dado de baja--//
        if(table.getValueAt(row, 15)!=null){
            if(table.getValueAt(row, 15).toString().equals("1")){
                setBackground(Color.green);
            }
        }
        if(row!=0){
        if(new BigDecimal(table.getValueAt(row-1, 9).toString()).compareTo( new BigDecimal(table.getValueAt(row, 9).toString()))==1 && !table.getValueAt(row-1, 15).toString().equals("1")){
            //Color rosa    
            setBackground( new Color(242, 189, 151 ));
        }
        }
         return component;
    }   
  }
