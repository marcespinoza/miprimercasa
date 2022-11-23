/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.DerechoDePosesion;
import conexion.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class DchoPosesionDAO {
    
    Conexion conexion;

    public DchoPosesionDAO() {
        conexion = new Conexion();
    }
    
    public List<DerechoDePosesion> listarCuenta(int id_control){
        Connection con = null;
        List<DerechoDePosesion> dp = new ArrayList<>();
        ResultSet rs = null;
     try {
          con = conexion.dataSource.getConnection();
          String listar = "SELECT fecha, monto, gastos, cemento_debe, cemento_haber, cemento_saldo, detalle, id_cta, nro_recibo from derecho_posesion where id_control='"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                DerechoDePosesion d = new DerechoDePosesion();
                d.setFecha(rs.getDate(1));
                d.setMonto(rs.getBigDecimal(2));
                d.setGastos(rs.getBigDecimal(3));
                d.setCemento_debe(rs.getBigDecimal(4));
                d.setCemento_haber(rs.getBigDecimal(5));
                d.setCemento_saldo(rs.getBigDecimal(6));
                d.setDetalle(rs.getString(7));
                d.setId_cta(rs.getInt(8));
                d.setNro_recibo(rs.getInt(9));
                dp.add(d);
          }
        } catch (SQLException e) {
            System.out.println(e.getMessage()+" listarCuenta de derecho de posesion");
        }finally{
         try {
                  con.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
     }
         return dp;
    }
    
    public void altaDchoPosesion(Date date, BigDecimal monto, BigDecimal gastos, BigDecimal cementoDebe, BigDecimal cementoHaber, BigDecimal cementoSaldo, String detalle, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = " insert into derecho_posesion (fecha, monto, gastos,cemento_debe, cemento_haber, cemento_saldo, detalle, id_control, timestamp)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDate(1, date);
            preparedStmt.setBigDecimal(2, monto);
            preparedStmt.setBigDecimal(3, gastos);
            preparedStmt.setBigDecimal(4, cementoDebe);
            preparedStmt.setBigDecimal(5, cementoHaber);
            preparedStmt.setBigDecimal(6, cementoSaldo);
            preparedStmt.setString(7, detalle);
            preparedStmt.setInt(8, id_control);
            preparedStmt.setTimestamp(9, new java.sql.Timestamp(new java.util.Date().getTime()));
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DchoPosesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
     public void actualizarNroRecibo(int nro_recibo, int id_recibo, BigDecimal saldo_cemento, int id_control){
        Connection con = null;
        PreparedStatement ps = null;
      try {
        con = conexion.dataSource.getConnection();
        ps = con.prepareStatement("UPDATE derecho_posesion SET nro_recibo = ?, id_recibo=? WHERE cemento_saldo = ? AND id_control = ?");
        ps.setInt(1, nro_recibo);
        ps.setInt(2, id_recibo);
        ps.setBigDecimal(3,saldo_cemento);
        ps.setInt(4,id_control);
        ps.executeUpdate();
      } catch (SQLException ex) {
        Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
            try {
                con.close();
            if(ps!=null){
               ps.close();
            }
            } catch (SQLException ex) {
                Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
  }
    
}
