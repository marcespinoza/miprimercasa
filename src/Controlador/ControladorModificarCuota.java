
package Controlador;

import Clases.Cuota;
import Clases.FichaDeControl;
import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.ModificarCuota;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marceloi7
 */
public class ControladorModificarCuota implements ActionListener{
    
    private int id_control, nro_cuota;
    Ventana ventana;
    ModificarCuota ac;
    FichaControlDAO fc = new FichaControlDAO();
    CuotaDAO cd = new CuotaDAO();
    BigDecimal cuotapuraActual, gastosActual;
    private int filas_insertadas=0;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorCliente.class.getName());

    public ControladorModificarCuota(Ventana ventana, int id_control, int nro_cuota, BigDecimal cuotapuraActual, BigDecimal gastosActual) {
        this.ventana=ventana;
        this.id_control=id_control;
        ac = new ModificarCuota(ventana, true);
        ac.aceptarBtn.addActionListener(this);
        ac.cancelarBtn.addActionListener(this);
        ac.aviso.setVisible(false);
        this.id_control=id_control;
        this.cuotapuraActual=cuotapuraActual;
        this.gastosActual=gastosActual;      
        ac.porcentaje_gastos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); 
            }            
         });
        //-------Evito que ingresen letras------------//
        ac.cuota_total.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); //To change body of generated methods, choose Tools | Templates.
            }               
        });
        ac.gastos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); 
            }   
        });
        //-------------------------//
        asignarValores();
        ac.setLocationRelativeTo(null);
        ac.setVisible(true);
    }
    
    private void asignarValores() {
       ac.cuota_total.setText(String.valueOf(cuotapuraActual.add(gastosActual)));
       ac.gastos.setText(String.valueOf(gastosActual));
    }
        
    private void calcularGastos(){
      if(!ac.cuota_total.getText().equals("")){
       if(!ac.porcentaje_gastos.getText().equals("")){   
         BigDecimal cuota_total = new BigDecimal(ac.cuota_total.getText());
         BigDecimal gasto = (cuota_total.multiply(new BigDecimal(ac.porcentaje_gastos.getText()))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
         ac.gastos.setText(String.valueOf(gasto));
      }else{
         ac.gastos.setText("");
       }
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ac.aceptarBtn){
            new Actualizar().execute();
        }
        if(e.getSource()==ac.cancelarBtn){
            ac.dispose();
        }
    }
    
    public void actualizarPago(){
        List<FichaDeControl> listaFichaControl;
        List<Cuota> cuotas;
        if(validarCampos()){           
               listaFichaControl = fc.obtenerFichaControl(id_control);
               cuotas = cd.listaDetalleCuotaXsaldo(id_control);                                  
               BigDecimal cuota_pura = new BigDecimal(ac.cuota_total.getText()).subtract(new BigDecimal(ac.gastos.getText()));
               BigDecimal gastos = new BigDecimal(ac.gastos.getText());
               BigDecimal haber = cuota_pura.add(gastos);
               BigDecimal bolsa_cemento = listaFichaControl.get(0).getBolsaCemento();
               BigDecimal ultimo_saldo =cuotas.get(cuotas.size()-1).getSaldo();                 
               BigDecimal ultimo_saldo_bolsa_cemento = cuotas.get(cuotas.size()-1).getCemento_saldo();      
               
               BigDecimal diferenciaCuotaPura = cuota_pura.subtract(cuotapuraActual);
               BigDecimal diferenciaGastos = gastos.subtract(gastosActual);           
               BigDecimal diferenciaHaber = diferenciaCuotaPura.add(diferenciaGastos);
               BigDecimal diferenciaHaberCemento = (diferenciaHaber).divide(bolsa_cemento,  2, RoundingMode.DOWN);
               
               BigDecimal saldo_actual = ultimo_saldo.subtract(diferenciaCuotaPura.add(diferenciaGastos));
               BigDecimal cemento_haber = haber.divide(bolsa_cemento, 2, RoundingMode.DOWN);
               BigDecimal cemento_saldo = ultimo_saldo_bolsa_cemento.subtract(diferenciaHaberCemento);
               filas_insertadas = cd.actualizarMontoCuota(cuota_pura, gastos, haber, saldo_actual, cemento_haber, cemento_saldo, nro_cuota, id_control);
        }
    }
    
    public boolean validarCampos(){
        boolean bandera = true;       
        if(ac.cuota_total.getText().isEmpty()){
         ac.cuota_total.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.cuota_total.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.gastos.getText().isEmpty()){
         ac.gastos.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.gastos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    public class Actualizar extends javax.swing.SwingWorker<Void, Void>{

        @Override
        protected Void doInBackground() throws Exception {
            actualizarPago();
            return null;
        }

        @Override
        protected void done() {
            if(filas_insertadas == 1){
                ac.dispose();
               log.info(Ventana.nombreUsuario.getText() + " - Modifica pago");}
            else{
               ac.setVisible(true);
            }
        }        
        
    }
    
}
