/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Marceloi7
 */
public class Propietario {
    
String nombres;
String apellidos;
int nro_recibo;
String cuit;
int idPropietario;


    public Propietario() {
    }

    public int getIdPropeietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropeietario) {
        this.idPropietario = idPropeietario;
    }
    

    
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getNro_recibo() {
        return nro_recibo;
    }

    public void setNro_recibo(int nro_recibo) {
        this.nro_recibo = nro_recibo;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

}
