/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.FichaDeControl;
import Modelo.ActualizacionEmpleadoDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.ActualizarCuota;
import Vista.Dialogs.Progress;
import Vista.Frame.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuota implements ActionListener{
    
    ActualizarCuota ac;
    FichaControlDAO fc = new FichaControlDAO();
    ActualizacionEmpleadoDAO ad =new ActualizacionEmpleadoDAO();
    Ventana ventana;
    int id_control;

    public ControladorActualizarCuota(Ventana ventana, int id_control) {
        this.ventana=ventana;
        this.id_control=id_control;
        ac = new ActualizarCuota(ventana, true);
        ac.setLocationRelativeTo(null);
        ac.cancelar.addActionListener(this);
        ac.actualizar.addActionListener(this);
        ac.porcentaje.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {   
             nuevoGasto();}
            @Override
            public void removeUpdate(DocumentEvent e) {
             nuevoGasto();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
         });
        rellenarCampos();
    }

    private void rellenarCampos() {
        List<FichaDeControl> listaFichaControl;
        listaFichaControl = fc.obtenerFichaControl(id_control);
        BigDecimal cuota = (listaFichaControl.get(0).getCuotaPura()).add(listaFichaControl.get(0).getGastos());
        ac.valor_actual.setText(cuota.toString());
        ac.setVisible(true);
    }   
    
    public void nuevoGasto(){
        if(!ac.valor_actual.getText().equals("")){
            if(!ac.porcentaje.getText().equals("")){
                BigDecimal aumento;
                BigDecimal cuota_total = new BigDecimal(ac.valor_actual.getText());   
                aumento = ((cuota_total.multiply(new BigDecimal(ac.porcentaje.getText()))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP)).add(cuota_total);
                ac.valor_actualizado.setText(aumento.toString());
              
            }else{
                ac.valor_actualizado.setText("");
            }
            }else{
                ac.valor_actualizado.setText("");
             }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if(e.getSource() == ac.actualizar){
              if(!ac.valor_actual.getText().equals("")){
                  new Actualizar_cuota().execute();
              }else{
              }
           }
            if(e.getSource() == ac.cancelar){
               ac.dispose();
           }
    }
    
   //----Hilo para generar recibo y minutas-----//      
       public class Actualizar_cuota extends javax.swing.SwingWorker<Void, Void>{
         
       final Progress progress = new Progress(ac, false);

        @Override
        protected Void doInBackground() throws Exception {     
            progress.setVisible(true);  
            long fechaActual = Calendar.getInstance().getTimeInMillis();
            Date date = new Date();
            BigDecimal valorActualizado = new BigDecimal(ac.valor_actualizado.getText());
            BigDecimal gastos =valorActualizado.subtract((valorActualizado).divide(new BigDecimal(1.1),2, BigDecimal.ROUND_HALF_UP));
            BigDecimal cuotaPura = valorActualizado.subtract(gastos);   
            System.out.println(ac.porcentaje.getText());
            fc.actualizarValorCuota(gastos, cuotaPura, new java.sql.Timestamp(fechaActual), id_control);
            ad.altaActualizacion(id_control, 
                    new java.sql.Date(date.getTime()), 
                    new BigDecimal(ac.porcentaje.getText()), 
                    new BigDecimal(ac.valor_actual.getText()), valorActualizado);
           return null;
           
       }

       @Override
       public void done() {             
            progress.setVisible(false);
            ac.dispose();
         }    
        }
    
}
