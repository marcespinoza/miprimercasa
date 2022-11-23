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
public class DerechoDePosesion {
    

int id_cta;
Date fecha;
BigDecimal monto;
BigDecimal gastos;
BigDecimal cemento_debe;
BigDecimal cemento_haber;
BigDecimal cemento_saldo;
String detalle;
int nro_recibo;

    public int getNro_recibo() {
        return nro_recibo;
    }

    public void setNro_recibo(int nro_recibo) {
        this.nro_recibo = nro_recibo;
    }



    public DerechoDePosesion() {
    }

    public int getId_cta() {
        return id_cta;
    }

    public void setId_cta(int id_cta) {
        this.id_cta = id_cta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getCemento_debe() {
        return cemento_debe;
    }

    public void setCemento_debe(BigDecimal cemento_debe) {
        this.cemento_debe = cemento_debe;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    

}
