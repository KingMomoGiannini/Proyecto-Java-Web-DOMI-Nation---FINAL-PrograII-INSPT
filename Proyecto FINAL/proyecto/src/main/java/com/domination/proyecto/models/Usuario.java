package com.domination.proyecto.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idUsuario;
    
    private String nombreUsuario;
    
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String celular;
    
    private String rol;
    
    // Constructor sin parámetros
    public Usuario() {}

    public Usuario(String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        this(0, nombreUsuario, nombre, apellido, email, password, celular, rol);
    }

    public Usuario(int idUsuario, String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.celular = celular;
        this.rol = rol;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCelular() {
        return celular;
    }

    public String getRol() {
        return rol;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nombreUsuario = nomUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
