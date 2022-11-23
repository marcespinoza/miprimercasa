/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Frame;
import Controlador.ControladorBaseDeDatos;
import Controlador.ControladorCliente;
import Controlador.ControladorBotones;
import Controlador.ControladorConfiguracion;
import Controlador.ControladorMinuta;
import Controlador.ControladorRegistro;
import Controlador.ControladorResumen;
import Vista.Panels.MinutaVista;
import Vista.Panels.Resumen;
import com.itextpdf.text.Rectangle;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo
 */
public class Ventana extends javax.swing.JFrame implements ActionListener{
    
    MinutaVista vistaMinuta = MinutaVista.getInstance();
    Resumen vistaResumen = Resumen.getInstance();
    public static ControladorMinuta cm;
    ControladorResumen cr;
    File configFile = new File("config.properties");
    String pathRespaldoBD = null;
    String pathMysqldump = null;
    DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    java.util.Date date = new java.util.Date();

    public Ventana() {
        initComponents();      
//        this.setSize(1366, 768);
        ImageIcon icon = new ImageIcon("src/Imagenes/logo.png");
        this.setIconImage(icon.getImage());         
        cm = new ControladorMinuta(minuta);
        cr = new ControladorResumen(resumen);       
        configuracion.addActionListener(this);
        registroEventos.addActionListener(this);
        about.addActionListener(this);
        baseDeDatos.addActionListener(this);
//        scheduleBackupBD();
        Calendar cal = Calendar.getInstance();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
               inicializarPaneles();
            }            
            });
        pack();
        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = 768 - winSize.height;
        setSize(1024,taskBarHeight);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
         
      }
    
      public void inicializarPaneles(){
          inicializarBotones();       
        
        //-------Controlador para manejar botones superiores - Clientes,Minutas---------//
        new ControladorBotones(this);   
        new ControladorCliente(clientes, this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        panelPrincipal = new javax.swing.JPanel();
        clientes = new Vista.Panels.Clientes();
        detallePago = new Vista.Panels.DetalleCuota();
        resumen = new Vista.Panels.Resumen();
        minuta = new Vista.Panels.MinutaVista();
        panelBotones1 = new Vista.Panels.PanelBotones();
        btnClientes = new javax.swing.JButton();
        btnResumen = new javax.swing.JButton();
        btnMinuta = new javax.swing.JButton();
        btnCumpleaños = new javax.swing.JButton();
        calculadora = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        labelTipoUsuario = new javax.swing.JLabel();
        apellidoUsuario = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        ayuda = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuInicio = new javax.swing.JMenu();
        cerrarSesion = new javax.swing.JMenuItem();
        info = new javax.swing.JMenu();
        configuracion = new javax.swing.JMenuItem();
        registroEventos = new javax.swing.JMenuItem();
        baseDeDatos = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("File");
        jMenuBar3.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi Primer Casa");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPrincipal.setLayout(new java.awt.CardLayout());
        panelPrincipal.add(clientes, "Clientes");
        panelPrincipal.add(detallePago, "DetallePago");
        panelPrincipal.add(resumen, "Resumen");
        panelPrincipal.add(minuta, "Minuta");

        panelBotones1.setBackground(new java.awt.Color(36, 47, 65));

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/lotes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnResumen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/resumen.png"))); // NOI18N
        btnResumen.setText("Resumen");
        btnResumen.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenActionPerformed(evt);
            }
        });

        btnMinuta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/minuta.png"))); // NOI18N
        btnMinuta.setText("Minuta");
        btnMinuta.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMinuta.setMaximumSize(new java.awt.Dimension(92, 34));
        btnMinuta.setMinimumSize(new java.awt.Dimension(92, 34));
        btnMinuta.setName(""); // NOI18N
        btnMinuta.setPreferredSize(new java.awt.Dimension(92, 34));
        btnMinuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinutaActionPerformed(evt);
            }
        });

        btnCumpleaños.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/cake.png"))); // NOI18N
        btnCumpleaños.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnCumpleaños.setMaximumSize(new java.awt.Dimension(92, 34));
        btnCumpleaños.setMinimumSize(new java.awt.Dimension(92, 34));
        btnCumpleaños.setName(""); // NOI18N
        btnCumpleaños.setPreferredSize(new java.awt.Dimension(92, 34));
        btnCumpleaños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCumpleañosActionPerformed(evt);
            }
        });

        calculadora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/calculadora.png"))); // NOI18N
        calculadora.setText("Calculadora");
        calculadora.setMargin(new java.awt.Insets(2, 2, 2, 2));
        calculadora.setMaximumSize(new java.awt.Dimension(92, 34));
        calculadora.setMinimumSize(new java.awt.Dimension(92, 34));
        calculadora.setName(""); // NOI18N
        calculadora.setPreferredSize(new java.awt.Dimension(92, 34));
        calculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculadoraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 153));
        jLabel1.setText("Usuario:");

        labelUsuario.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));

        labelTipoUsuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        labelTipoUsuario.setForeground(new java.awt.Color(255, 255, 255));

        apellidoUsuario.setForeground(new java.awt.Color(36, 47, 65));
        apellidoUsuario.setText("jLabel3");

        nombreUsuario.setForeground(new java.awt.Color(36, 47, 65));
        nombreUsuario.setText("jLabel2");

        ayuda.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ayuda.setForeground(new java.awt.Color(51, 255, 255));
        ayuda.setText("Ayuda");

        javax.swing.GroupLayout panelBotones1Layout = new javax.swing.GroupLayout(panelBotones1);
        panelBotones1.setLayout(panelBotones1Layout);
        panelBotones1Layout.setHorizontalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMinuta, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculadora, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCumpleaños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ayuda, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
                .addComponent(nombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apellidoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelBotones1Layout.setVerticalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotones1Layout.createSequentialGroup()
                        .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(apellidoUsuario)
                                .addComponent(nombreUsuario)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBotones1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBotones1Layout.createSequentialGroup()
                                .addComponent(ayuda, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelBotones1Layout.createSequentialGroup()
                                .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMinuta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(calculadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20))
                            .addGroup(panelBotones1Layout.createSequentialGroup()
                                .addComponent(btnCumpleaños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        MenuInicio.setText("Inicio");

        cerrarSesion.setText("Cerrar sesión");
        cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionActionPerformed(evt);
            }
        });
        MenuInicio.add(cerrarSesion);

        jMenuBar1.add(MenuInicio);

        info.setText("Opciones");

        configuracion.setText("Configuración");
        info.add(configuracion);

        registroEventos.setText("Registro de eventos");
        registroEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroEventosActionPerformed(evt);
            }
        });
        info.add(registroEventos);

        baseDeDatos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        baseDeDatos.setText("Base de datos");
        info.add(baseDeDatos);

        about.setText("Acerca de ..");
        info.add(about);

        jMenuBar1.add(info);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBotones1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBotones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResumenActionPerformed

    private void btnMinutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinutaActionPerformed

    private void cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarSesionActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

    private void registroEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroEventosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registroEventosActionPerformed

    private void btnCumpleañosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCumpleañosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCumpleañosActionPerformed

    private void calculadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculadoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calculadoraActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenu MenuInicio;
    public javax.swing.JMenuItem about;
    public static javax.swing.JLabel apellidoUsuario;
    public javax.swing.JLabel ayuda;
    public javax.swing.JMenuItem baseDeDatos;
    public static javax.swing.JButton btnClientes;
    public static javax.swing.JButton btnCumpleaños;
    public static javax.swing.JButton btnMinuta;
    public static javax.swing.JButton btnResumen;
    public static javax.swing.JButton calculadora;
    public static javax.swing.JMenuItem cerrarSesion;
    public Vista.Panels.Clientes clientes;
    public javax.swing.JMenuItem configuracion;
    public Vista.Panels.DetalleCuota detallePago;
    public javax.swing.JMenu info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    public static javax.swing.JLabel labelTipoUsuario;
    public static javax.swing.JLabel labelUsuario;
    public Vista.Panels.MinutaVista minuta;
    public static javax.swing.JLabel nombreUsuario;
    private Vista.Panels.PanelBotones panelBotones1;
    public static javax.swing.JPanel panelPrincipal;
    public javax.swing.JMenuItem registroEventos;
    public Vista.Panels.Resumen resumen;
    // End of variables declaration//GEN-END:variables

    public void inicializarBotones(){
    if(Ventana.labelTipoUsuario.getText().equals("operador"))
    registroEventos.setEnabled(false);
}

    private void scheduleBackupBD(){       
     try {
      FileReader reader = new FileReader(configFile);
      Properties props = new Properties();
      props.load(reader); 
      pathMysqldump = props.getProperty("pathMysqldump");
      pathRespaldoBD = props.getProperty("pathRespaldoBD");
      reader.close();
     } catch (FileNotFoundException ex) {
     // file does not exist
     } catch (IOException ex) {
     // I/O error
     }  
      long delay = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.of(12, 00, 00));
      //------Si delay es negativo significa que ya paso la hora de ejecucio entonces no lo programo----// Ejecuta a las 12
      if(delay>0){
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.schedule(new MyRunnable("_12Hs"), delay, TimeUnit.MILLISECONDS);}
      long delay2 = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.of(20, 00, 00));
      //------Si delay es negativo significa que ya paso la hora de ejecucio entonces no lo programo----// Ejectua a las 20
      if(delay2>0){
      ScheduledExecutorService schedulerTarde = Executors.newScheduledThreadPool(1);
      schedulerTarde.schedule(new MyRunnable("_20Hs"), delay2, TimeUnit.MILLISECONDS);}
    }

    
   public class MyRunnable implements Runnable {
       
       String parameter;

       public MyRunnable(String parameter) {
           this.parameter=parameter;
       }

       public void run() {
       // task to run goes here
         try {
             Runtime.getRuntime().exec(pathMysqldump+"/mysqldump -u root -pMiPrimerCasa --add-drop-database -B miprimercasa -r "+"\""+pathRespaldoBD+"/"+fecha.format(cal.getTime())+parameter+".sql\"");
         } catch (IOException ex) {
             System.out.println(ex.getMessage());
             Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
         }
       }
    }

    public void desactivarBotones(){  
        if(Ventana.labelTipoUsuario.getText().equals("operador")){
            clientes.eliminarBtn.setEnabled(false);
            registroEventos.setEnabled(false);
            btnResumen.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(configuracion)){
             new ControladorConfiguracion(this);
        }
        if(e.getSource().equals(baseDeDatos)){
            new ControladorBaseDeDatos(this);
        }
        if(e.getSource().equals(about)){
          ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/about.png");   
          JOptionPane.showMessageDialog(null, "Desarrollado por Marcelo Espinoza \n marceloespinoza00@gmail.com","Acerca de ..", HEIGHT, icon);
        }
        if(e.getSource().equals(registroEventos)){
           new ControladorRegistro(this);
        }
    }
     
}

