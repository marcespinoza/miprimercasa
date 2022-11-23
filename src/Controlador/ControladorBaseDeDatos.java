/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.BaseDeDatos;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorBaseDeDatos implements ActionListener{
    
    BaseDeDatos bd;
    String pathRespaldo, pathMysql;
    File f;
    JFileChooser chooser;
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    File configFile = new File("config.properties");

    public ControladorBaseDeDatos(Ventana ventana) {
        bd = new BaseDeDatos(ventana, true);
        bd.setLocationRelativeTo(null);
        bd.crearRespaldo.addActionListener(this);
        bd.guardarEn.addActionListener(this);
        bd.buscar.addActionListener(this);
        bd.progressBar.setVisible(false);
        cargarDirectorios();
        bd.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==bd.buscar){
          chooser = new JFileChooser();
          chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int returnVal = chooser.showOpenDialog(bd);
          //------Verifico que aprete boton Aceptar------//
          if(returnVal==JFileChooser.APPROVE_OPTION){
          f = chooser.getSelectedFile();
          pathMysql = f.getAbsolutePath().replace("\\", "/");
          bd.pathMysqlTxf.setText(pathMysql);
          try {
          Properties props = new Properties();                
          fileIn = new FileInputStream(configFile);
          props.load(fileIn);
          props.setProperty("pathMysqldump", pathMysql);
          fileOut = new FileOutputStream(configFile);
          props.store(fileOut, "config");
          } catch (FileNotFoundException ex) {
          // file does not exist
          } catch (IOException ex) {
           // I/O error
          }
        }
        }
        if(e.getSource()==bd.guardarEn){
          chooser = new JFileChooser();
          chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int returnVal = chooser.showOpenDialog(bd);
          //------Verifico que aprete boton Aceptar------//
          if(returnVal==JFileChooser.APPROVE_OPTION){
          f = chooser.getSelectedFile();
          pathRespaldo = f.getAbsolutePath().replace("\\", "/");;
          bd.pathGuardarTxf.setText(pathRespaldo);
          try {
          Properties props = new Properties();                
          fileIn = new FileInputStream(configFile);
          props.load(fileIn);
          props.setProperty("pathRespaldoBD", pathRespaldo);
          fileOut = new FileOutputStream(configFile);
          props.store(fileOut, "config");
          } catch (FileNotFoundException ex) {
          // file does not exist
          } catch (IOException ex) {
           // I/O error
          }
        }
        }
        if(e.getSource()==bd.crearRespaldo){
            bd.respaldoOk.setText("");
            if(!bd.pathMysqlTxf.getText().equals("") && !bd.pathGuardarTxf.getText().equals("")){
                new RespaldoBD().execute();
           }else{
              bd.respaldoOk.setText("Rellene todos los campos");
            }
        }
    }
    
    //-----Carga los paths de Mysqldump y donde se va a guardar la copia------//
    private void cargarDirectorios(){
          try {
          FileReader reader = new FileReader(configFile);
          Properties props = new Properties();
          props.load(reader); 
          String pathMysqldump = props.getProperty("pathMysqldump");
          String pathRespaldoBD = props.getProperty("pathRespaldoBD");
          bd.pathMysqlTxf.setText(pathMysqldump);
          bd.pathGuardarTxf.setText(pathRespaldoBD);
          reader.close();
         } catch (FileNotFoundException ex) {
        // file does not exist
        } catch (IOException ex) {
        // I/O error
         }
    }
    
    private class RespaldoBD extends SwingWorker<Object, Object>{
        
         int completo=-1;

        @Override
        protected Object doInBackground() throws Exception {
            bd.progressBar.setVisible(true);
            bd.progressBar.setIndeterminate(true);
            try {
                DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                java.util.Date date = new java.util.Date();
                Process p;
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec(bd.pathMysqlTxf.getText()+"/mysqldump -u root -pMiPrimerCasa --add-drop-database -B miprimercasa -r "+"\""+bd.pathGuardarTxf.getText()+"/miprimercasa_"+fecha.format(date)+".sql\"");
                completo = p.waitFor();     
            } catch (IOException | InterruptedException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ControladorBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
        @Override
        public void done(){
          bd.progressBar.setVisible(false);
             switch (completo) {
                 case 0:
                     bd.respaldoOk.setForeground(new Color(0, 102, 0));
                     bd.respaldoOk.setText("Respaldo creado correctamente");
                     break;
                 case 1:
                     bd.respaldoOk.setForeground(Color.RED);
                     bd.respaldoOk.setText("No se pudo crear el respaldo");
                      System.out.println(completo);
                     break;
                 default:       
                     bd.respaldoOk.setForeground(Color.RED);
                     bd.respaldoOk.setText("No se pudo crear el respaldo");
                      System.out.println(completo);
                     break;
             }
        }
        
        
    }
    
}
