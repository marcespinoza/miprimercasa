/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Propietario;
import Clases.Vendedor;
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
 * @author csiad
 */
public class VendedorDAO {
    
    Conexion conexion;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MinutaDAO.class.getName());
    
    public VendedorDAO() {
        conexion = new Conexion();
    }
    
        public int agregarVendedor(int dni, String apellido, String nombre){
        Connection connection = null;
        PreparedStatement stmt = null;
        int i = 0;
        try {
           connection = conexion.dataSource.getConnection();
           String query="INSERT INTO vendedor (dni, apellido, nombre)VALUES(?,?,?)  ON DUPLICATE KEY UPDATE dni = VALUES(dni), apellido = VALUES(apellido), nombre = VALUES(nombre)";
           stmt = connection.prepareStatement(query);
           stmt.setInt(1, dni);
           stmt.setString(2, apellido);
           stmt.setString(3, nombre);
           i = stmt.executeUpdate();
       } catch (SQLException ex) {
           System.out.println(ex.getMessage().toString()+" Alta Vendedor");
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
        
        public void editarVendedor(int dni, String apellido,String nombre){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE vendedor SET apellido = ?,  nombre =? where dni = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, apellido);
            preparedStmt.setString(2, nombre);
            preparedStmt.setInt(3, dni);
            preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
        
         public List<Vendedor> obtenerVendedores(){
          List<Vendedor> vendedores = new ArrayList<Vendedor>();
          ResultSet rs = null;
          Vendedor vendedor = null;
          Connection connection = null;
          try {
          connection = conexion.dataSource.getConnection();         
          String listar = "SELECT * from vendedor"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()){
              vendedor = new Vendedor();
              vendedor.setDni(rs.getInt(1));
              vendedor.setApellido(rs.getString(2));
              vendedor.setNombre(rs.getString(3));
              vendedores.add(vendedor);
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
     return vendedores;
     } 
    
}
