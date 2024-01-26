/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

import domination.DAO.AdministradorDAO;


/**
 *
 * @author giann
 */
public class Administrador {

    private static final int ID = 1;
    private static final String NOM_USUARIO="admin";
    private static final String PASSWORD= "1234";
    private static final String ROL= "administrador";

    public Administrador(String nomUsuario, String password) {
        this(ID,nomUsuario,password,ROL);
    }

    public Administrador(int id, String nomUsuario, String password, String rol) {
    }
    
    public static int getId() {
        return ID;
    }

    public String getNomUsuario() {
        return NOM_USUARIO;
    } 

    public String getPassword() {
        return PASSWORD;
    }

    public String getRol() {
        return ROL;
    }
    
}
