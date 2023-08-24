package com.example.app_biblioteca.modelo;

import com.example.app_biblioteca.enums.Orientacion;

import java.io.Serializable;

public class Domicilio implements Serializable {

    private int numero;
    private String calle;
    private String colonia;
    private String ciudad;
    private String estado;
    private int codigoPostal;
    private String pais;
    private Orientacion orientacion;

    public Domicilio(){

    }


    public Domicilio(int numero, String calle, String colonia, String ciudad, String estado, int codigoPostal, String pais, Orientacion orientacion) {
        this.numero = numero;
        this.calle = calle;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.orientacion = orientacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "numero=" + numero +
                ", calle='" + calle + '\'' +
                ", colonia='" + colonia + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", pais='" + pais + '\'' +
                ", orientacion=" + orientacion +
                '}';
    }
}
