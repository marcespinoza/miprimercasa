/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.Frame.Ventana;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ReciboDAO {
    
    Conexion conexion;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ReciboDAO.class.getName());


    public ReciboDAO() {
        conexion = new Conexion();
    }
    
     public int altaRecibo(int nro_recibo, String apellido_propietario, String nombre_propietario){
         int id_control = 0;
         Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
         Connection con = null;
         PreparedStatement ps = null;
     try {
          con = conexion.dataSource.getConnection();
          String insertar = "Insert into recibo(nro_recibo, "
                                                 + "apellido_propietario, "
                                                 + "nombre_propietario, "
                                                 + "timestamp) values (?, ?, ?, ?)";
          ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          ps.setInt(1, nro_recibo);
          ps.setString(2, apellido_propietario);
          ps.setString(3, nombre_propietario);
          ps.setTimestamp(4, timestamp);
          ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.info(Ventana.nombreUsuario.getText() + " - Error insertar recibo: " + e.getMessage());
        }finally{
             try{
                 ps.close();
             }catch(SQLException ex){
                 Logger.getLogger(ReciboDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
                 con.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ReciboDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
     }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
}
