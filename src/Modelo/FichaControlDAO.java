/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.FichaDeControl;
import conexion.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class FichaControlDAO {
    
     Conexion conexion;

    public FichaControlDAO() {
        conexion = new Conexion();
    }
    
    public int altaFichaControl(String tipo_compra,
            String dimension, 
            int cantidad_cuotas, 
            BigDecimal cuota_pura, 
            BigDecimal gastos, 
            byte bandera_cemento, 
            BigDecimal bolsa_cemento,
            Date fch_actualizacion, 
            String barrio, 
            String manzana, 
            String parcela, 
            Date fch_suscripcion,
            byte bandera_indice,
            int vendedor, 
            String canal_venta,
            BigDecimal suscripcion){
         int id_control = 0;
         int rowAffected = 0;
         Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
     try {
          Connection con = conexion.dataSource.getConnection();
          String insertar = "Insert into ficha_control_lote(tipo_compra, dimension, cantidad_cuotas, cuota_pura, gastos, bandera_cemento, bolsa_cemento, fecha_actualizacion, lote_barrio, lote_manzana, lote_parcela, bandera, indice_corrector, timestamp, vendedor, canal_venta, suscripcion)"
                  + " values "
                  + "('"+tipo_compra+"',"
                  + "'"+dimension+"',"
                  + "'"+cantidad_cuotas+"',"
                  + "'"+cuota_pura+"',"
                  + "'"+gastos+"',"
                  + "'"+bandera_cemento+"',"
                  + "'"+bolsa_cemento+"', "
                  + "'"+fch_actualizacion+"',"
                  + "'"+barrio+"',"
                  + "'"+manzana+"',"
                  + "'"+parcela+"',"
                  + "'"+fch_suscripcion+"',"
                  + "'"+bandera_indice+"', "
                  + "'"+timestamp+"',"
                  + "'"+vendedor+"',"
                  + "'"+canal_venta+"',"
                  + "'"+suscripcion+"')";
          PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          rowAffected = ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString()+" Alta ficha de control");
        }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
    public List<FichaDeControl> obtenerFichaControl(){
         ResultSet rs = null;
         Connection connection = null;
         List<FichaDeControl> listaFichaControl = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT precio, gastos, bolsa_cemento FROM cliente c LEFT JOIN ficha_control_lote f ON c.Dni = f.Dni"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              FichaDeControl fc = new FichaDeControl();
              fc.setPrecio(rs.getBigDecimal(1));
              fc.setGastos(rs.getBigDecimal(2));
              fc.setBolsaCemento(rs.getBigDecimal(3));
              listaFichaControl.add(fc);
          }
        } catch (Exception e) {
            Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally{
          try {
              connection.close();
          } catch (SQLException ex) {
              Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
     return listaFichaControl;
 }
    
     public ResultSet obtenerMontoCuotas(){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT cuota_pura FROM ficha_control_lote"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
 }
    
    public List<FichaDeControl> obtenerFichaControl(int id_control){
         ResultSet rs = null;
         Connection connection = null;
         List<FichaDeControl> listaFichaControl = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT fc.dimension, fc.cantidad_cuotas, fc.cuota_pura, fc.gastos, fc.bolsa_cemento, fc.lote_barrio, fc.lote_manzana, fc.lote_parcela,  fc.bandera, c.apellidos, c.nombres, fc.bandera_cemento FROM ficha_control_lote fc inner join cliente_tiene_lote cl on fc.id_control=cl.id_control inner join cliente c on c.dni=cl.cliente_dni where fc.id_control = '"+id_control+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              FichaDeControl fc = new FichaDeControl();
              fc.setDimension(rs.getString(1));
              fc.setCantidadCuotas(rs.getInt(2));
              fc.setCuotaPura(rs.getBigDecimal(3));
              fc.setGastos(rs.getBigDecimal(4));
              fc.setBolsaCemento(rs.getBigDecimal(5));
              fc.setBarrio(rs.getString(6));
              fc.setManzana(rs.getString(7));
              fc.setParcela(rs.getString(8));
              fc.setBandera(rs.getTimestamp(9));
              fc.setApellido(rs.getString(10));
              fc.setNombre(rs.getString(11));
              fc.setBandera_cemento(rs.getInt(12));
              listaFichaControl.add(fc);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
         try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
     }
     return listaFichaControl;
 }
    
    public int obtenerIdControl(){
     ResultSet rs = null;
     int id_control = 0;
     try {
          //numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
        }
     return id_control;
 }
    
  public void cambiarPropietario(int nuevo_dni, int viejo_dni, int id_ficha_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cliente_tiene_lote SET cliente_dni = ? WHERE cliente_dni = ? AND id_control = ?");
            ps.setInt(1,nuevo_dni);
            ps.setInt(2,viejo_dni);
            ps.setInt(3,id_ficha_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
  
  //---------Baja logica de un cliente----------//
  public void bajaPropietario(int dni, int id_ficha_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cliente_tiene_lote SET baja = 1 WHERE cliente_dni = ? AND id_control = ?");
            ps.setInt(1,dni);
            ps.setInt(2,id_ficha_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
  
   public void actualizarBolsaCemento( BigDecimal precio_cemento, Date fecha_actualizacion, String id_control){
             Connection con = null;
             PreparedStatement ps = null;
        try {
            con = conexion.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE ficha_control_lote SET bolsa_cemento = ?, fecha_actualizacion = ? WHERE id_control = ?");
            ps.setBigDecimal(1, precio_cemento);
            ps.setDate(2, fecha_actualizacion);
            ps.setString(3, id_control);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
                 try {
                     ps.close();
                     con.close();
                 } catch (SQLException ex) {
                     System.out.println(ex.getMessage());
                     Logger.getLogger(FichaControlDAO.class.getName()).log(Level.SEVERE, null, ex);
                 }            
        }
  }
   
   public void actualizarValorCuota( BigDecimal gastos, BigDecimal cuota_pura, Timestamp bandera,int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ficha_control_lote SET gastos = ?, cuota_pura = ?, bandera = ? WHERE id_control = ?");
            ps.setBigDecimal(1, gastos);
            ps.setBigDecimal(2, cuota_pura);
            ps.setTimestamp(3, bandera);
            ps.setInt(4, id_control);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
   
    public void actualizarObservacion(String observacion, int id_control){
          Connection con = null;
          PreparedStatement ps = null;
        try {
            con = conexion.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE ficha_control_lote SET observacion = ? WHERE id_control = ?");
            ps.setString(1,observacion);
            ps.setInt(2,id_control);
            ps.executeUpdate();          
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              try {
                  ps.close();
                  con.close();
              } catch (SQLException ex) {
                  Logger.getLogger(FichaControlDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
  }
    
   // ------Actualiza valor cuota y nuevo saldo------//
  public void actualizarGasto(BigDecimal cuota_pura, BigDecimal gastos, int id_control){
      Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE ficha_control_lote SET cuota_pura = ?, gastos = ? WHERE id_control = ?");
            ps.setBigDecimal(1, cuota_pura);
            ps.setBigDecimal(2,gastos);
            ps.setInt(3, id_control);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try {
              con.close();
          } catch (SQLException ex) {
              Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
  }
  
   // ------Bandera para cuando se actualiza el saldo y la cuota------//
  public void actualizarBandera(boolean bandera,int id_control){
      Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE ficha_control_lote SET bandera = ? WHERE id_control = ?");
            ps.setBoolean(1, bandera);
            ps.setInt(2, id_control);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try {
              con.close();
          } catch (SQLException ex) {
              Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
  }
  
   // ------Actualizo la nota de un cliente------//
  public void actualizarNota(String nota,int id_control){
      Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE ficha_control_lote SET nota = ? WHERE id_control = ?");
            ps.setString(1, nota);
            ps.setInt(2, id_control);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          try {
              con.close();
          } catch (SQLException ex) {
              Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
  }
  
  //---------Baja logica de un cliente----------//
  public String obtenerNota(int id_control){
        ResultSet rs = null;
        String nota= "";
        Connection connection = null;
        try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT nota FROM ficha_control_lote WHERE id_control = '"+id_control+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              nota = rs.getString(1);
          }
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
          try {
              connection.close();
          } catch (SQLException ex) {
              Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        return nota;
  }
    
}
