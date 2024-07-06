package com.domination.proyecto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name= "usuario_idusuario",referencedColumnName = "idUsuario")
public class Cliente extends Usuario {
    
    @Column(name = "id_cliente")
    private int idCliente; 
    
    public Cliente() {
    }

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(nombreUsuario, nombre, apellido, email, password, celular, rol);
    }

    public Cliente(int idCliente, String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(nombreUsuario, nombre, apellido, email, password, celular, rol);
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
}
