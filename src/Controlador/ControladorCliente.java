/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ActualizacionCementoDAO;
import Clases.ClientesPorCriterio;
import Clases.GenerarLista;
import Clases.Lote;
import Clases.Propietario;
import Clases.Referencia;
import static Controlador.ControladorDetalleCuota.IMG;
import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.PropietarioDAO;
import Modelo.ReferenciaDAO;
import Utils.IsInteger;
import Utils.RendererActualizacion;
import Utils.RendererAviso;
import Utils.RendererTablaCliente;
import Vista.Dialogs.Cumpleaños;
import Vista.Dialogs.Progress;
import Vista.Dialogs.ProgressDialog;
import Vista.Frame.Ventana;
import Vista.Panels.Clientes;
import Vista.Panels.DetalleCuota;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener, MouseListener, TableModelListener{
    
    RendererTablaCliente r = new RendererTablaCliente();
    Clientes vistaClientes;
    Cumpleaños dialogCumpleaños;
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    LoteDAO ld = new LoteDAO();
    FichaControlDAO fd = new FichaControlDAO();
    PropietarioDAO pd = new PropietarioDAO();
    ActualizacionCementoDAO acd = new ActualizacionCementoDAO();
    private ArrayList<String[]> cumpleaños = new ArrayList<String[]>();
    public ArrayList<Object> datosCliente = new ArrayList<>();
    Ventana ventana;
    String barrio;
    int manzana, parcela;
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    private Object [] clientes;
    private List<Object> cliente = new ArrayList<>();
    private List<Object> referencia = new ArrayList<>();
    File configFile = new File("config.properties");
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    private String nombres, apellidos, lote;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("registro");
    ArrayList<Integer> sucesion = new ArrayList<>();
    JTable tablePrinter = null;
    String tipoFiltro = "";
    boolean filtroManzana = false;
    boolean filtroParcela = false;
//    Integer manzana_prop = 0;
//    Integer parcela_prop = 0;
    
    public ControladorCliente(Clientes vistaCliente, Ventana ventana){
        this.vistaClientes=vistaCliente;
        this.ventana=ventana;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.editarBtn.addActionListener(this);
        this.vistaClientes.detalleBtn.addActionListener(this);
        this.vistaClientes.asignarBtn.addActionListener(this);
        this.vistaClientes.bajaBtn.addActionListener(this);
        this.vistaClientes.tablaCliente.addMouseListener(this);
        this.vistaClientes.agregarPropietario.addActionListener(this);
        this.vistaClientes.cambiarPropietario.addActionListener(this);
        this.vistaClientes.comboApellido.addActionListener(this);
        this.vistaClientes.comboDias.addActionListener(this);
        this.vistaClientes.comboLote.addActionListener(this);
        this.vistaClientes.comboNombre.addActionListener(this);
        this.vistaClientes.mostrarTodos.addActionListener(this);
        this.vistaClientes.imprimirClientes.addActionListener(this);
        this.vistaClientes.imprimirClientesOrdenados.addActionListener(this);
        this.vistaClientes.tablaCliente.getModel().addTableModelListener(this);
        this.vistaClientes.buscarTodos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarTodos.getText().toLowerCase();
              filter(query);
            }
         });
        this.vistaClientes.buscarBarrio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarBarrio.getText().toLowerCase();
              filtroBarrio(query);
            }
         });
        this.vistaClientes.buscarManzana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarManzana.getText().toLowerCase();
              filtroManzana(query);
            }
         });
        this.vistaClientes.buscarParcela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarParcela.getText().toLowerCase();
              filtroParcela(query);
            }
         });
        this.vistaClientes.buscarTipoCuota.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarTipoCuota.getText().toLowerCase();
              filtroTipoCuota(query);
            }
         });
        this.vistaClientes.bolsa_cemento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
               if(e.getKeyCode()==KeyEvent.VK_ENTER){                   
                    Date date = new Date();
                    String bolsaCemento = vistaClientes.bolsa_cemento.getText();
                if(!bolsaCemento.equals("")){
                  if(!new BigDecimal(bolsaCemento).equals(BigDecimal.ZERO)){  
                    int i = vistaClientes.tablaCliente.getSelectedRow();
                    BigDecimal nuevo_bolsa_cemento = new BigDecimal(vistaClientes.bolsa_cemento.getText());
                    fd.actualizarBolsaCemento( nuevo_bolsa_cemento, 
                                               new java.sql.Date(date.getTime()),
                                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(i), 11).toString());
                    acd.actualizarCemento(vistaClientes.tablaCliente.getModel().getValueAt(i, 11).toString(), 
                                          new java.sql.Date(date.getTime()), 
                                          new BigDecimal(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(i), 14).toString()),
                                          nuevo_bolsa_cemento);
                    llenarTabla();
                    log.info(Ventana.apellidoUsuario.getText()+" "+Ventana.nombreUsuario.getText() + " Actualiza bolsa cemento a: " + nuevo_bolsa_cemento.toString());
                    vistaClientes.tablaCliente.getSelectionModel().clearSelection();
                    vistaClientes.fch_actualizacion.setText("");
                    vistaClientes.bolsa_cemento.setText("");
                    vistaClientes.advertencia.setText("");
                    ventana.requestFocusInWindow();
                  }else{
                       JOptionPane.showMessageDialog(null, "Ingrese un valor mayor a cero", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
                  }
                }
                else{
                   JOptionPane.showMessageDialog(null, "Ingrese un valor", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
                }
               }
            }
            //-----Solo permite ingresar numeros y punto en campo bolsa cemento----//
            @Override
            public void keyTyped(KeyEvent e){
             char vchar = e.getKeyChar();
             if(!(Character.isDigit(vchar)) && vchar != '.'){
              e.consume();
             }
            }
        });
        vistaClientes.datosCliente.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos cliente"));
        vistaClientes.datosReferencia.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos referencia"));
        this.vistaClientes.tablaCliente.setDefaultRenderer(Object.class, r);
        this.vistaClientes.tablaCliente.getColumn("Aviso").setCellRenderer(new RendererAviso());
        this.vistaClientes.tablaCliente.getColumn("Actualizacion").setCellRenderer(new RendererActualizacion()); 
        cargarSucesion();        
        llenarComboApellidos();
        desactivarBotones();        
    }

    public ControladorCliente() {
    }    
    
     public void desactivarBotones(){
      if(Ventana.labelTipoUsuario.getText().equals("operador")){
            vistaClientes.bolsa_cemento.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaClientes.imprimirClientes){
            List<ClientesPorCriterio> listaClientes;
            int dias =  Integer.parseInt(vistaClientes.comboDias.getSelectedItem().toString());
            listaClientes = cd.clientesPorPropietarios(apellidos, nombres, dias, lote);
            GenerarLista.generarResumenPdf(listaClientes, dias);
        // vistaClientes.comboNombre.setSelectedIndex(0);                      
        }
        if(e.getSource()==vistaClientes.imprimirClientesOrdenados){
            if(tablePrinter==null){
                tablePrinter = vistaClientes.tablaCliente;
            }
            String barrio = vistaClientes.comboLote.getSelectedItem().toString();
            GenerarLista.generarResumenPdfporTipo(tablePrinter, tipoFiltro, barrio, filtroManzana, filtroParcela);
        // vistaClientes.comboNombre.setSelectedIndex(0);                      
        }
        //-----------Boton mostrar todos los clientes----//
        if(e.getSource()==vistaClientes.mostrarTodos){
            apellidos = ""; 
            vistaClientes.comboApellido.setSelectedIndex(0);
        // vistaClientes.comboNombre.setSelectedIndex(0);
                      
        }
        
        //=======Eventos sobre comboApellido=====//
         if(e.getSource()==vistaClientes.comboApellido){
          if(vistaClientes.comboApellido.getItemCount()!=0){
            if(!vistaClientes.comboApellido.getSelectedItem().equals("Seleccione")){  
                apellidos =vistaClientes.comboApellido.getSelectedItem().toString();
                llenarComboNombres(apellidos);
            }else{
                vistaClientes.comboNombre.setSelectedIndex(0);
            }
          } 
        }
         //----------Eventos sobre combo nombre-------//
         if(e.getSource()==vistaClientes.comboNombre){
           if(vistaClientes.comboNombre.getItemCount()!=0){
            if(!vistaClientes.comboNombre.getSelectedItem().equals("Seleccione")){ 
                nombres = vistaClientes.comboNombre.getSelectedItem().toString();   
                llenarComboLote(nombres, apellidos);
            }else{
                vistaClientes.comboLote.setSelectedIndex(0);
            }
           // llenarTabla();
            }
        }
          //----------Eventos sobre combo lote-------//
         if(e.getSource()==vistaClientes.comboLote){
           if(vistaClientes.comboLote.getItemCount()!=0){
            if(!vistaClientes.comboLote.getSelectedItem().equals("Seleccione")){ 
                lote = vistaClientes.comboLote.getSelectedItem().toString();
            } 
                llenarTabla();         
            
            }
        }
         //-------Evento sobre combo dias--------//
         if(e.getSource()==vistaClientes.comboDias){
             llenarTabla();             
         }
         //-----------Boton cambiar propietario------------//
          if(e.getSource() == vistaClientes.cambiarPropietario){
              int row = vistaClientes.tablaCliente.getSelectedRow();
              //--------Verifico que haya seleccionado alguna fila----------//
              if(row != -1){
                if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){ 
                  String[] options = {"Nuevo", "Existente"};
                  int seleccion = JOptionPane.showOptionDialog(null, "Cambiar propietario..", "Propietario", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                 //---Seleccion es 0 se va a cambiar un cliente actual por un cliente nuevo
                  if(seleccion==0){
                    new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()), true);
                    llenarTabla();}
                  if(seleccion==1){
                     new ControladorPanelClientes((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()));
                     llenarTabla();}
                  if(seleccion==-1){
                     JOptionPane.getRootFrame().dispose(); 
                  }
                 }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente con una propiedad asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);    
                }          
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }}
        //--------Boton agregar propietario-----------//  
        if(e.getSource() == vistaClientes.agregarPropietario){
               int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 11) != null){          
                  new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 11).toString()), 0, false);
                  llenarTabla();
               }else{
                  JOptionPane.showMessageDialog(null, "Seleccione un cliente con un lote asignado", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           } 
        }
        //-----Boton agregar propietario--//
        if(e.getSource() == vistaClientes.agregarBtn){  
            new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), 0,0, false);
            llenarTabla();
         }
        //----------Boton eliminar cliente---------//
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.tablaCliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
                  int dni = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString());
                  cd.eliminarCliente(dni);
                  llenarTabla();
               } 
              }
              else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
        }
        //--------Boton editar cliente---------//
        if(e.getSource() == vistaClientes.editarBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
            if(row!=-1){
            cliente.clear();
            referencia.clear();
            for (int i = 0; i < 14; i++) {
                cliente.add(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), i));
            }
            referencia.add(vistaClientes.apellido_referencia.getText());
            referencia.add(vistaClientes.nombre_referencia.getText());
            referencia.add(vistaClientes.telefono_referencia.getText());
            referencia.add(vistaClientes.parentesco.getText());
            new ControladorEditarCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), cliente, referencia);
            llenarTabla();
            } else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
          }
        //----------Boton detalle de pago------//
         if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           Object rowData = ((DefaultTableModel) vistaClientes.tablaCliente.getModel()).getDataVector().elementAt(vistaClientes.tablaCliente.getSelectedRow());
           if(row != -1){
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11) != null){
                   String tipo_actualizacion = vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 19).toString();
                   try {
                       new ControladorDetalleCuota(datosCliente,
                               Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 12).toString()),
                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0).toString(), //apellido
                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1).toString(),//nombre
                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 3).toString(),//telefono
                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString(),//barrio
                               vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString(),//calle
                               Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString()),//numero
                               Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()),//idcontrol
                               Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 10).toString()),//bajalogica
                               tipo_actualizacion,
                               this);
                   } catch (SQLException ex) {
                       java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }else{
                  JOptionPane.showMessageDialog(null, "Debe asignar una propiedad para ver los detalles", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }           
           }
        //---------Boton asignar lote----------//
        if (e.getSource() == vistaClientes.asignarBtn) {
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               //-----Controlo si ya tiene asignada una propiedad-----------//
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11)!=null){
                   int reply = JOptionPane.showConfirmDialog(null, "Desea agregar un nuevo lote a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                 "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                  if (reply == JOptionPane.YES_OPTION) {  
                   //------Paso el dni para crear la ficha de control---------//
                 new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
                 llenarTabla();
                }
               }else{
                 new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
                 llenarTabla(); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }  
        }        
        //----------Boton dar de baja cliente-----//
        if(e.getSource()==vistaClientes.bajaBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 10) != null){
                 int reply = JOptionPane.showConfirmDialog(null, "Dar de baja a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                 "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                  if (reply == JOptionPane.YES_OPTION) {  
                    fd.bajaPropietario(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()));
                    ld.editarPropiedad(0, 
                            vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 16).toString(), 
                            vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 17).toString(), 
                            vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 18).toString());
                    llenarTabla();
                  }
                   }else{
                     JOptionPane.showMessageDialog(null, "Seleccione un cliente con una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                     }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente con propiedad asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }
        }
    }    
    
    public void llenarComboApellidos(){
        try {
            //-------Obtengo el propietario seleccionado por default, para mostrar los clientes de ese propietario----//
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            apellidos = props.getProperty("apellidoPropietario");
            nombres = props.getProperty("nombrePropietario");
            List<Propietario> propietarios;
            propietarios = pd.obtenerApellidos();
            vistaClientes.comboApellido.removeAllItems();
            vistaClientes.comboApellido.addItem("Seleccione");            
            for (int i = 0; i < propietarios.size(); i++) {   
                vistaClientes.comboApellido.addItem(propietarios.get(i).getApellidos());
            }
            vistaClientes.comboApellido.setSelectedItem(apellidos);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        
     }   
      public void llenarComboNombres(String apellidos){
            List<Propietario>propietarios = null;        
            propietarios = pd.obtenerNombres(apellidos);
            vistaClientes.comboNombre.removeAllItems();
            vistaClientes.comboNombre.addItem("Seleccione");
            for(int i=0; i< propietarios.size();i++) {
               vistaClientes.comboNombre.addItem(propietarios.get(i).getNombres());
            }
            vistaClientes.comboNombre.setSelectedItem(nombres);
           // llenarTabla();
     }
      
        public void llenarComboLote(String nombre, String apellidos){
            List<Lote>lotes = null;        
            lotes = ld.obtenerLotesPorGrupo(apellidos, nombre);
            vistaClientes.comboLote.removeAllItems();
            vistaClientes.comboLote.addItem("Seleccione");
            for(int i=0; i< lotes.size();i++) {
               vistaClientes.comboLote.addItem(lotes.get(i).getBarrio());
            }
            vistaClientes.comboLote.setSelectedIndex(1);
          //  llenarTabla();
     }
    
      public void llenarTabla(){     
          new AnswerWorker().execute();  
      }
      
      class AnswerWorker extends SwingWorker<Void, Void>   {
            
        List<ClientesPorCriterio> listaClientes;
        ProgressDialog pd = new ProgressDialog();
            
        protected Void doInBackground() throws Exception{
        
        pd.setVisible(true);
        if(apellidos.equals("")){
            listaClientes = cd.clientesPorLotes();}
        else{
           int dias =  Integer.parseInt(vistaClientes.comboDias.getSelectedItem().toString());
           listaClientes = cd.clientesPorPropietarios(apellidos, nombres, dias, lote);
        }  
            return null;
        }
        protected void done(){
        
        pd.dispose();
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        JLabel icono;
        String fch_actualizacion = "";
        String actualizar_cemento = "";
        String cumpleaños;
        String tipoActualizacion;
        Date date = new Date();
        DateTime current_date = new DateTime();
        DateTime joda_suscription_time, joda_actualizacion_time;
        Date fechaActualizacion, fechaSuscripcion, bandera;
        LocalDate fecha_actual = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        vistaClientes.nroClientes.setText(String.valueOf(listaClientes.size()));
        try {
            if(listaClientes.size()>0){
                for (int i = 0; i < listaClientes.size(); i++) {
                   actualizar_cemento = "0";
                   tipoActualizacion = "Cemento" ;
                   icono = new JLabel();
                   icono.setIcon(null);
                   cumpleaños = "0";
                   int dni = listaClientes.get(i).getDni();
                   String apellidos = listaClientes.get(i).getApellidos();
                   String nombres = listaClientes.get(i).getNombres();
                   Instant fchSuscripcion, banderaInstant;
                   Date fecha_nacimiento = listaClientes.get(i).getFecha_nacimiento();
                    //------Controlo dia y mes para saber si es el cumpleaños--------//
                   Instant instant = Instant.ofEpochMilli(fecha_nacimiento.getTime());
                   LocalDate fch_nacimiento =  LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                   if(fecha_actual.getMonthValue() == fch_nacimiento.getMonthValue() && fecha_actual.getDayOfMonth() == fch_nacimiento.getDayOfMonth()){
                     cumpleaños = "1";
                   }
                   String barrio = listaClientes.get(i).getBarrio_cliente();
                   String calle = listaClientes.get(i).getCalle_cliente();
                   int numero = listaClientes.get(i).getNro_cliente();
                   String telefono1 = listaClientes.get(i).getTelefono1();
                   String telefono2 = listaClientes.get(i).getTelefono2();
                   String trabajo = listaClientes.get(i).getTrabajo();
                   int baja = listaClientes.get(i).getBaja();
                   String idControl = listaClientes.get(i).getIdControl();
                   int cantidad_cuotas = listaClientes.get(i).getCantidad_cuotas();
                   BigDecimal gastos = listaClientes.get(i).getGastos();
                   BigDecimal bolsa_cemento = listaClientes.get(i).getBolsa_cemento();
                   //-----Aplico solo para clientes con bolsa de cemento----//
                   if(listaClientes.get(i).getFecha_actualizacion()!=null && listaClientes.get(i).getBandera_cemento()==1){
                      bandera = listaClientes.get(i).getBandera();
                      banderaInstant = Instant.ofEpochMilli(bandera.getTime());
                      fechaActualizacion = listaClientes.get(i).getFecha_actualizacion();
                      fechaSuscripcion = listaClientes.get(i).getFecha_suscripcion();
                      joda_suscription_time = new DateTime(bandera);
                      joda_actualizacion_time = new DateTime(fechaActualizacion);
                      fchSuscripcion = Instant.ofEpochMilli(fechaSuscripcion.getTime());
                      fch_actualizacion = sdf.format(fechaActualizacion);
                      //----Controlo si ya paso 6, 18,30 meses de la ultima fecha de actualizacion de la bolsa de cemento----//
                      int difMeses = (Months.monthsBetween(joda_actualizacion_time, current_date)).getMonths();
                      if(difMeses >= 12 || (fechaActualizacion.equals(fechaSuscripcion) && (Months.monthsBetween(joda_suscription_time, current_date)).getMonths() >= 6)){
                            actualizar_cemento = "1"; 
                        }        
                                  
                        int monthsBetween = Months.monthsBetween(joda_suscription_time, current_date).getMonths();     
                        //----Getmonths devuelve desde 0 a 11 meses, luego empieza nuevamente desde 0---//
                        //------ Actualizo cuota y saldo-------------//
                        
                        if(monthsBetween >= 12  ){
                         actualizar_cemento = "2";
                        }
                     }else{
                        fch_actualizacion = "";
                        actualizar_cemento = "0";
                     } 
                    String barrio_prop = listaClientes.get(i).getBarrio();
//                    if(listaClientes.get(i).getManzana()!=null){
//                        if(IsInteger.isInteger(listaClientes.get(i).getManzana())){
//                         Integer manzana_prop = listaClientes.get(i).getManzana()!=null?Integer.parseInt(listaClientes.get(i).getManzana()):0;
//                         Integer parcela_prop = listaClientes.get(i).getManzana()!=null?Integer.parseInt(listaClientes.get(i).getParcela()):0;
//                        };
//                    }else{
//                        Integer manzana_prop = listaClientes.get(i).getManzana()!=null?Integer.parseInt(listaClientes.get(i).getManzana()):0;
//                        Integer parcela_prop = listaClientes.get(i).getManzana()!=null?Integer.parseInt(listaClientes.get(i).getParcela()):0;
//                            
//                    }
                        String manzana_prop = listaClientes.get(i).getManzana();
                        String parcela_prop = listaClientes.get(i).getParcela();
                       
                    switch(listaClientes.get(i).getBandera_cemento()){
                        case 0: tipoActualizacion = "Emp. Público"; break;
                        case 2: tipoActualizacion = "C. Fija"; break;
                        case 3: tipoActualizacion = "C. fija vble."; break;
                        default: break;
                    }
                    BigDecimal cuota_pura = listaClientes.get(i).getCuota_pura();   
                    int nroCuota = listaClientes.get(i).getCuotas();
                    String ultimaCuota = sdf.format(listaClientes.get(i).getUltimaCuota());
                    BigDecimal total = listaClientes.get(i).getTotal();
                    clientes = new Object[] {apellidos, nombres, dni, telefono1, telefono2, barrio, calle, numero, fecha_nacimiento, trabajo, baja, idControl, cantidad_cuotas, gastos, bolsa_cemento, fch_actualizacion, barrio_prop, manzana_prop, parcela_prop, tipoActualizacion, actualizar_cemento, cumpleaños, cuota_pura, "", nroCuota, ultimaCuota, total};
                    model.addRow(clientes);                     
                    }
            }
            controlCumpleaños();                
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       }
    }
   
    
    public void cargarSucesion(){
      for(int n = 6; n < 300; n= n+12 )  {
      sucesion.add(n);}
    }  
    
    private void controlCumpleaños(){
        //-----Limpio la lista de cumpleaños------//
        dialogCumpleaños = Cumpleaños.getInstance(ventana, true);
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        DefaultTableModel modeloCumpleaños = (DefaultTableModel) dialogCumpleaños.tablaCumpleaños.getModel();
        modeloCumpleaños.setRowCount(0);
        //----Verifico que la tabla de listaClientes tenga al menos una fila--------//
        if(model.getRowCount()!=0){            
        //------Si hay cumpleaños en el dia, habilito el boton para mostrar los cumpleaños--------//
        if(modeloCumpleaños.getRowCount()!=0){
            Ventana.btnCumpleaños.setVisible(true);
        }
       }  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();  
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 9).toString());  
        vistaClientes.telefono.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 3).toString()+"_"+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 4).toString());
        //-------Si no tiene propiedad asignada entonces este valor va a ser nulo--------//
        if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 13)!=null){
            //----Muestro valor bolsa de cemento y fecha de actualizacion---------//
            vistaClientes.fch_actualizacion.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 15).toString());
            vistaClientes.bolsa_cemento.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 14).toString());
        }else{
            vistaClientes.fch_actualizacion.setText("");
            vistaClientes.bolsa_cemento.setText("");
        }
        //----Si tengo un 1 ha pasado un año o mas y tengo que actualizar precio bolsa de cemento-----//
        if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 20).toString().equals("1")){
            vistaClientes.advertencia.setText("Actualizar precio bolsa cemento");
//            vistaClientes.detalleBtn.setEnabled(false);
        }else{
            vistaClientes.advertencia.setText("");
            vistaClientes.detalleBtn.setEnabled(true);
        }
         List<Referencia> listaReferencia;
        listaReferencia = new ArrayList<>();
        listaReferencia = rd.obtenerReferencia(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
        if(!listaReferencia.isEmpty()){
            for (int i = 0; i < listaReferencia.size(); i++) {
                vistaClientes.apellido_referencia.setText(listaReferencia.get(i).getApellidos());
                vistaClientes.nombre_referencia.setText(listaReferencia.get(i).getNombres());
                vistaClientes.telefono_referencia.setText(listaReferencia.get(i).getTelefono());
                vistaClientes.parentesco.setText(listaReferencia.get(i).getParentesco()); 
            }
        }else{
                vistaClientes.apellido_referencia.setText("");
                vistaClientes.nombre_referencia.setText("");
                vistaClientes.telefono_referencia.setText("");
                vistaClientes.parentesco.setText(""); 
        }
        
    }    
    

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    
    private void filter(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
    
    private void filtroBarrio(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,16));
    }
    
     private void filtroParcela(String query){
        DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
        if(!query.isEmpty()){
            if(!IsInteger.isInteger(query)){
               tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,18));  
             }else{
               tr.setRowFilter(RowFilter.regexFilter("(^|\\s)" + Integer.parseInt(query) +"(\\s|$)" ,18));
             } 
          vistaClientes.tablaCliente.setRowSorter(tr);
          tr.setComparator(17, new Comparator<String>() {
          @Override
          public int compare(String o1, String o2)
          {
            return Integer.parseInt(o1) - Integer.parseInt(o2);
          }
          });
          vistaClientes.tablaCliente.getRowSorter().toggleSortOrder(17);
          tablePrinter = vistaClientes.tablaCliente;
          tipoFiltro = "Pc. "+query; 
          filtroParcela = true;
        }else{
          vistaClientes.tablaCliente.setRowSorter(null);
        }
    }
    private void filtroManzana(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();         
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setModel(table);
         if(!query.isEmpty()){
             if(!IsInteger.isInteger(query)){
               tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,17));  
             }else{
               tr.setRowFilter(RowFilter.regexFilter("(^|\\s)" + Integer.parseInt(query) +"(\\s|$)" ,17));
             }                
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setComparator(18, new Comparator<String>() {
        @Override
        public int compare(String o1, String o2)
        {
            return Integer.parseInt(o1) - Integer.parseInt(o2);
        }
        });
        vistaClientes.tablaCliente.getRowSorter().toggleSortOrder(18);
        tablePrinter = vistaClientes.tablaCliente;
        tipoFiltro = "Mz. "+query;
        filtroManzana = true;
        }else{
         vistaClientes.tablaCliente.setRowSorter(null);
        }
    }

    private void filtroTipoCuota(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,19));
         tablePrinter = vistaClientes.tablaCliente;
         tipoFiltro = vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(0), 19).toString();
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();
        if(row!=-1){
          if (e.getType() == TableModelEvent.UPDATE) {
              fd.actualizarObservacion(vistaClientes.tablaCliente.getValueAt(row, 19).toString() , Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 11).toString()));
          } 
        }  
    }
    
    private void generarResumenPdf(){
            JFrame parentFrame = new JFrame(); 
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar en..");  
            File fileToSave = null;
            int userSelection = fileChooser.showSaveDialog(parentFrame); 
            if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
            }
            List<ClientesPorCriterio> listaClientes;
            listaClientes = cd.clientesPorPropietarios(apellidos, nombres, 0, lote);
            Document document= new Document(PageSize.A4);
            DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = new java.util.Date();
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,8.0f,0,null);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(fileToSave.getAbsolutePath()+".pdf")));
            document.open();       
            Image image = Image.getInstance(IMG); 
            image.scaleAbsolute(70, 70);
            document.add(new Chunk(image, 0, -55f));
            Chunk titulo = new Chunk("Lista de clientes");
            titulo.setUnderline(0.1f, -2f); 
            Phrase ph1 = new Phrase(titulo);
            Phrase ph2 = new Phrase("    "+dateFormat2.format(date));
            Paragraph ph = new Paragraph();
            ph.add(ph1);
            ph.add(ph2);
            ph.setAlignment(Element.ALIGN_CENTER);
            Paragraph total = new Paragraph("Total clientes: "+listaClientes.size());
            total.setAlignment(Element.ALIGN_CENTER);
            document.add(ph);
            document.add( Chunk.NEWLINE );
            document.add(total);
            document.add( Chunk.NEWLINE );
            //------Cabeceras de las columnas de las cuotas---------//
            if(!listaClientes.isEmpty()){//---Si la tabla tiene 1 fila no imprimo cabeceras----//
            
            PdfPTable table = new PdfPTable(7); 
            table.setTotalWidth(new float[]{ 2,2,1,1,1,1,1});
            table.setWidthPercentage(100);
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Apellido",f));
            PdfPCell fecha_pago = new PdfPCell(new Paragraph("Nombre/s",f));
            PdfPCell monto_cuota = new PdfPCell(new Paragraph("Barrio",f));
            PdfPCell saldo = new PdfPCell(new Paragraph("Mz - Pc",f));            
            PdfPCell cemento_saldo = new PdfPCell(new Paragraph("Total cuotas",f));
            PdfPCell ultima_cuota = new PdfPCell(new Paragraph("Ultima cuota",f));
            PdfPCell total_cuotas = new PdfPCell(new Paragraph("Total $",f));
            nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            fecha_pago.setHorizontalAlignment(Element.ALIGN_CENTER);
            monto_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cemento_saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            ultima_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            total_cuotas.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nro_cuota);
            table.addCell(fecha_pago);
            table.addCell(monto_cuota);
            table.addCell(saldo);
            table.addCell(cemento_saldo);
            table.addCell(ultima_cuota);
            table.addCell(total_cuotas);
            document.add(table);            
            for(int i = 1; i < listaClientes.size(); i++){
                  PdfPTable table2 = new PdfPTable(7);            
                  table2.setTotalWidth(new float[]{ 2,2,1,1,1,1,1});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getApellidos()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getNombres()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getBarrio()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getManzana()+" - "+listaClientes.get(i).getParcela()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getCuotas()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getUltimaCuota()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getTotal()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  document.add(table2);
              }
            }
            document.close();
        }            
            catch (DocumentException ex) {
            java.util.logging.Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar. Compruebe que no tiene abierto actualmente el archivo"+ex.getMessage());
            java.util.logging.Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);        
         }
      }
    
}
