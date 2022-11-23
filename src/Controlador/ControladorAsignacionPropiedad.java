/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Lote;
import Clases.Propietario;
import Clases.Vendedor;
import Modelo.ClienteDAO;
import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.DepartamentoDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.PropietarioDAO;
import Modelo.VendedorDAO;
import Vista.Dialogs.AsignarPropiedad;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAsignacionPropiedad implements ActionListener, KeyListener{
    
    private Frame parent;    
    AsignarPropiedad vistaAsignarPropiedad ;
    LoteDAO ld = new LoteDAO();
    DepartamentoDAO dd = new DepartamentoDAO();
    PropietarioDAO pd = new PropietarioDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    FichaControlDAO fichaControlDAO = new FichaControlDAO();
    CuotaDAO cuotaDao = new CuotaDAO();
    ClienteDAO cd = new ClienteDAO();
    VendedorDAO vd = new VendedorDAO();
    ControladorCliente cc = new ControladorCliente();
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("registro");
    int dni;

    public ControladorAsignacionPropiedad(Frame parent, int dni) {
        vistaAsignarPropiedad = new AsignarPropiedad(parent, true);
        this.parent=parent;
        this.dni=dni;
        vistaAsignarPropiedad.tipo_actualizacion.add(vistaAsignarPropiedad.bolsaCemento);
        vistaAsignarPropiedad.tipo_actualizacion.add(vistaAsignarPropiedad.empPublico);  
        vistaAsignarPropiedad.tipo_actualizacion.add(vistaAsignarPropiedad.cuota_fija);
        vistaAsignarPropiedad.tipo_actualizacion.add(vistaAsignarPropiedad.cuota_fija_vble);
        //--------Button group del indice corrector inflacionario----//
        vistaAsignarPropiedad.indice_corrector.add(vistaAsignarPropiedad.indice_no);
        vistaAsignarPropiedad.indice_corrector.add(vistaAsignarPropiedad.indice_si);
        vistaAsignarPropiedad.aceptarBtn.addActionListener(this);
        vistaAsignarPropiedad.cancelarBtn.addActionListener(this);
        vistaAsignarPropiedad.bolsa_cemento.addKeyListener(this);
        vistaAsignarPropiedad.tipo_propiedad.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                llenarComboApellidos(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString());
            }
        });
        vistaAsignarPropiedad.apellido_propietario.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               //-----Solo llamo al evento cuando un item es seleccionado en el combobox, no cuando es cargado o eliminado----//  
              if (e.getStateChange() == ItemEvent.SELECTED) {
                if(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString()!=("Seleccione") && vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()!=("Seleccione") );
                 llenarComboNombres(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(), vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString());
            }}
        });
        vistaAsignarPropiedad.nombre_propietario.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                //-----Solo llamo al evento cuando un item es seleccionado, no cuando es cargado o eliminado----//
              if (e.getStateChange() == ItemEvent.SELECTED) {
                switch (vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
                case "Terreno": cargarBarrios(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString(), vistaAsignarPropiedad.nombre_propietario.getSelectedItem().toString()); break;
                case "Departamento": cargarTorres(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString(), vistaAsignarPropiedad.nombre_propietario.getSelectedItem().toString());break;
              }
             }
            }
        });
        vistaAsignarPropiedad.barrio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                cargarManzanas(vistaAsignarPropiedad.barrio.getSelectedItem().toString());
                }
            }
        });
        vistaAsignarPropiedad.manzana.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                  if(vistaAsignarPropiedad.manzana.getSelectedItem() != null){
                   cargarParcelas(vistaAsignarPropiedad.barrio.getSelectedItem().toString(), vistaAsignarPropiedad.manzana.getSelectedItem().toString());
                  }
                }
             }
        });     
    vistaAsignarPropiedad.empPublico.addItemListener(new ItemListener() { 
    @Override
     public void itemStateChanged(ItemEvent event) {
        int state = event.getStateChange();
        if (state == ItemEvent.SELECTED) {
            vistaAsignarPropiedad.indice_no.setEnabled(true);
            vistaAsignarPropiedad.indice_si.setEnabled(true);
        } else if (state == ItemEvent.DESELECTED) {
            vistaAsignarPropiedad.indice_no.setEnabled(false);
            vistaAsignarPropiedad.indice_si.setEnabled(false);
        }
      }
      });
        llenarComboApellidos(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString());
        cargarComboVendendores();
        vistaAsignarPropiedad.pack();
        vistaAsignarPropiedad.setVisible(true);   
        vistaAsignarPropiedad.mensaje_error.setText("");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource() == vistaAsignarPropiedad.aceptarBtn){
              if(validarCampos() == true){                  
                  new SwingWorker().execute();                
              }
          }
          if(e.getSource() == vistaAsignarPropiedad.cancelarBtn){
             vistaAsignarPropiedad.dispose();
          }
    }
    
     public void llenarComboApellidos(String propiedad){
        try {
            ResultSet rs=null;
            switch (propiedad){
                case "Terreno": rs = pd.obtenerApellidosXLote(); break;
                case "Departamento": rs = pd.obtenerApellidosXDepartamento();break;
            }
            vistaAsignarPropiedad.apellido_propietario.removeAllItems();
            vistaAsignarPropiedad.apellido_propietario.addItem("Seleccione");
            if(rs!=null){
            while (rs.next()) {
                vistaAsignarPropiedad.apellido_propietario.addItem(rs.getString(1));                
            }  
            rs.close();}
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 } 
     
      public void llenarComboNombres(String propiedad, String apellido){        
            List<Propietario>propietarios = null;
            switch (propiedad){
                case "Terreno": propietarios = pd.obtenerNombresXLote(apellido); break;
                case "Departamento": propietarios = pd.obtenerNombresXDepartamento(apellido);break;
            }            
            vistaAsignarPropiedad.nombre_propietario.removeAllItems();
            vistaAsignarPropiedad.nombre_propietario.addItem("Seleccione");
            if(propietarios!=null){
              for(int i=0;i< propietarios.size();i++){
                vistaAsignarPropiedad.nombre_propietario.addItem(propietarios.get(i).getNombres());                
              } 
            }
 } 
    
    public void cargarBarrios(String apellidos, String nombres){
     List<Lote>lotes=null;
     lotes = ld.obtenerBarrios(apellidos, nombres);
      vistaAsignarPropiedad.barrio.removeAllItems();
        try {
            if(lotes!=null){
                for(int i = 0; i < lotes.size(); i++){
                  vistaAsignarPropiedad.barrio.addItem(lotes.get(i).getBarrio());
             }
            }else{
               vistaAsignarPropiedad.barrio.addItem("Sin propiedades");
            }
        } catch (Exception e) {
        }
    }
    public void cargarManzanas(String barrio){
        List<Lote>lotes=null;
        lotes = ld.manzanasPorBarrio(barrio);        
        vistaAsignarPropiedad.manzana.removeAllItems();
        try {
            if(lotes!=null){
                for(int i = 0; i < lotes.size(); i++){
                  vistaAsignarPropiedad.manzana.addItem(String.valueOf(lotes.get(i).getManzana()));
             }
            }else{
               vistaAsignarPropiedad.manzana.addItem("Sin propiedades");
            }
        } catch (Exception e) {
        }
    }
    public void cargarParcelas(String barrio, String manzana){
        List<Lote>lotes=null;
        lotes = ld.parcelasPorManzana(barrio, manzana);
        vistaAsignarPropiedad.parcela.removeAllItems();
        try {
            if(lotes!=null){
                for(int i = 0; i < lotes.size(); i++){
                  vistaAsignarPropiedad.parcela.addItem(String.valueOf(lotes.get(i).getParcela()));
             }
            }else{
               vistaAsignarPropiedad.comboCanalVenta.addItem("Sin propiedades");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar parcelas: "+e.getMessage());
        }
    }
    
    public void cargarTorres(String apellidos, String nombres){
     ResultSet rs = dd.obtenerTorres(apellidos, nombres);
      vistaAsignarPropiedad.barrio.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.barrio.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarPisos(String torre){
        ResultSet rs = dd.pisosPortorre(torre);        
        vistaAsignarPropiedad.manzana.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.manzana.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarNroDptos(String torre, int piso){
         ResultSet rs = dd.dptosPorpiso(torre, piso);
        vistaAsignarPropiedad.comboCanalVenta.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.comboCanalVenta.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
     
     public boolean validarCampos(){
        boolean bandera = true; 
        if(vistaAsignarPropiedad.suscripcion.getText().isEmpty()){
         vistaAsignarPropiedad.suscripcion.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.suscripcion.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.cantidad_cuotas.getText().isEmpty()){
         vistaAsignarPropiedad.cantidad_cuotas.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.cantidad_cuotas.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.cuota_total.getText().isEmpty()){
         vistaAsignarPropiedad.cuota_total.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.cuota_total.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }        
        if(vistaAsignarPropiedad.dimension.getText().isEmpty()){
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        } 
       
         if(vistaAsignarPropiedad.bolsa_cemento.getText().isEmpty() ){
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.fch_suscripción.getDate()== null){
         vistaAsignarPropiedad.fch_suscripción.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         vistaAsignarPropiedad.fch_suscripción.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.comboCanalVenta.getSelectedItem()==null || (!vistaAsignarPropiedad.bolsaCemento.isSelected() && !vistaAsignarPropiedad.empPublico.isSelected() && !vistaAsignarPropiedad.cuota_fija.isSelected() && !vistaAsignarPropiedad.cuota_fija_vble.isSelected())){
         vistaAsignarPropiedad.mensaje_error.setText("Complete todos los campos");
         bandera=false;
        }else{
         vistaAsignarPropiedad.mensaje_error.setText("");
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            vistaAsignarPropiedad.aceptarBtn.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void cargarComboVendendores() {
        List<Vendedor> lVendedores = vd.obtenerVendedores();
        Vendedor [] aVendedores = new Vendedor [lVendedores.size()];
        for(int i =0; i< lVendedores.size(); i++){
            aVendedores[i] = lVendedores.get(i);
        }
        vistaAsignarPropiedad.comboVendedor.setModel(new DefaultComboBoxModel(aVendedores));
    }
     
    public class SwingWorker extends javax.swing.SwingWorker<Integer, Integer>{
         
         int id_control;
         long fecha_suscripcion = vistaAsignarPropiedad.fch_suscripción.getDate().getTime();

        @Override
        protected Integer doInBackground() throws Exception { 
           byte flag_cemento = 1; 
           byte flag_indice = 1;
           if(vistaAsignarPropiedad.empPublico.isSelected()){
               flag_cemento = 0;
           }else if(vistaAsignarPropiedad.cuota_fija.isSelected()){
               flag_cemento = 2;
           }else if(vistaAsignarPropiedad.cuota_fija_vble.isSelected()){
               flag_cemento = 3;
           }
           if(vistaAsignarPropiedad.indice_no.isSelected()){
               flag_indice = 0;
           }
           BigDecimal gastos =new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()).subtract((new BigDecimal(vistaAsignarPropiedad.cuota_total.getText())).divide(new BigDecimal(1.1),2, BigDecimal.ROUND_HALF_UP));
           id_control = fichaControlDAO.altaFichaControl(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(),
                   vistaAsignarPropiedad.dimension.getText(), 
                   Integer.parseInt(vistaAsignarPropiedad.cantidad_cuotas.getText()),  
                   new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()).subtract(gastos), 
                   gastos, 
                   flag_cemento, 
                   new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()), 
                   new java.sql.Date(fecha_suscripcion),
                   String.valueOf(vistaAsignarPropiedad.barrio.getSelectedItem()),
                   (String)vistaAsignarPropiedad.manzana.getSelectedItem(), 
                   (String)vistaAsignarPropiedad.parcela.getSelectedItem(), 
                   new java.sql.Date(fecha_suscripcion), 
                   flag_indice,
                   ((Vendedor)vistaAsignarPropiedad.comboVendedor.getSelectedItem()).getDni(),
                   vistaAsignarPropiedad.comboCanalVenta.getSelectedItem().toString(),
                   new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()));
           return id_control;
        }

       @Override
       public void done() { 
           int idControl = 0;
             try {
                 idControl = get();
             } catch (InterruptedException | ExecutionException ex) {
                 Logger.getLogger(ControladorAsignacionPropiedad.class.getName()).log(Level.SEVERE, null, ex);
             }
           if(idControl!=0){  
             log.info(Ventana.nombreUsuario.getText()+" - Asigna propiedad - id control: "+id_control + "Mz:"+vistaAsignarPropiedad.manzana.getSelectedItem()+" Pc:"+vistaAsignarPropiedad.comboCanalVenta.getSelectedItem());
             BigDecimal saldo = new BigDecimal(vistaAsignarPropiedad.cantidad_cuotas.getText()).multiply(new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()));
             switch(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
               case "Terreno":cuotaDao.altaCuotaLote(new java.sql.Timestamp(fecha_suscripcion), 0,"Saldo Inicio", new BigDecimal(0),new BigDecimal(0), saldo , new BigDecimal(0), saldo, saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()),2, BigDecimal.ROUND_HALF_UP), new BigDecimal(0), saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()),2, BigDecimal.ROUND_HALF_UP), "", "", id_control, 0, BigDecimal.ZERO); break;
               case "Departamento":cuotaDao.altaCuotaDpto(new java.sql.Date(fecha_suscripcion), 0,"Saldo Inicio", new BigDecimal(0),new BigDecimal(0), saldo , new BigDecimal(0), saldo, saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), new BigDecimal(0), saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), "", "", id_control);  break;
           }
           BigDecimal saldo_dcho_posesion = (saldo.multiply(new BigDecimal (50))).divide(new BigDecimal(100));
           dp.altaDchoPosesion(new java.sql.Date(fecha_suscripcion), new BigDecimal(0),new BigDecimal(0), saldo_dcho_posesion , new BigDecimal(0),saldo_dcho_posesion.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()), 2, RoundingMode.DOWN), "Saldo Inicio", id_control);
           
           switch(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
               case "Terreno":ld.editarPropiedad(1, 
                       vistaAsignarPropiedad.barrio.getSelectedItem().toString(), 
                       vistaAsignarPropiedad.manzana.getSelectedItem().toString(), 
                       vistaAsignarPropiedad.parcela.getSelectedItem().toString()); 
               {
                try {
                    cd.altaClientesXLotes(dni, id_control);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAsignacionPropiedad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
          }           
           vistaAsignarPropiedad.dispose();
           }else{
               vistaAsignarPropiedad.mensaje_error.setText("Error, revise los datos.");
           }
       }
    
}
     
}
