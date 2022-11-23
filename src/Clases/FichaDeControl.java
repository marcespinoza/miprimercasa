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
public class FichaDeControl {
    
    BigDecimal precio;
    BigDecimal gastos;
    BigDecimal bolsaCemento;
    Date bandera;
    String dimension;
    int cantidadCuotas;
    BigDecimal cuotaPura;
    String barrio; 
    String manzana;
    String parcela;
    String nombre;
    String apellido;
    int bandera_cemento;

    public int getBandera_cemento() {
        return bandera_cemento;
    }

    public void setBandera_cemento(int bandera_cemento) {
        this.bandera_cemento = bandera_cemento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    

    public String getNombre(){
        return nombre;
   }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public FichaDeControl() {
    }

    public Date getBandera() {
        return bandera;
    }

    public void setBandera(Date bandera) {
        this.bandera = bandera;
    }
  
    
    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public BigDecimal getCuotaPura() {
        return cuotaPura;
    }

    public void setCuotaPura(BigDecimal cuotaPura) {
        this.cuotaPura = cuotaPura;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getBolsaCemento() {
        return bolsaCemento;
    }

    public void setBolsaCemento(BigDecimal bolsaCemento) {
        this.bolsaCemento = bolsaCemento;
    }   
    
    
}
