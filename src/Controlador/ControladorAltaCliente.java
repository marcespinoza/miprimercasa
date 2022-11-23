/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Clases.LimitadorCaracteres;
import Modelo.ReferenciaDAO;
import Vista.Dialogs.AltaCliente;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */

public class ControladorAltaCliente implements ActionListener, KeyListener{
        
    AltaCliente ac;
    FichaControlDAO fd = new FichaControlDAO();
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    private int id_control, dni=0;
    boolean bandera_=false;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("registro");
    
    public ControladorAltaCliente(Ventana ventana, int id_control,int dni, boolean bandera_){
        ac = new AltaCliente(ventana, true);  
        this.id_control=id_control;
        this.dni=dni;
        this.bandera_=bandera_;
        this.ac.aceptar.addActionListener(this);
        this.ac.cancelar.addActionListener(this);
        this.ac.parentescoRef.addKeyListener(this);
        this.ac.documento.addKeyListener(this);
        this.ac.numero.addKeyListener(this);
        this.ac.documento.setDocument(new LimitadorCaracteres(8));
        this.ac.apellidos.setDocument(new LimitadorCaracteres(30));
        this.ac.nombres.setDocument(new LimitadorCaracteres(30));
        this.ac.barrio.setDocument(new LimitadorCaracteres(30));
        this.ac.calle.setDocument(new LimitadorCaracteres(40));
        this.ac.numero.setDocument(new LimitadorCaracteres(5));
        this.ac.telefono1.setDocument(new LimitadorCaracteres(12));
        this.ac.telefono2.setDocument(new LimitadorCaracteres(12));        
        this.ac.trabajo.setDocument(new LimitadorCaracteres(20));
        this.ac.apellidosRef.setDocument(new LimitadorCaracteres(30));
        this.ac.nombresRef.setDocument(new LimitadorCaracteres(30));
        this.ac.telefonoRef.setDocument(new LimitadorCaracteres(12));
        this.ac.parentescoRef.setDocument(new LimitadorCaracteres(15));
        ac.setVisible(true);
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ac.aceptar){
            if(validarCampos()){ 
             new AltaClienteSwing().execute();
            }
        }
          if(e.getSource() == ac.cancelar){
                ac.dispose();
        }
    }
 
    
    public boolean validarCampos(){
        boolean bandera = true;       
        if(ac.apellidos.getText().isEmpty()){
         ac.apellidos.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.apellidos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombres.getText().isEmpty()){
         ac.nombres.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.nombres.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.fecha_nac.getText().trim().length()==4){
         ac.fecha_nac.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.fecha_nac.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.documento.getText().isEmpty()){
         ac.documento.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.documento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.barrio.getText().isEmpty()){
         ac.barrio.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.calle.getText().isEmpty()){
         ac.calle.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.calle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.telefono1.getText().isEmpty()){
         ac.telefono1.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.telefono1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource()==ac.documento){
             char vchar = e.getKeyChar();
             if(!(Character.isDigit(vchar))){
              e.consume();             
            }
        }
         if(e.getSource()==ac.numero){
             char vchar = e.getKeyChar();
             if(!(Character.isDigit(vchar))){
              e.consume();             
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            ac.aceptar.doClick();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public class AltaClienteSwing extends javax.swing.SwingWorker<Void, Void>{         

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        int alta = 0;
        
        @Override
        protected Void doInBackground() throws Exception {
            Date fecha =df.parse(ac.fecha_nac.getText());
            alta = cd.altaCliente(Integer.parseInt(ac.documento.getText()), ac.apellidos.getText(), ac.nombres.getText(),new java.sql.Date(fecha.getTime()), ac.barrio.getText(), ac.calle.getText(), ac.numero.getText(), ac.telefono1.getText(), ac.telefono2.getText(), ac.trabajo.getText(), ac.cuil.getText());     
            return null;
        }

       @Override
       public void done() { 
           //--------Si alta es igual a 1 el cliente fue agregado-----------//
           if(alta==1){
             log.info(Ventana.nombreUsuario.getText() + " - Alta cliente");
           if(bandera_){
             fd.cambiarPropietario(Integer.parseInt(ac.documento.getText()), dni, id_control);
            }
              rd.altaReferencia(ac.telefonoRef.getText(), ac.apellidosRef.getText(), ac.nombresRef.getText(), ac.parentescoRef.getText(), Integer.parseInt(ac.documento.getText()));
              //------Si el id_control es distinto de cero y bandera falso, entonces estoy agregando un propietario mas-----//
              //------a un lote que ya posee un propietario-------------------------------------------------//
            if(id_control!=0 && !bandera_){                
               try {
                   cd.altaClientesXLotes(Integer.parseInt(ac.documento.getText()), id_control);
               } catch (SQLException ex) {
                   Logger.getLogger(ControladorAltaCliente.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            ac.dispose();
           } else{
            ac.aviso.setText("*Documento duplicado");
           }
       }
    
}
    
}
