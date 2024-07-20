package com.domination.proyecto.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Sucursal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSucursal;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "cant_salas")
    private int cantSalas;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    
    @Column(name = "telefono")
    private String telefono;
    
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "prestador_idprestador", referencedColumnName = "id_prestador")
    private Prestador prestador;
    
    @OneToOne(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private Domicilio domicilio;
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<Sala> salas;

    public Sucursal() {
    }

    public Sucursal(String nombre, int cantSalas, LocalTime horaInicio, LocalTime horaFin, String telefono, Prestador prestador) {
        this.nombre = nombre;
        this.cantSalas = cantSalas;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.telefono = telefono;
        this.prestador = prestador;
    }

    public Sucursal(int idSucursal, String nombre, int cantSalas, LocalTime horaInicio, LocalTime horaFin, String telefono) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.cantSalas = cantSalas;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.telefono = telefono;
    }
    
    public Sucursal(int idSucursal, String nombre, int cantSalas, LocalTime horaInicio, LocalTime horaFin, String telefono, Prestador prestador) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.cantSalas = cantSalas;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.telefono = telefono;
        this.prestador = prestador;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantSalas() {
        return cantSalas;
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

    public Prestador getPrestador() {
        return prestador;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantSalas(int cantSalas) {
        this.cantSalas = cantSalas;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
    
}
