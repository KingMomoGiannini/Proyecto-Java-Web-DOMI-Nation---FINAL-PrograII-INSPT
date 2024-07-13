package com.domination.proyecto.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "usuario_idusuario", referencedColumnName = "idUsuario")
public class Prestador extends Usuario {
    
    @Column(name = "id_prestador")
    private int idPrestador;
    
    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    private List<Sucursal> sucursales;

    public Prestador() {
    }
    
    public Prestador(int idPrestador) {
        this.idPrestador = idPrestador;
    }

    public Prestador(String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(nombreUsuario, nombre, apellido, email, password, celular, rol);
    }

    public Prestador(int idPrestador, int idUsuario, String nombreUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        super(idUsuario, nombreUsuario, nombre, apellido, email, password, celular, rol);
        this.idPrestador = idPrestador;
    }

    public int getIdPrestador() {
        return idPrestador;
    }

    public void setIdPrestador(int idPrestador) {
        this.idPrestador = idPrestador;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }
    
}
