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
 * @author Marcelo
 */
public class Cuota {
    
    int nro_cuota;
    Date fecha;
    String detalle;
    BigDecimal cuota_pura;
    BigDecimal gastos_administrativos;
    BigDecimal debe;
    BigDecimal haber;
    BigDecimal saldo;
    BigDecimal cemente_debe;
    BigDecimal cemento_haber;
    BigDecimal cemento_saldo;
    String observaciones;
    int nro_recibo;
    int id_recibo;
    String tipo_pago;
    String actualizacionCuota;
    int indice;

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
    
    

    public Cuota() {
    }

    public int getNro_cuota() {
        return nro_cuota;
    }

    public String getActualizacionCuota() {
        return actualizacionCuota;
    }

    public void setActualizacionCuota(String actualizacionCuota) {
        this.actualizacionCuota = actualizacionCuota;
    }
    
    

    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getCuota_pura() {
        return cuota_pura;
    }

    public void setCuota_pura(BigDecimal cuota_pura) {
        this.cuota_pura = cuota_pura;
    }

    public BigDecimal getGastos_administrativos() {
        return gastos_administrativos;
    }

    public void setGastos_administrativos(BigDecimal gastos_administrativos) {
        this.gastos_administrativos = gastos_administrativos;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getCemente_debe() {
        return cemente_debe;
    }

    public void setCemento_debe(BigDecimal cemente_debe) {
        this.cemente_debe = cemente_debe;
    }

    public BigDecimal getCemento_haber() {
        return cemento_haber;
    }

    public void setCemento_haber(BigDecimal cemento_haber) {
        this.cemento_haber = cemento_haber;
    }

    public BigDecimal getCemento_saldo() {
        return cemento_saldo;
    }

    public void setCemento_saldo(BigDecimal cemento_saldo) {
        this.cemento_saldo = cemento_saldo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getNro_recibo() {
        return nro_recibo;
    }

    public void setNro_recibo(int nro_recibo) {
        this.nro_recibo = nro_recibo;
    }

    public int getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(int id_recibo) {
        this.id_recibo = id_recibo;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }
    
    
    
    
}
