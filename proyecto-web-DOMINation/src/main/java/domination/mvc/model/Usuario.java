/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

/**
 *
 * @author giann
 */
public class Usuario {
    private int idUsuario;
    private String nomUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String celular;
    private String rol;
    private int idAdmin;

    public Usuario(String nomUsuario, String nombre, String apellido, String email, String password, String celular,String rol) {
        this(0, nomUsuario, nombre, apellido, email, password, celular, rol);
    }

    public Usuario(int idUsuario, String nomUsuario, String nombre, String apellido, String email, String password, String celular, String rol) {
        this.idUsuario = idUsuario;
        this.nomUsuario = nomUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.celular = celular;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
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
    
    public int getIdAdmin(){
        return Administrador.getId();
    }

    public String getRol() {
        return rol;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
