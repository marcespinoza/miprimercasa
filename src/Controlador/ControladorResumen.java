/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MinutaDAO;
import Vista.Panels.Resumen;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


public class ControladorResumen implements ActionListener{
    
    Resumen vistaResumen;
    private Object [] resumen;
    private Object [] resumen2;
    MinutaDAO md = new MinutaDAO();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
    DefaultCategoryDataset dataset2 = new DefaultCategoryDataset(); 

    public ControladorResumen(Resumen vistaResumen) {
        this.vistaResumen=vistaResumen;
        this.vistaResumen.mostrar.addActionListener(this);
    }
   
    public void llenarTabla(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
        ResultSet rs = null;
        ResultSet rs2 = null;
        BigDecimal total1 = new BigDecimal(0);
        BigDecimal total2 = new BigDecimal(0);
        BigDecimal total3 = new BigDecimal(0);
        rs = md.obtenerMinutasXMes(añoDesde, añoHasta, mesDesde, mesHasta);
        rs2 = md.obtenerMinutasXCategoria(añoDesde, añoHasta, mesDesde, mesHasta);
        DefaultTableModel model = (DefaultTableModel) vistaResumen.tablaResumen.getModel();
        DefaultTableModel model2 = (DefaultTableModel) vistaResumen.tablaResumen2.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);
        try {
            //--------Minutas discriminados por monto de cuota---------//
            while(rs2.next()){
                String categoria = rs2.getString(1);
                String total = rs2.getString(2);
                String mes = rs2.getString(3);
                total1 = total1.add(new BigDecimal(total));
                resumen = new Object[] {categoria, total, mes};
                dataset.addValue(new BigDecimal(total), categoria, mes); 
                model.addRow(resumen);   
            } 
            vistaResumen.totalTabla1.setText(String.valueOf(total1));
            while(rs.next()){
                String mes = rs.getString(1);
                String recaudacion = rs.getString(2);
                String rendido = rs.getString(3);
                total2 = total2.add(new BigDecimal(recaudacion));
                total3 = total3.add(new BigDecimal(rendido));
                resumen2 = new Object[] {mes, recaudacion, rendido};
                dataset2.addValue(new BigDecimal(recaudacion),"", mes); 
                model2.addRow(resumen2);   
            } 
            vistaResumen.totalTabla2.setText(String.valueOf(total2));
            vistaResumen.totalTabla3.setText(String.valueOf(total3));
            graficoUno();
            graficoDos();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public void graficoUno(){ 
        JFreeChart chart = ChartFactory.createBarChart( 
        "", // El titulo de la gráfica 
        "Mes", // Etiqueta de categoria 
        "Valor", // Etiqueta de valores 
        dataset, // Datos 
        PlotOrientation.VERTICAL, // orientacion 
        true, // Incluye Leyenda 
        true, // Incluye tooltips 
        false // URLs? 
    ); 
        ChartPanel chartpanel = new ChartPanel(chart);
        //chartpanel.setDomainZoomable(true);  
        vistaResumen.freeChart.removeAll();
        vistaResumen.freeChart.setLayout(new java.awt.BorderLayout());
        vistaResumen.freeChart.setPreferredSize(new Dimension(200, 200));    
        vistaResumen.freeChart.add(chartpanel, BorderLayout.CENTER);
        //vistaResumen.freeChart.revalidate();
        vistaResumen.freeChart.repaint();
    }
     
      public void graficoDos(){ 
      JFreeChart chart = ChartFactory.createBarChart( 
    "", // El titulo de la gráfica 
    "Mes", // Etiqueta de categoria 
    "Valor", // Etiqueta de valores 
    dataset2, // Datos 
    PlotOrientation.VERTICAL, // orientacion 
    true, // Incluye Leyenda 
    true, // Incluye tooltips 
    false // URLs? 
    ); 
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);         
        vistaResumen.freeChart2.removeAll();
        vistaResumen.freeChart2.setLayout(new java.awt.BorderLayout());
        vistaResumen.freeChart2.setPreferredSize(new Dimension(200, 200)); 
        vistaResumen.freeChart2.add(chartpanel, BorderLayout.CENTER);
        vistaResumen.freeChart2.revalidate();
        vistaResumen.freeChart2.repaint();
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaResumen.mostrar){
            llenarTabla(vistaResumen.añoDesde.getYear(), vistaResumen.añoHasta.getYear(), vistaResumen.mesDesde.getMonth()+1,vistaResumen.mesHasta.getMonth()+1);
        }
    }
    
}
