package com.domination.proyecto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Domicilio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDomicilio;
    
    @Column(name = "calle")
    private String calle;
    
    @Column(name = "altura")
    private String altura;
    
    @Column(name = "localidad")
    private String localidad;
    
    @Column(name = "partido")
    private String partido;
    
    @Column(name = "provincia")
    private String provincia;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sucursal_idsucursal", referencedColumnName = "idSucursal")
    private Sucursal sucursal;

    public Domicilio() {
    }

    public Domicilio(String calle, String altura, String localidad, String partido, String provincia, Sucursal sucursal) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.partido = partido;
        this.provincia = provincia;
        this.sucursal = sucursal;
    }
    
    public Domicilio(int idDomicilio, String calle, String altura, String localidad, String partido, String provincia) {
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.partido = partido;
        this.provincia = provincia;
    }

    public Domicilio(int idDomicilio, String calle, String altura, String localidad, String partido, String provincia, Sucursal sucursal) {
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
        this.partido = partido;
        this.provincia = provincia;
        this.sucursal = sucursal;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public String getCalle() {
        return calle;
    }

    public String getAltura() {
        return altura;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getPartido() {
        return partido;
    }

    public String getProvincia() {
        return provincia;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    
}
