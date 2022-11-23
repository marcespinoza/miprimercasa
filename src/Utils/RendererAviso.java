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
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Marcelo Espinoza
 */
public class RendererAviso extends DefaultTableCellRenderer{
    
    JLabel jLabel = new JLabel();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/iconos/alerta.png"));
    ImageIcon icon2 = new ImageIcon(getClass().getResource("/Imagenes/iconos/alerta2.png"));
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        jLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        if(isSelected){
            setBackground(Color.YELLOW);
        }else{
            setBackground(Color.WHITE);
        }
         //-----Pinto de rojo si el cliente esta dado de baja--//
        if(table.getValueAt(row, 10)!=null){
            if(table.getValueAt(row, 10).toString().equals("1")){                
                jLabel.setBackground(Color.RED);
            }
        }
        if(table.getValueAt(row, 11)==null){
            jLabel.setBackground(Color.PINK);
        }
        if(table.getValueAt(row, 21)!=null){
           //-----Si esta dado de baja no pongo el aviso de actualizacion de cuota---//
           if(!table.getValueAt(row, 10).toString().equals("1")){  
//            if(table.getValueAt(row, 20).toString().equals("1")){                
//                jLabel.setIcon(icon);
//                jLabel.setHorizontalAlignment(CENTER);
//            }else 
                if(table.getValueAt(row, 20).toString().equals("2")){                
                jLabel.setIcon(icon2);
                jLabel.setHorizontalAlignment(CENTER);
            }
            else{
                jLabel.setIcon(null);
            }
           }
        }
         return jLabel;
    }   
  }