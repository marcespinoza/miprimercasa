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
public class ActualizacionCemento {

    public ActualizacionCemento() {
    }    
       
    String idControl;
    Date fecha;
    BigDecimal precioAnterior;
    BigDecimal precioActualizado;

    public String getIdControl() {
        return idControl;
    }

    public void setIdControl(String idControl) {
        this.idControl = idControl;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(BigDecimal precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public BigDecimal getPrecioActualizado() {
        return precioActualizado;
    }

    public void setPrecioActualizado(BigDecimal precioActualizado) {
        this.precioActualizado = precioActualizado;
    }
    
}
