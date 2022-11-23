/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ActualizacionEmpleado;
import Clases.ClientesPorCriterio;
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
public class ActualizacionEmpleadoDAO {
    
     Conexion conexion;
    
    public ActualizacionEmpleadoDAO(){
       conexion = new Conexion();
    }
    
    public List<ActualizacionEmpleado> listaActualizaciones(int id_control){         
         ResultSet rs = null;
         Connection connection = null;
         List<ActualizacionEmpleado> actualizaciones = new ArrayList<>();
       try {
         connection = conexion.dataSource.getConnection();
         String listar = "SELECT * from actualizacion_empleado a where a.id_control='"+id_control+"'";
         Statement st = connection.createStatement();
         rs = st.executeQuery(listar);
         while(rs.next()){
            ActualizacionEmpleado a = new ActualizacionEmpleado();
            a.setFecha(rs.getDate(3));
            a.setPorcentaje(rs.getByte(4));
            a.setCuota_anterior(rs.getBigDecimal(5));
            a.setCuota_actualizada(rs.getBigDecimal(6));
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
    
    public void altaActualizacion(int id_control, Date fecha, BigDecimal porcentaje, BigDecimal valor_anterior, BigDecimal valor_actualizado) throws SQLException{
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into actualizacion_empleado (id_control, fecha, porcentaje, saldo_anterior, saldo_nuevo, timestamp) values (?,?,?,?,?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setInt(1, id_control);
            stmt.setDate(2, fecha);
            stmt.setBigDecimal(3, porcentaje);
            stmt.setBigDecimal(4, valor_anterior);
            stmt.setBigDecimal(5, valor_actualizado);
            stmt.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
            con.close();
            if(stmt!=null){
               stmt.close();
            }
        }
    }
    
}
