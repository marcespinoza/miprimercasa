/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.VendedorDAO;
import Vista.Dialogs.Configuracion;
import Vista.Panels.Vendedor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ing. Marcelo Espinoza
 */
public class ControladorVendedor implements ActionListener{
    
    Configuracion vConfiguracion = null;
    VendedorDAO vd = new VendedorDAO();
    private Object [] oVendedores;
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuEliminar = new JMenuItem("Eliminar");
 


    public ControladorVendedor(Configuracion vConfiguracion) {
        this.vConfiguracion= vConfiguracion;
       // popupMenu.add(menuEliminar);
       // menuEliminar.addActionListener(this);
        vConfiguracion.vendedores.tablaVendedores.setComponentPopupMenu(popupMenu);
        vConfiguracion.vendedores.tablaVendedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                 if (mouseEvent.getClickCount() == 3 ) {
                     DefaultTableModel model = (DefaultTableModel) table.getModel();
                     table.setModel(model);
                     model.addRow(new Object[]{ "", "", ""});}
            }     

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                String dni = table.getModel().getValueAt(row, 0).toString();
                String nya = table.getModel().getValueAt(row, 1).toString()+" "+table.getModel().getValueAt(row, 2).toString();
                final JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem deleteItem = new JMenuItem("Eliminar vendedor");
                deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(vConfiguracion, "Eliminar a "+nya, "",JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    
                }else{
                }
            }
        });
        popupMenu.add(deleteItem);
        table.setComponentPopupMenu(popupMenu);
            }
            
        });
        vConfiguracion.vendedores.tablaVendedores.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                DefaultTableModel model = (DefaultTableModel) e.getSource();
                switch(e.getColumn()){
                    //Modifica columna dni
                    case 0:vd.agregarVendedor(Integer.parseInt((String)model.getValueAt(e.getFirstRow(), e.getColumn())),
                           (String)model.getValueAt(e.getFirstRow(), 1),
                           (String) model.getValueAt(e.getFirstRow(), 2))
                        ; break;
                    //Modifica columna Apellido
                    case 1:
                        if(model.getValueAt(e.getFirstRow(), 0)!=null){
                        vd.editarVendedor(Integer.parseInt((String)model.getValueAt(e.getFirstRow(), 0)),
                                         (String)model.getValueAt(e.getFirstRow(), 1),
                                         (String) model.getValueAt(e.getFirstRow(), 2));
                        }
                        break;
                    //Modifica columna Nombre
                    case 2:
                        if(model.getValueAt(e.getFirstRow(), 0)!=null){
                        vd.editarVendedor(Integer.parseInt((String)model.getValueAt(e.getFirstRow(), 0)),
                                         (String)model.getValueAt(e.getFirstRow(), 1),
                                         (String) model.getValueAt(e.getFirstRow(), 2));
                        }
                        break;
                    default:
                }
            }
        });      
        llenarTabla();
    }
    
    public void llenarTabla(){
        List<Clases.Vendedor> vendedores = vd.obtenerVendedores();
        DefaultTableModel model = (DefaultTableModel) vConfiguracion.vendedores.tablaVendedores.getModel();        
        model.setRowCount(0);
        for (int i = 0; i < vendedores.size(); i++) {          
             int dni = vendedores.get(i).getDni();
             String apellido = vendedores.get(i).getApellido();
             String nombre = vendedores.get(i).getNombre();
             oVendedores = new Object[] {dni, apellido, nombre};
             model.addRow(oVendedores);
          }
       }

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuEliminar) {
            
        }
    }
    
}
