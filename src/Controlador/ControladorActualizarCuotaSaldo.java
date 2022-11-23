/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Cuota;
import Clases.FichaDeControl;
import static Controlador.ControladorAltaCuota.log;
import Modelo.ActualizacionCementoDAO;
import Modelo.ActualizacionEmpleadoDAO;
import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Utils.DoubleJTextField;
import Vista.Dialogs.ActualizarCuota;
import Vista.Dialogs.ActualizarCuotaSaldo;
import Vista.Dialogs.Progress;
import Vista.Frame.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuotaSaldo implements ActionListener{
    
    ActualizarCuotaSaldo acs;
    FichaControlDAO fc = new FichaControlDAO();
    ActualizacionEmpleadoDAO ad =new ActualizacionEmpleadoDAO();
    Ventana ventana;
    int id_control, cuota;
    FichaControlDAO fcd = new FichaControlDAO();
    ActualizacionCementoDAO acd = new ActualizacionCementoDAO();
    CuotaDAO cd = new CuotaDAO();
    BigDecimal cdc, precio_bc, nueva_cuota, gastos, cuota_pura, nuevo_saldo, cemento_saldo, cantidad_bc, cuota_anterior;
    int cant_cuotas;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorDetalleCuota.class.getName());

    public ControladorActualizarCuotaSaldo(Ventana ventana, int id_control) {
        this.ventana=ventana;
        this.id_control=id_control;
        acs = new ActualizarCuotaSaldo(ventana, true);
        acs.actualizar.addActionListener(this);
        acs.cancelar.addActionListener(this);
        acs.setLocationRelativeTo(null);
        //---Listener para cuando actualizo manualmente el saldo---//
        acs.cuotaActualizada.getDocument().addDocumentListener(new DocumentListener() {
          public void changedUpdate(DocumentEvent e) {
           nuevoSaldo();
         }
        public void removeUpdate(DocumentEvent e) {
          nuevoSaldo();
         }
        public void insertUpdate(DocumentEvent e) {
         nuevoSaldo();
         }
        });
        //-------Controlo que solo ingrese numeros y punto------//
       acs.cuotaActualizada.addKeyListener(new DoubleJTextField(acs.cuotaActualizada));
       if(Ventana.labelTipoUsuario.getText().equals("operador")){
         acs.cuotaActualizada.setEnabled(false);
       }
       calcularValores();   
       
       }
    
    private boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    private boolean isValidSignal(char ch){
        if( (acs.cuotaActualizada.getText() == null || "".equals(acs.cuotaActualizada.getText().trim()) ) && ch == '-'){
            return true;
        }

        return false;
    }

    private boolean validatePoint(char ch){
        if(ch != '.'){
            return false;
        }

        if(acs.cuotaActualizada.getText() == null || "".equals(acs.cuotaActualizada.getText().trim())){
            acs.cuotaActualizada.setText("0.");
            return false;
        }

        return true;
    }
    
    public void calcularValores(){
        List<Cuota> lcuotas = new ArrayList<>();
        List<Cuota> listaC;
        List<FichaDeControl> listaFichaControl = fcd.obtenerFichaControl(id_control);
        lcuotas = cd.listaDetalleCuotaXsaldo(id_control); 
        precio_bc = listaFichaControl.get(0).getBolsaCemento();
        cemento_saldo =  lcuotas.get(lcuotas.size()-1).getCemento_saldo(); 
        cuota_anterior = listaFichaControl.get(0).getCuotaPura().add(listaFichaControl.get(0).getGastos());
         //------Calculo el nro de cuota--------//
        cuota = 0;
        int indice = 0;
        listaC = cd.getNrosCuotas(id_control);
        if(!listaC.isEmpty()){
          cuota = listaC.get(indice).getNro_cuota();
          indice = indice + 1;
          while(indice < listaC.size()&& (listaC.get(indice).getNro_cuota()-1==cuota || listaC.get(indice).getNro_cuota()-1 < cuota)){
           cuota=listaC.get(indice).getNro_cuota();
           indice = indice + 1;
          }
        }
//        int cant_cuotas = 180-(listaC.size()-1);
        cant_cuotas = listaFichaControl.get(0).getCantidadCuotas()-(listaC.size()-1);
        cantidad_bc = cemento_saldo.divide(new BigDecimal(cant_cuotas),2, RoundingMode.DOWN);
        nueva_cuota = cantidad_bc.multiply(precio_bc);
        gastos = nueva_cuota.subtract((nueva_cuota).divide(new BigDecimal(1.12),2, BigDecimal.ROUND_HALF_UP));
        cuota_pura = nueva_cuota.subtract(gastos);
        nuevo_saldo = nueva_cuota.multiply(new BigDecimal(cant_cuotas));
        acs.cuotaActualizada.setText(String.valueOf(nueva_cuota.setScale(2, RoundingMode.HALF_UP)));
        acs.saldoActualizado.setText(String.valueOf(nuevo_saldo.setScale(2, RoundingMode.HALF_UP)));
        acs.setVisible(true);
    }
    
     public void nuevoSaldo() {
       if(!acs.cuotaActualizada.getText().equals("")){
        acs.actualizar.setEnabled(true);
        nueva_cuota = new BigDecimal(acs.cuotaActualizada.getText().toString());
        gastos = nueva_cuota.subtract((nueva_cuota).divide(new BigDecimal(1.12),2, BigDecimal.ROUND_HALF_UP));
        cuota_pura = nueva_cuota.subtract(gastos);
        nuevo_saldo = nueva_cuota.multiply(new BigDecimal(cant_cuotas));
        acs.saldoActualizado.setText(String.valueOf(nuevo_saldo.setScale(2, RoundingMode.HALF_UP)));
      }else{
           acs.saldoActualizado.setText("");
           acs.actualizar.setEnabled(false);
       }
     }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== acs.actualizar){
           actualizarSaldo();
        }
        if(e.getSource() == acs.cancelar){
            acs.dispose();
        }
    }

    public void actualizarSaldo(){
        long fechaActual = Calendar.getInstance().getTimeInMillis();
        Date date = new Date();
        int filas_insertadas = cd.altaCuotaLote(new java.sql.Timestamp(fechaActual),cuota, "", BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(0), nueva_cuota, nuevo_saldo, new BigDecimal(0), cantidad_bc, cemento_saldo, "ACTUALIZACION", "", id_control, 1, BigDecimal.ZERO);  
        fc.actualizarValorCuota(gastos, cuota_pura, new java.sql.Timestamp(fechaActual), id_control);         
        acd.actualizarCemento(String.valueOf(id_control), new java.sql.Date(date.getTime()), cuota_anterior , nueva_cuota);
        
        if (filas_insertadas==1) {
           log.info(Ventana.nombreUsuario.getText() + " - Actualiza cuota");
           acs.dispose();
           filas_insertadas=0;
         } 
    }
    
    
}
