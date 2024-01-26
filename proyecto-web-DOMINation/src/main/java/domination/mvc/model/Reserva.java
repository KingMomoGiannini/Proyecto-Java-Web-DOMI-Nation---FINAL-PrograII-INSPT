/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Seba
 */
public class Reserva {
    private int idReserva;
    private double duracion;
    private int idSala;
    private int idCliente;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private double monto;

    public Reserva() {
    }

    public Reserva(double duracion, int idSala, int idCliente, LocalDateTime horaInicio, LocalDateTime horaFin, double monto) {
        this(0,duracion,idSala,idCliente,horaInicio,horaFin,monto);
    }
    
    public Reserva(int idReserva, double duracion, int idSala, int idCliente, LocalDateTime horaInicio, LocalDateTime horaFin, double monto) {
        this.idReserva = idReserva;
        this.duracion = duracion;
        this.idSala = idSala;
        this.idCliente = idCliente;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monto = monto;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public double getDuracion() {
        return duracion;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }
    
    public double getMonto() {
        return monto;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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
