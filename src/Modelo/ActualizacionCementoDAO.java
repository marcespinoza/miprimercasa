/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ActualizacionCemento;
import Clases.ActualizacionEmpleado;
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
 * @author Marceloi7
 */
public class ActualizacionCementoDAO {
    
     Conexion conexion;

    public ActualizacionCementoDAO() {
        conexion = new Conexion();
    }
     
     public List<ActualizacionCemento> listaActualizaciones(int id_control){         
         ResultSet rs = null;
         Connection connection = null;
         List<ActualizacionCemento> actualizaciones = new ArrayList<>();
       try {
         connection = conexion.dataSource.getConnection();
         String listar = "SELECT * from actualizacion_cemento a where a.id_control='"+id_control+"'";
         Statement st = connection.createStatement();
         rs = st.executeQuery(listar);
         while(rs.next()){
            ActualizacionCemento a = new ActualizacionCemento();
            a.setFecha(rs.getDate(3));
            a.setPrecioAnterior(rs.getBigDecimal(4));
            a.setPrecioActualizado(rs.getBigDecimal(5));
            actualizaciones.add(a);
        }
       }catch (SQLException ex) {
          System.out.println(ex.getMessage());
       }finally{
         try {
           connection.close();
         } catch (SQLException ex) {
           Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
        return actualizaciones;   
    }
         
     public void actualizarCemento(String id_control, Date fecha, BigDecimal precio_anterior, BigDecimal precio_actualizado) {
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into actualizacion_cemento (id_control, fecha, precio_anterior, precio_actualizado, timestamp) values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setString(1, id_control);
            stmt.setDate(2, fecha);
            stmt.setBigDecimal(3, precio_anterior);
            stmt.setBigDecimal(4, precio_actualizado);
            stmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
             try {
                 con.close();
                 if(stmt!=null){
                     stmt.close();
                 }} catch (SQLException ex) {
                 System.out.println(ex.getMessage());
                 Logger.getLogger(ActualizacionCementoDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }
    
}
