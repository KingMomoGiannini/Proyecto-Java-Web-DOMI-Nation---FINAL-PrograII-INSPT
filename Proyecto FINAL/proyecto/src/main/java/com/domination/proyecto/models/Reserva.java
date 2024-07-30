package com.domination.proyecto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReserva;
    
    @Column(name = "duracion")
    private double duracion;
    
    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;
    
    @Column(name = "hora_fin")
    private LocalDateTime horaFin;
    
    @Column(name = "monto")
    private double monto;
    
    @ManyToOne
    @JoinColumn(name = "sala_idsala", referencedColumnName = "idSala")
    private Sala sala;
    
    @ManyToOne
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    public Reserva() {
    }

    public Reserva(double duracion, LocalDateTime horaInicio, LocalDateTime horaFin, double monto) {
        this.duracion = duracion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monto = monto;
    }

    public Reserva(double duracion, LocalDateTime horaInicio, LocalDateTime horaFin, double monto, Sala sala, Cliente cliente) {
        this.duracion = duracion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monto = monto;
        this.sala = sala;
        this.cliente = cliente;
    }
    
    public int getIdReserva() {
        return idReserva;
    }

    public double getDuracion() {
        return duracion;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public double getMonto() {
        return monto;
    }

    public Sala getSala() {
        return sala;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public LocalTime getHoraMinutoInicio(){
        return this.horaInicio.toLocalTime();
    }
    
    public LocalTime getHoraMinutoFin(){
        return this.horaFin.toLocalTime();
    }
    
    public LocalDate getSoloFecha(){
        return this.horaInicio.toLocalDate();
    }
}
