package com.domination.proyecto.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name= "usuario_idusuario",referencedColumnName = "idUsuario")
public class Cliente extends Usuario {
    
    @Column(name = "id_cliente")
    private int idCliente; 
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;
    
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

    public List<Reserva> getReservas() {
        return reservas;
    }
    
}
