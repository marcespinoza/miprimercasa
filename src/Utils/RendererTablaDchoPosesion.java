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
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererTablaDchoPosesion extends DefaultTableCellRenderer{
private JLabel component;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
          
         if (row==0 && column==8) {
        setBackground(Color.green);
        }else{
             setBackground(null);}
         return component;
    }
    
    
    
}
