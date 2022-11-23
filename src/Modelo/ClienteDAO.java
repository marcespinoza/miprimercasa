/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Cliente;
import Clases.ClientesPorCriterio;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.Global;


/**
 *
 * @author Marcelo
 */
public class ClienteDAO {
    
    Conexion conexion;
    
    public ClienteDAO(){
       conexion = new Conexion();
    }
    
 public List<ClientesPorCriterio> clientesPorLotes(){
     ResultSet rs = null;
     Connection connection = null;
     Statement st = null;
     List<ClientesPorCriterio> clientesPorLotes = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, h.baja, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.fecha_actualizacion, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela,f.bandera_cemento, f.cuota_pura, IF(COUNT(lc.Nro_cuota)=0,0, COUNT(lc.Nro_cuota)) as cuotas, IFNULL((SELECT lc.Fecha FROM linea_control_lote lc WHERE lc.id_control=f.Id_control ORDER BY lc.Fecha DESC LIMIT 1 ), DATE_FORMAT('2000-01-01','%Y-%m-%d')) as ultimaCuota, SUM(lc.Haber) as total, (Select fecha from linea_control_lote lc where lc.id_control=f.id_control and lc.nro_cuota=0)as suscripcion, f.bandera   from ((cliente c LEFT JOIN cliente_tiene_lote h ON c.Dni = h.cliente_Dni) left join ficha_control_lote f on f.Id_control=h.Id_control) LEFT JOIN linea_control_lote lc ON lc.id_control=f.Id_control GROUP BY f.Id_control, c.dni, ifnull(f.Id_control, c.Dni) order by c.apellidos"; 
          st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
            ClientesPorCriterio cpp = new ClientesPorCriterio();
            cpp.setDni(rs.getInt(1));
            cpp.setApellidos(rs.getString(2));
            cpp.setNombres(rs.getString(3));
            cpp.setFecha_nacimiento(rs.getDate(4));
            cpp.setBarrio_cliente(rs.getString(5));
            cpp.setCalle_cliente(rs.getString(6));
            cpp.setNro_cliente(rs.getInt(7));
            cpp.setTelefono1(rs.getString(8));
            cpp.setTelefono2(rs.getString(9));
            cpp.setTrabajo(rs.getString(10));
            cpp.setBaja(rs.getInt(11));
            cpp.setIdControl(rs.getString(12));
            cpp.setCantidad_cuotas(rs.getInt(13));
            cpp.setGastos(rs.getBigDecimal(14));
            cpp.setBolsa_cemento(rs.getBigDecimal(15));
            cpp.setFecha_actualizacion(rs.getDate(16));
            cpp.setBarrio(rs.getString(17));
            cpp.setManzana(rs.getString(18));
            cpp.setParcela(rs.getString(19));
            cpp.setBandera_cemento(rs.getByte(20));
            cpp.setCuota_pura(rs.getBigDecimal(21));
            cpp.setCuotas(rs.getInt(22));
            cpp.setUltimaCuota(rs.getDate(23));
            cpp.setTotal(rs.getBigDecimal(24));
            cpp.setFecha_suscripcion(rs.getDate(25));
            cpp.setBandera(rs.getDate(26));
            clientesPorLotes.add(cpp);

         }
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }finally{
              try{
                  rs.close();
              }catch(SQLException ex){
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try{
                  st.close();
              }catch(SQLException ex){
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return clientesPorLotes;
 }
 
  public ResultSet clientesSinLotes(){
     ResultSet rs = null;
     Statement st = null;
     Connection con = null;
     try {
          con = conexion.dataSource.getConnection();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres FROM (cliente c left OUTER JOIN cliente_tiene_lote h on c.Dni=h.cliente_Dni) WHERE h.cliente_Dni is null GROUP BY c.dni order by c.apellidos"; 
          st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        } 
     return rs;
 }
 
  public ResultSet clientes(){
     ResultSet rs = null;
     Statement st = null;
     Connection con = null;
     try {
          con = conexion.dataSource.getConnection();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres FROM (cliente c INNER JOIN cliente_tiene_lote h on c.Dni=h.cliente_Dni) GROUP BY c.dni order by c.apellidos"; 
          st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        } 
     return rs;
 }
 
  public List<Cliente> clientePorLote(int id_control){
     ResultSet rs = null;
     Connection connection = null;
     List<Cliente> cliente = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT c.Apellidos, c.Nombres,c.barrio, c.calle, c.numero, c.cuil FROM cliente c LEFT JOIN cliente_tiene_lote f ON c.Dni = f.cliente_Dni where f.id_control = '"+id_control+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              Cliente c = new Cliente();
              c.setApellidos(rs.getString(1));
              c.setNombres(rs.getString(2));
              c.setBarrio(rs.getString(3));
              c.setCalle(rs.getString(4));
              c.setNumero(rs.getInt(5));
              c.setCuil(rs.getString(6));
              cliente.add(c);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             connection.close();
         } catch (SQLException ex) {
             Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return cliente;
     }
  
     public void altaClientesXLotes(int dni, int id_control) throws SQLException{
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into cliente_tiene_lote (cliente_dni, id_control) values (?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setInt(1, dni);
            stmt.setInt(2, id_control);
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

 
    public int altaCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, String numero, String telefono1, String telefono2, String trabajo, String cuil) throws SQLException{
       int filasAfectadas=0;
       PreparedStatement ps = null;
       Connection con = null;
       try {
         con = conexion.dataSource.getConnection();
         String insertar = "Insert into cliente(dni, apellidos, nombres, fecha_nacimiento, barrio, calle, numero, telefono1, telefono2, trabajo, cuil) values ('"+dni+"','"+apellidos+"','"+nombres+"','"+fecha_nacimiento+"','"+barrio+"','"+calle+"','"+numero+"','"+telefono1+"','"+telefono2+"','"+trabajo+"', '"+cuil+"')";
         ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
       } catch (SQLException e) { 
          //--------1062 es el codigo de error para claves duplicadas------//
          System.out.println(e.getMessage());
         if(e.getErrorCode() == 1062){
           filasAfectadas = 0; 
       }         
       }finally{
         con.close();
         if(ps!=null){
            ps.close();
         }
     }
     return filasAfectadas;
 }
 
 public void eliminarCliente(int dni){
     try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from cliente where dni = '"+dni+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
 public void editarCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo, int clave_cliente){
            Connection con = null;
            PreparedStatement preparedStmt = null;
        try {
            con = conexion.dataSource.getConnection();
            String query = "UPDATE cliente SET dni = ?, nombres = ?, apellidos = ?, fecha_nacimiento = ?, barrio = ?, calle = ?, numero = ?, telefono1 = ?, telefono2 = ?, trabajo = ? where dni = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, dni);
            preparedStmt.setString(2, nombres);
            preparedStmt.setString(3, apellidos);
            preparedStmt.setDate(4, fecha_nacimiento);
            preparedStmt.setString(5, barrio);
            preparedStmt.setString(6, calle);
            preparedStmt.setInt(7, numero);
            preparedStmt.setString(8, telefono1);
            preparedStmt.setString(9, telefono2);
            preparedStmt.setString(10, trabajo);
            preparedStmt.setInt(11, clave_cliente);
            preparedStmt.executeUpdate(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{                  
            try {
                con.close();
                preparedStmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 }
 public ResultSet buscarCliente(int dni){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT  c.apellidos, c.nombres, c.fecha_nacimiento, c.dni, c.barrio, c.calle, c.numero, c.telefono1, c.telefono2, c.trabajo, r.apellidos, r.nombres, r.telefono, r.parentesco from cliente c inner join referencia r on c.dni=r.cliente_dni where dni = '"+dni+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
 
   public List<ClientesPorCriterio> clientesPorPropietarios(String apellido, String nombre, int dias, String lote){
     ResultSet rs;
     Connection connection = null;
     PreparedStatement ps = null;
     List<ClientesPorCriterio> clientesPorPropietario = new ArrayList<>();
     try {
      connection = conexion.dataSource.getConnection();
      String listar = "SELECT DISTINCT  c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, cl.baja, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.fecha_actualizacion, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela, f.bandera_cemento, f.cuota_pura, "
              + "      (SELECT COUNT(lc.Nro_cuota)-1 as cuotas from linea_control_lote lc where lc.id_control=f.Id_control) as cuotas, "
              + "      (SELECT lc.Fecha FROM linea_control_lote lc WHERE lc.id_control=f.Id_control ORDER BY lc.Fecha DESC LIMIT 1 ) as ultimaCuota,"
              + "      " + "      (Select SUM(lc.haber) from linea_control_lote lc where lc.id_control=f.id_control)as total, "
              + "      (Select fecha from linea_control_lote lc where lc.id_control=f.id_control and lc.nro_cuota=0)as suscripcion, "
              + "      f.bandera  from ((cliente c INNER join cliente_tiene_lote cl on c.dni=cl.cliente_dni) INNER join ficha_control_lote f on cl.id_control=f.id_control and f.lote_barrio=?) INNER join lote l on f.lote_barrio=l.barrio AND l.manzana=f.lote_manzana AND l.parcela=f.lote_parcela where l.propietario_Apellidos=? and l.propietario_Nombres=? and cl.baja=0 order by c.apellidos, c.nombres"; 
      ps = connection.prepareStatement(listar);
      ps.setString(1, lote);
      ps.setString(2, apellido);
      ps.setString(3, nombre);
      rs = ps.executeQuery();
      while (rs.next()) {
        ClientesPorCriterio cpp = new ClientesPorCriterio();
        cpp.setDni(rs.getInt(1));
        cpp.setApellidos(rs.getString(2));
        cpp.setNombres(rs.getString(3));
        cpp.setFecha_nacimiento(rs.getDate(4));
        cpp.setBarrio_cliente(rs.getString(5));
        cpp.setCalle_cliente(rs.getString(6));
        cpp.setNro_cliente(rs.getInt(7));
        cpp.setTelefono1(rs.getString(8));
        cpp.setTelefono2(rs.getString(9));
        cpp.setTrabajo(rs.getString(10));
        cpp.setBaja(rs.getInt(11));
        cpp.setIdControl(rs.getString(12));
        cpp.setCantidad_cuotas(rs.getInt(13));
        cpp.setGastos(rs.getBigDecimal(14));
        cpp.setBolsa_cemento(rs.getBigDecimal(15));
        cpp.setFecha_actualizacion(rs.getDate(16));
        cpp.setBarrio(rs.getString(17));
        cpp.setManzana(rs.getString(18));
        cpp.setParcela(rs.getString(19));            
        cpp.setBandera_cemento(rs.getByte(20));
        cpp.setCuota_pura(rs.getBigDecimal(21));
        cpp.setCuotas(rs.getInt(22));
        cpp.setUltimaCuota(rs.getDate(23));
        cpp.setTotal(rs.getBigDecimal(24));
        cpp.setFecha_suscripcion(rs.getDate(25));
        cpp.setBandera(rs.getTimestamp(26));
        Instant instant2 = null;
        long days = 0;
        Date time = rs.getDate(23);
        if(time!=null){      
          instant2 = Instant.ofEpochMilli(rs.getDate(23).getTime());
          LocalDate fecha_pago = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()).toLocalDate();
             days = ChronoUnit.DAYS.between(fecha_pago, LocalDate.now());         
         if(days>=dias){
            clientesPorPropietario.add(cpp); 
        };  }                     
     }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
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
     return clientesPorPropietario;
 }

    public List<ClientesPorCriterio> clientesPorManzana(String apellido, String nombre, int manzana){
     ResultSet rs;
     Connection connection = null;
     PreparedStatement preparedStmt = null;
     List<ClientesPorCriterio> clientesPorPropietario = new ArrayList<>();
     try {
      connection = conexion.dataSource.getConnection();
      String listar = "SELECT DISTINCT  c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, cl.baja, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.fecha_actualizacion, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela, f.bandera_cemento, f.cuota_pura, "
              + "      (SELECT COUNT(lc.Nro_cuota)-1 as cuotas from linea_control_lote lc where lc.id_control=f.Id_control) as cuotas, "
              + "      (SELECT lc.Fecha FROM linea_control_lote lc WHERE lc.id_control=f.Id_control ORDER BY lc.Fecha DESC LIMIT 1 ) as ultimaCuota,"
              + "      " + "      (Select SUM(lc.haber) from linea_control_lote lc where lc.id_control=f.id_control)as total, "
              + "      (Select fecha from linea_control_lote lc where lc.id_control=f.id_control and lc.nro_cuota=0)as suscripcion, "
              + "      f.bandera  from ((cliente c INNER join cliente_tiene_lote cl on c.dni=cl.cliente_dni) INNER join ficha_control_lote f on cl.id_control=f.id_control) INNER join lote l on f.lote_barrio=l.barrio AND l.manzana=f.lote_manzana AND l.parcela=f.lote_parcela where l.propietario_Apellidos=? and l.propietario_Nombres=? and cl.baja=0 order by c.apellidos, c.nombres"; 
      preparedStmt = connection.prepareStatement(listar);
      preparedStmt.setString(1, apellido);
      preparedStmt.setString(2, nombre);
      rs = preparedStmt.executeQuery();
      while (rs.next()) {
        ClientesPorCriterio cpp = new ClientesPorCriterio();
        cpp.setDni(rs.getInt(1));
        cpp.setApellidos(rs.getString(2));
        cpp.setNombres(rs.getString(3));
        cpp.setFecha_nacimiento(rs.getDate(4));
        cpp.setBarrio_cliente(rs.getString(5));
        cpp.setCalle_cliente(rs.getString(6));
        cpp.setNro_cliente(rs.getInt(7));
        cpp.setTelefono1(rs.getString(8));
        cpp.setTelefono2(rs.getString(9));
        cpp.setTrabajo(rs.getString(10));
        cpp.setBaja(rs.getInt(11));
        cpp.setIdControl(rs.getString(12));
        cpp.setCantidad_cuotas(rs.getInt(13));
        cpp.setGastos(rs.getBigDecimal(14));
        cpp.setBolsa_cemento(rs.getBigDecimal(15));
        cpp.setFecha_actualizacion(rs.getDate(16));
        cpp.setBarrio(rs.getString(17));
        cpp.setManzana(rs.getString(18));
        cpp.setParcela(rs.getString(19));            
        cpp.setBandera_cemento(rs.getByte(20));
        cpp.setCuota_pura(rs.getBigDecimal(21));
        cpp.setCuotas(rs.getInt(22));
        cpp.setUltimaCuota(rs.getDate(23));
        cpp.setTotal(rs.getBigDecimal(24));
        cpp.setFecha_suscripcion(rs.getDate(25));
        cpp.setBandera(rs.getTimestamp(26));
        Instant instant2 = null;
        long days = 0;
        Date time = rs.getDate(23);
        if(time!=null){      
          instant2 = Instant.ofEpochMilli(rs.getDate(23).getTime());
          LocalDate fecha_pago = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()).toLocalDate();
             days = ChronoUnit.DAYS.between(fecha_pago, LocalDate.now());         
         if(days>=manzana){
            clientesPorPropietario.add(cpp); 
        };  }                     
     }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }finally{
      try {
          connection.close();
      } catch (SQLException ex) {
          Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
     return clientesPorPropietario;
 }
    
}
