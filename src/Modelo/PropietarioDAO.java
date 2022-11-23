/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Propietario;
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
public class PropietarioDAO {

     Conexion conexion;
    
    public PropietarioDAO() {
        conexion = new Conexion();
    }
    
    public int obtenerNroRecibo(String apellidos, String nombres, String cuit){
        int nroRecibo = 0;
         ResultSet rs = null;
         Connection connection = null;
     try {
          connection = conexion.dataSource.getConnection();         
          String listar = "SELECT nro_recibo from propietario where apellidos = '"+apellidos+"' and nombres = '"+nombres+"' and cuit= '"+cuit+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()) { 
                nroRecibo = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             connection.close();
         } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        return nroRecibo;
    }
    
     public ResultSet validarPropietarios(String usuario, String contraseña, String tipo_usuario){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();         
          String listar = "SELECT usuario, tipo_usuario, nombres, apellidos from usuario where usuario = '"+usuario+"' and contraseña = '"+contraseña+"' and tipo_usuario = '"+tipo_usuario+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
     
     public void editarPropietario(String apellidos, String nombres, String cuit, int nro_recibo, String backup_cuit){
          Connection con = null;
         try {
             con = conexion.dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE propietario set apellidos= ? , nombres= ? , cuit= ? , nro_recibo= ? where cuit= ? ");             
             pstm.setString(1,apellidos);
             pstm.setString(2,nombres);
             pstm.setString(3,cuit);
             pstm.setInt(4, nro_recibo);
             pstm.setString(5,backup_cuit);
             pstm.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getMessage());
         } finally{
             try {
                 con.close();
             } catch (Exception e) {
                  System.out.println(e.getMessage());
             }
         }
     }
     
     public void editarNroRecibo(String apellidos, String nombres, String cuit, int nro_recibo){
         Connection con = null;
         try {
             con = conexion.dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("update propietario set nro_recibo= ? where apellidos= ? and nombres=? and cuit=? ");             
             pstm.setInt(1,nro_recibo);
             pstm.setString(2,apellidos);
             pstm.setString(3,nombres);
             pstm.setString(4, cuit);
             pstm.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
             try {
                 con.close();
             } catch (Exception e) {
                  System.out.println(e.getMessage());
             }
         }
     }
     
     public List<Propietario> obtenerPropietarios(){
          List<Propietario> propietarios = new ArrayList<Propietario>();
          ResultSet rs = null;
          Propietario propietario = null;
          Connection connection = null;
     try {
          connection = conexion.dataSource.getConnection();         
          String listar = "SELECT apellidos, nombres, cuit, nro_recibo from propietario"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()){
              propietario = new Propietario();
              propietario.setApellidos(rs.getString(1));
              propietario.setNombres(rs.getString(2));
              propietario.setCuit(rs.getString(3));
              propietario.setNro_recibo(rs.getInt(4));
              propietarios.add(propietario);
          }
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }finally{
         try {
            connection.close();
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }
     }
     return propietarios;
     }
        
      public ResultSet obtenerApellidosXLote(){
          ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();         
          String listar = "SELECT DISTINCT propietario_apellidos from lote"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }      
     
      public ResultSet obtenerApellidosXDepartamento(){
          ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();         
          String listar = "SELECT DISTINCT propietario_apellidos from departamento"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
      
     public List<Propietario> obtenerNombresXLote(String apellido){
          List<Propietario> propietarios = new ArrayList<Propietario>();
          ResultSet rs = null;
          Propietario propietario = null;
          Connection connection = null;
     try {
          connection = conexion.dataSource.getConnection();         
          String listar = "SELECT distinct propietario_nombres from lote where propietario_apellidos='"+apellido+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()){
              propietario = new Propietario();
              propietario.setNombres(rs.getString(1));
              propietarios.add(propietario);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             connection.close();
         } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return propietarios;
     }      
     
      public List<Propietario> obtenerNombresXDepartamento(String apellido){
          List<Propietario> propietarios = new ArrayList<Propietario>();
          ResultSet rs = null;
          Propietario propietario = null;
          Connection connection = null;
     try {
         connection = conexion.dataSource.getConnection();         
          String listar = "SELECT DISTINCT propietario_nombres from departamento where propietario_apellidos='"+apellido+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          if (rs.next()){
              propietario = new Propietario();
              propietario.setNombres(rs.getString(1));
              propietarios.add(propietario);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             connection.close();
         } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return propietarios;
     }
      
       public List<Propietario> obtenerApellidos(){
          ResultSet rs = null;
          Connection con = null; 
          Statement st = null;
          List<Propietario> propietarios = new ArrayList<>();
          try {
           con = conexion.dataSource.getConnection();         
           String listar = "SELECT distinct apellidos from propietario"; 
           st = con.createStatement();
           rs = st.executeQuery(listar);          
           while (rs.next()) {
                Propietario p = new Propietario();
                p.setApellidos(rs.getString(1));
                propietarios.add(p);
            }            
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }finally{
              try {
                  st.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  con.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
      return propietarios;
     }
     
       public List<Propietario> obtenerNombres(String apellidos){
          ResultSet rs = null;
          Connection connection = null;
          Statement st = null;
          List<Propietario> propietarios = new ArrayList<>();
         try {
          connection = conexion.dataSource.getConnection();         
          String listar = "SELECT nombres, cuit from propietario where apellidos='"+apellidos+"'"; 
          st = connection.createStatement();
          rs = st.executeQuery(listar);
            while (rs.next()) {
                Propietario p = new Propietario();
                p.setNombres(rs.getString(1));
                p.setCuit(rs.getString(2));
                propietarios.add(p);
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
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
        return propietarios;
     }  
       
        public List<Propietario> obtenerCuit(String apellidos, String nombres){
          List<Propietario> propietarios = new ArrayList<>();
          Connection connection = null;
          ResultSet rs = null;
           try {
              connection = conexion.dataSource.getConnection();  
              String listar = "SELECT cuit, nro_recibo,id_propietario from propietario where apellidos='"+apellidos+"' and nombres = '"+nombres+"'"; 
              Statement st = connection.createStatement();
              rs = st.executeQuery(listar);
              while (rs.next()) {
                Propietario p = new Propietario();
                p.setCuit(rs.getString(1));
                p.setNro_recibo(rs.getInt(2));
                p.setIdPropietario(rs.getInt(3));
                propietarios.add(p);
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
        return propietarios;
      }  
       
     public void eliminarPropietarios(String cuit){
         try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from propietario where cuit='"+cuit+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
     }
     
     public int agregarPropietarios(String apellidos, String nombres, String cuit, String nroRecibo){
          int filasAfectadas=0;
     try {
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into propietario(apellidos, nombres, cuit, nro_recibo) values ('"+apellidos+"','"+nombres+"','"+cuit+"','"+nroRecibo+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage());
     }
     return filasAfectadas;
     }
    
     public float obtenerGastoXPropietario(int id_control){
       ResultSet rs = null;
       Connection connection = null;
       float porcentaje = 0;
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT p.porcentaje_gasto FROM lote as l INNER JOIN propietario as p ON l.id_propietario=p.id_propietario,\n" +
                " ficha_control_lote AS f\n" +
                "where f.Id_control='"+id_control+"' AND l.barrio=f.lote_barrio AND l.manzana=f.lote_manzana AND l.parcela=f.lote_parcela";
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                porcentaje = (rs.getFloat(1));
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
       return porcentaje;
    }
}
