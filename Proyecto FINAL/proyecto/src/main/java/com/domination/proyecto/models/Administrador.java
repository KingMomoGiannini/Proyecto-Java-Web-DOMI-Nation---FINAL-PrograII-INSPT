package com.domination.proyecto.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.util.HashSet;

import java.util.Set;


@Entity
public class Administrador {
    
    @Id
    @Column(name = "id_administrador")
    private int idAdministrador = 1 ;
    
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario ;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "rol")
    private String rol;
    
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios = new HashSet<>();
    
    public Administrador() {
    }

    public Administrador(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
    
    public Administrador(int idAdministrador, String nombreUsuario, String password,String rol) {
        this.idAdministrador = idAdministrador;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol= rol;
    }
    
    public int getIdAdministrador() {
        return idAdministrador;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }
    
    public void setIdAdministrador(int id) {
        this.idAdministrador = id;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getRol() {
        return rol;
    }
    
}
