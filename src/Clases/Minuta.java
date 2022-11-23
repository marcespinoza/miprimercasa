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
public class Minuta {
    
    Date fechaMinuta;
    String Apellidos;
    String Nombres;
    String manzana;
    String parcela;
    String barrio;
    BigDecimal cobrado;
    BigDecimal gastos;
    BigDecimal rendido;
    int nroCuota;
    String observaciones;
    String categoria;
    int baja;
    int nroRecibo;
    String apellidoP;
    String nombreP;
    int planCuotas;

    public int getPlanCuotas() {
        return planCuotas;
    }

    public void setPlanCuotas(int planCuotas) {
        this.planCuotas = planCuotas;
    }

   

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public int getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(int nroRecibo) {
        this.nroRecibo = nroRecibo;
    }

    public Minuta() {
    }

    public Minuta(Date fechaMinuta, String Apellidos, String Nombres, String manzana,String parcela, BigDecimal cobrado, BigDecimal gastos, BigDecimal rendido, int nroCuota, String observaciones, String categoria, int baja, String barrio) {
        this.fechaMinuta = fechaMinuta;
        this.Apellidos = Apellidos;
        this.Nombres = Nombres;
        this.manzana = manzana;
        this.parcela = parcela;
        this.cobrado = cobrado;
        this.gastos = gastos;
        this.rendido = rendido;
        this.nroCuota = nroCuota;
        this.observaciones = observaciones;
        this.categoria = categoria;
        this.baja = baja;
        this.barrio = barrio;
    }

    public Date getFechaMinuta() {
        return fechaMinuta;
    }

    public void setFechaMinuta(Date fechaMinuta) {
        this.fechaMinuta = fechaMinuta;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getCobrado() {
        return cobrado;
    }

    public void setCobrado(BigDecimal cobrado) {
        this.cobrado = cobrado;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getRendido() {
        return rendido;
    }

    public void setRendido(BigDecimal rendido) {
        this.rendido = rendido;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getBaja() {
        return baja;
    }

    public void setBaja(int baja) {
        this.baja = baja;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    
    
    
}
