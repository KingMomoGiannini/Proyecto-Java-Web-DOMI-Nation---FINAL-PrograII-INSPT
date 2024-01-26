/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

import java.time.LocalTime;

/**
 *
 * @author giann
 */
public class Sede {
    private int idSede;
    private String nombre;
    private int cantSalas;
    private int idPrestador;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String telefono;


    public Sede() {
    }
    
    public Sede(String nombre, int cantSalas, int idPrestador, LocalTime horaInicio, LocalTime horaFin, String telefono) {
        this(0,nombre,cantSalas, idPrestador, horaInicio, horaFin, telefono);
    }

    public Sede(int idSede, String nombre, int cantSalas, int idPrestador, LocalTime horaInicio, LocalTime horaFin, String telefono) {
        this.idSede = idSede;
        this.nombre = nombre;
        this.cantSalas = cantSalas;
        this.idPrestador = idPrestador;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.telefono = telefono;
    }

    public int getIdSede() {
        return idSede;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantSalas() {
        return cantSalas;
    }

    public int getIdPrestador() {
        return idPrestador;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }
    
}
