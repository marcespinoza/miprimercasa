/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Lote;
import conexion.Conexion;
import java.sql.Connection;
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
public class LoteDAO {
    
      Conexion conexion;
      

    public LoteDAO() {
        conexion = new Conexion();
    }
    
    public void editarPropiedad(int vendido, String barrio,String manzana, String parcela){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE lote SET vendido = ? where barrio = ? and manzana =? and parcela=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, vendido);
            preparedStmt.setString(2, barrio);
            preparedStmt.setString(3, manzana);
            preparedStmt.setString(4, parcela);
            int count = preparedStmt.executeUpdate();      
            System.out.print("contador"+count);
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.print("contadorerror"+ex.getMessage());
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    
     public List<Lote> obtenerPropietarioxLote(int id_control){
       ResultSet rs = null;
       Connection connection = null;
       List<Lote> lote = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT l.propietario_Apellidos, l.propietario_Nombres, l.propietario_cuit, l.propietario_nro_recibo FROM lote l inner JOIN ficha_control_lote f where f.lote_barrio = l.barrio and f.Lote_Manzana=l.Manzana and f.lote_parcela=l.parcela and f.Id_control='"+id_control+"'";
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Lote l = new Lote();
                l.setApellidoPropietario(rs.getString(1));
                l.setNombrePropietario(rs.getString(2));
                l.setPropietario_cuit(rs.getString(3));
                l.setNroRecibo(rs.getInt(4));
                lote.add(l);
           } 
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
       return lote;
    }
     
     public List<Lote> obtenerBarrios(String apellidos, String nombres){
          ResultSet rs = null;
          Connection connection = null;
          List<Lote> lotes = new ArrayList<>();
          try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT barrio from lote where vendido=0 and propietario_Apellidos='"+apellidos+"' and propietario_nombres='"+nombres+"' "; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Lote l = new Lote();
                l.setBarrio(rs.getString(1));
                lotes.add(l);
           }  
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
 }    
    
            
     public List<Lote> manzanasPorBarrio(String barrio){
       ResultSet rs = null;
       Connection connection = null;
       List<Lote> lotes = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT manzana from lote where barrio = '"+barrio+"' and vendido=0"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Lote l = new Lote();
                l.setManzana(rs.getString(1));
                lotes.add(l);
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
   }
      
    public List<Lote> parcelasPorManzana(String barrio, String manzana){
       ResultSet rs = null;
       Connection connection = null;
       List<Lote> lotes = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT parcela from lote where barrio = '"+barrio+"' and manzana = '"+manzana+"' and vendido=0"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Lote l = new Lote();
                l.setParcela(rs.getString(1));
                lotes.add(l);
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
 }
      
    public void eliminarLote(String barrio, String manzana, String parcela){
     try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from lote where barrio = ? and manzana = ? and parcela = ?";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.setString(1, barrio);
         ps.setString(2, manzana);
         ps.setString(3, parcela);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
    
    public List<Lote> obtenerLotes(String apellidos, String nombres){
          ResultSet rs = null;
          List<Lote> lotes = new ArrayList<>();
          Connection connection = null;
     try {
          connection = conexion.dataSource.getConnection();  
          String sql = "SELECT barrio, manzana, parcela, observacion, vendido, propietario_cuit from lote where propietario_apellidos =? AND propietario_nombres =? order by barrio "; 
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setString(1, apellidos);
          preparedStatement.setString(2, nombres);
         rs = preparedStatement.executeQuery();
          while (rs.next()) {
                Lote l = new Lote();
                l.setBarrio(rs.getString(1));
                l.setManzana(rs.getString(2));
                l.setParcela(rs.getString(3));
                l.setObservaciones(rs.getString(4));
                l.setVendido(rs.getInt(5));
                l.setPropietario_cuit(rs.getString(6));
                lotes.add(l);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
     } 
      
   public List<Lote> obtenerLotesPorGrupo(String apellidos, String nombres){
          ResultSet rs = null;
          List<Lote> lotes = new ArrayList<>();
          Connection connection = null;
          PreparedStatement ps = null;
     try {
          connection = conexion.dataSource.getConnection();  
          String sql = "SELECT barrio, manzana, parcela, observacion, vendido, propietario_cuit from lote where propietario_apellidos =? AND propietario_nombres =? group by barrio "; 
          ps = connection.prepareStatement(sql);
          ps.setString(1, apellidos);
          ps.setString(2, nombres);
          rs = ps.executeQuery();
          while (rs.next()) {
                Lote l = new Lote();
                l.setBarrio(rs.getString(1));
                l.setManzana(rs.getString(2));
                l.setParcela(rs.getString(3));
                l.setObservaciones(rs.getString(4));
                l.setVendido(rs.getInt(5));
                l.setPropietario_cuit(rs.getString(6));
                lotes.add(l);
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
              try {
                  ps.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
     }  

    public int agregarLote(String barrio, String manzana, String parcela, String propietario_apellidos, String propietario_nombres, String propietario_cuit, String propietario_nro_recibo, String idPropietario){
        Connection connection = null;
        PreparedStatement stmt = null;
        int i = 0;
        try {
           connection = conexion.dataSource.getConnection();
           String query="INSERT INTO lote (barrio, manzana, parcela, propietario_apellidos, propietario_nombres, propietario_cuit, propietario_nro_recibo, id_propietario)VALUES(?,?,?,?,?,?,?,?)  ON DUPLICATE KEY UPDATE barrio = VALUES(barrio), manzana = VALUES(manzana), parcela = VALUES(parcela),  propietario_apellidos=VALUES(propietario_apellidos), propietario_nombres = VALUES(propietario_nombres), propietario_cuit = VALUES(propietario_cuit), propietario_nro_recibo = VALUES(propietario_nro_recibo), id_propietario = VALUES(id_propietario)";
           stmt = connection.prepareStatement(query);
           stmt.setString(1, barrio);
           stmt.setString(2, manzana);
           stmt.setString(3, parcela);
           stmt.setString(4, propietario_apellidos);
           stmt.setString(5, propietario_nombres);
           stmt.setString(6, propietario_cuit);
           stmt.setString(7, propietario_nro_recibo);
           stmt.setString(8, idPropietario);
           i = stmt.executeUpdate();
       } catch (SQLException ex) {
           System.out.println(ex.getMessage().toString()+" Alta lote");
       }finally{
            try {
                stmt.close();
            } catch (SQLException ex) {
                 System.out.println(ex.getMessage().toString());
                Logger.getLogger(LoteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                 System.out.println(ex.getMessage().toString());
                Logger.getLogger(LoteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    } 
    
     public int editarLote(String backup_barrio, String backup_manzana, String backup_parcela, String barrio,String manzana, String parcela, String observaciones){
         int flag = 0;
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE lote SET barrio = ?, manzana = ?, parcela = ?, observacion = ? where barrio = ? and manzana =? and parcela=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, barrio);
            preparedStmt.setString(2, manzana);
            preparedStmt.setString(3, parcela);
            preparedStmt.setString(4, observaciones);
            preparedStmt.setString(5, backup_barrio);
            preparedStmt.setString(6, backup_manzana);
            preparedStmt.setString(7, backup_parcela);
            flag = preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
 }
     
        
   public void actualizarObservacion(String observaciones, String barrio, int manzana, int parcela){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE lote SET observacion = ? WHERE barrio = ? and manzana = ? and parcela = ?");
            ps.setString(1,observaciones);
            ps.setString(2,barrio);
            ps.setInt(3, manzana);
            ps.setInt(4, parcela);
            ps.executeUpdate();            
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
   
   public List<Lote> propietariosPorBarrios(String barrio){
          ResultSet rs = null;
          Connection connection = null;
          Statement st = null;
          List<Lote> lotes = new ArrayList<>();
          try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT propietario_apellidos, propietario_nombres from lote where barrio='"+barrio+"' "; 
          st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Lote l = new Lote();
                l.setApellidoPropietario(rs.getString(1));
                l.setNombrePropietario(rs.getString(2));
                lotes.add(l);
           }  
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }finally{
              try {
                  rs.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  st.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return lotes;
 }    
    
}
