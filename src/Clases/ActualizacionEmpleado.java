/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Marceloi7
 */

public class ActualizacionEmpleado {
    
    int id_control;
    Date fecha;
    byte porcentaje;
    BigDecimal cuota_anterior;
    BigDecimal cuota_actualizada;

    public ActualizacionEmpleado() {
    }

    public byte getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(byte actualizacion) {
        this.porcentaje = actualizacion;
    }

    public int getId_control() {
        return id_control;
    }

    public void setId_control(int id_control) {
        this.id_control = id_control;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCuota_anterior() {
        return cuota_anterior;
    }

    public void setCuota_anterior(BigDecimal cuota_anterior) {
        this.cuota_anterior = cuota_anterior;
    }

    public BigDecimal getCuota_actualizada() {
        return cuota_actualizada;
    }

    public void setCuota_actualizada(BigDecimal cuota_actualizada) {
        this.cuota_actualizada = cuota_actualizada;
    }
    
    
    
}
