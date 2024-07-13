package com.domination.proyecto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSala;
    
    @Column(name = "num_sala")
    private int numSala;
    
    @Column(name = "valor_hora")
    private double valorHora;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sucursal_idsucursal", referencedColumnName = "idSucursal")
    private Sucursal sucursal;

    public Sala() {
    }

    public Sala(int idSala, int numSala, double valorHora, Sucursal sucursal) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.valorHora = valorHora;
        this.sucursal = sucursal;
    }

    public Sala(int idSala, int numSala, double valorHora) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.valorHora = valorHora;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getNumSala() {
        return numSala;
    }

    public double getValorHora() {
        return valorHora;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    
}
