/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Usuario;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class UsuarioDAO {

     Conexion conexion;
    
    public UsuarioDAO() {
        conexion = new Conexion();
    }
    
     public Usuario validarUsuario(String usuario, String contraseña, String tipo_usuario){
     ResultSet rs = null;
     Usuario user = null;
     Connection con = null;
     try {          
           con = conexion.dataSource.getConnection();         
          String listar = "SELECT usuario, tipo_usuario, nombres, apellidos from usuario where usuario = '"+usuario+"' and contraseña = '"+contraseña+"' and tipo_usuario = '"+tipo_usuario+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
          if (rs.next()){
              user = new Usuario();
              user.setUsuario(rs.getString(1));
              user.setTipoUsuario(rs.getString(2));
              user.setNombres(rs.getString(3));
              user.setApellidos(rs.getString(4)); 
          }
     } catch (Exception e) {
            System.out.println(e.getMessage());
     }finally{
         try {
             con.close();
         } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return user;
 }
     
     public void actualizarUsuario(String usuario, String contraseña, String apellidos, String nombres, String tipo_operador){
         try {
             Connection con = conexion.dataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement("update usuario " +
                     "set usuario= ?  ,  " +
                     "contraseña= ? , " +
                     "apellidos= ? , " +
                     "nombres= ? , " +
                     "tipo_usuario= ?  " +
                     "where usuario= ? ");             
             pstm.setString(1,usuario);
             pstm.setString(2,contraseña);
             pstm.setString(3,apellidos);
             pstm.setString(4,nombres);
             pstm.setString(5,tipo_operador);
             pstm.setString(6,usuario);
             pstm.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     public ResultSet obtenerUsuarios(){
          ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();         
          String listar = "SELECT *from usuario"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
     public void eliminarUsuario(String usuario){
          try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from usuario where usuario='"+usuario+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
     }
     public int agregarUsuario(String usuario, String contraseña, String apellidos, String nombres, String tipo_usuario){
          int filasAfectadas=0;
     try {
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into usuario(usuario, contraseña, apellidos, nombres, tipo_usuario) values ('"+usuario+"','"+contraseña+"','"+apellidos+"','"+nombres+"','"+tipo_usuario+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage());
     }
     return filasAfectadas;
     }
    
}
