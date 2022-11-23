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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class DepartamentoDAO {
    
    Conexion conexion;

    public DepartamentoDAO() {
        conexion = new Conexion();
    }
    
      public void editarDepartamento(String torre,int piso, int dpto){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE departamento SET vendido = ? where torre = ? and piso =? and dpto=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, 1);
            preparedStmt.setString(2, torre);
            preparedStmt.setInt(3, piso);
            preparedStmt.setInt(4, dpto);
            preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    
     public ResultSet obtenerPropietarioxdepartamento(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT l.propietario_Apellidos, l.propietario_Nombres, l.propietario_cuit, l.propietario_nro_recibo FROM departamento l inner JOIN ficha_control f where f.departamento_piso=l.piso and f.Id_control='"+id_control+"'";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
     
     public ResultSet obtenerTorres(String apellidos, String nombres){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT torre from departamento where vendido=0 and propietario_Apellidos='"+apellidos+"' and propietario_nombres='"+nombres+"' "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }    
    
            
      public ResultSet pisosPortorre(String torre){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT piso from departamento where torre = '"+torre+"' and vendido=0"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
      
      public ResultSet dptosPorpiso(String torre, int piso){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT dpto from departamento where torre = '"+torre+"' and piso = '"+piso+"' and vendido=0"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
      
      public void eliminarDepartamento(String torre, int piso, int dpto){
     try {
         Connection con = conexion.dataSource.getConnection();
         System.out.println(torre+piso+dpto);
         String eliminar = "delete from departamento where torre = ? and piso = ? and dpto = ?";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.setString(1, torre);
         ps.setInt(2, piso);
         ps.setInt(3, dpto);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
      
      public List<Lote> obtenerDepartamentos(String apellidos, String nombres){
          List<Lote>lotes = null;
          ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();   
          String sql = "SELECT torre, piso, dpto, vendido, propietario_cuit from departamento where propietario_apellidos =? AND propietario_nombres =? "; 
          PreparedStatement preparedStatement = con.prepareStatement(sql);
          preparedStatement.setString(1, apellidos);
          preparedStatement.setString(2, nombres);
         rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return lotes;
     }  

    public void agregarDepartamento(String torre, String piso, String dpto, String propietario_apellidos, String propietario_nombres, String propietario_cuit, String propietario_nro_recibo){
       try {
           Connection con = conexion.dataSource.getConnection();
           String query="INSERT INTO departamento (torre, piso, dpto, propietario_apellidos, propietario_nombres, propietario_cuit, propietario_nro_recibo)VALUES(?,?,?,?,?,?,?)  ON DUPLICATE KEY UPDATE torre = VALUES(torre), piso = VALUES(piso), dpto = VALUES(dpto),  propietario_apellidos=VALUES(propietario_apellidos), propietario_nombres = VALUES(propietario_nombres), propietario_cuit = VALUES(propietario_cuit), propietario_nro_recibo = VALUES(propietario_nro_recibo)";
           PreparedStatement stmt = con.prepareStatement(query);
           stmt.setString(1, torre);
           stmt.setString(2, piso);
           stmt.setString(3, dpto);
           stmt.setString(4, propietario_apellidos);
           stmt.setString(5, propietario_nombres);
           stmt.setString(6, propietario_cuit);
           stmt.setString(7, propietario_nro_recibo);
           stmt.executeUpdate();
       } catch (SQLException ex) {
           System.out.println(ex.getMessage().toString());
       }
} 
    
    
}
