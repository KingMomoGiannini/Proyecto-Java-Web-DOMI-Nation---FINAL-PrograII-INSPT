package com.domination.proyecto.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name= "usuario_idusuario",referencedColumnName = "idUsuario")
public class Cliente extends Usuario {
    
    
    @Column(name = "id_cliente")
    private int idCliente;
    
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "usuario_idusuario")
//    private Usuario usuario;
//    
    
    public Cliente() {
    }

    public Cliente(int idCliente, Usuario usuario) {
        this.idCliente = idCliente;
//        this.usuario = usuario;
    }

    public Cliente(String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(nombreUsuario, nombre, apellido, email, password, celular, rol);
//        this.usuario = usuario;
    }

    public Cliente(int idCliente, Usuario usuario, int idUsuario, String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(idUsuario, nombreUsuario, nombre, apellido, email, password, celular, rol);
        this.idCliente = idCliente;
//        this.usuario = usuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

//    public Usuario getUsuario() {
//        return usuario;
//    }

//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
    
}
