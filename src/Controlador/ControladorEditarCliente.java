/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.ReferenciaDAO;
import Vista.Dialogs.AltaCliente;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorEditarCliente implements ActionListener{
    AltaCliente ac;
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    ControladorCliente cc = new ControladorCliente();
    List <Object> cliente;
    List <Object> referencia;
    
    public ControladorEditarCliente(Ventana ventana, List cliente, List referencia){
        ac = new AltaCliente(ventana, true);  
        this.cliente=cliente;
        this.referencia=referencia;
        this.ac.aceptar.addActionListener(this);
        this.ac.cancelar.addActionListener(this);
        rellenarCampos();
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ac.aceptar){
            if(validarCampos()){ 
            new ModificarClienteSwing().execute();
            }
        }
          if(e.getSource() == ac.cancelar){
              ac.setVisible(false);
        }
    }
    
    public void rellenarCampos(){
            DateFormat dffrom = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            
            //------------datos cliente a editar--------//
            ac.apellidos.setText(cliente.get(0).toString());
            ac.nombres.setText(cliente.get(1).toString());
            ac.documento.setText(cliente.get(2).toString());
            ac.barrio.setText(cliente.get(5).toString());
            ac.calle.setText(cliente.get(6).toString());
            ac.numero.setText(cliente.get(7).toString());
            try {
                 Date today = dffrom.parse(cliente.get(8).toString());
                 ac.fecha_nac.setText(df.format(today));
            } catch (ParseException ex) {
                 Logger.getLogger(ControladorEditarCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            ac.telefono1.setText(cliente.get(3).toString());
            ac.telefono2.setText(cliente.get(4).toString());
            ac.trabajo.setText(cliente.get(9).toString());
            //---------------datos referencia a editar-----------------/
            ac.apellidosRef.setText(referencia.get(0).toString());
            ac.nombresRef.setText(referencia.get(1).toString());
            ac.telefonoRef.setText(referencia.get(2).toString());
            ac.parentescoRef.setText(referencia.get(3).toString());     
            //---------muestro ventana-------------//
            ac.setVisible(true);
        
    }
    
    public boolean validarCampos(){
        boolean bandera = true;
       
        if(ac.apellidos.getText().isEmpty()){
         ac.apellidos.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.apellidos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombres.getText().isEmpty()){
         ac.nombres.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.nombres.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.fecha_nac.getText() == null){
         ac.fecha_nac.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.fecha_nac.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.documento.getText().isEmpty()){
         ac.documento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.documento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.barrio.getText().isEmpty()){
         ac.barrio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.calle.getText().isEmpty()){
         ac.calle.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.calle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
      
        if(ac.telefono1.getText().isEmpty()){
         ac.telefono1.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.telefono1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
    }
    
    public class ModificarClienteSwing extends javax.swing.SwingWorker<Void, Void>{
         
         int id_control;
          DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        protected Void doInBackground() throws Exception {  
             Date fecha =df.parse(ac.fecha_nac.getText());
             String numero = "S/N";
             if(!ac.numero.getText().isEmpty()){
                 numero = ac.numero.getText();
             }
             cd.editarCliente(Integer.parseInt(ac.documento.getText()), ac.apellidos.getText(), ac.nombres.getText(),new java.sql.Date(fecha.getTime()), ac.barrio.getText(), ac.calle.getText(), Integer.parseInt(numero), ac.telefono1.getText(), ac.telefono2.getText(), ac.trabajo.getText(),Integer.parseInt(cliente.get(2).toString()));        
            return null;
        }

       @Override
       public void done() { 
           rd.editarReferencia(Integer.parseInt(ac.documento.getText()),ac.telefonoRef.getText(), ac.apellidosRef.getText(), ac.nombresRef.getText(), ac.parentescoRef.getText(), referencia.get(2).toString());
           ac.dispose();   
       }
    
}
}
