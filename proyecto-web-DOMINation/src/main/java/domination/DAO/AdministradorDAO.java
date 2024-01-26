/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giann
 */
public class AdministradorDAO implements DAO<Administrador,Integer>{

    @Override
    public List<Administrador> getAll() throws Exception {
        List<Administrador> admins  = new LinkedList();
        String query = "SELECT * FROM administrador"; //Creamos el SELECT * para la base de datos
        try(Connection conexion =ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);//Transformamos la cadena guardada en query, en una sentencia SQL
            ResultSet rs = ps.executeQuery();)
        {    
            while(rs.next()){
                admins.add(rsRowToAdmin(rs));//El metodo utilizado debería convertir una rs en un objeto de tipo Administrador
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        
        return admins;
    }

    @Override
    public void create(Administrador elAdmin) throws Exception {
        String query = "INSERT INTO administrador (nombre_usuario, password) VALUES (?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, elAdmin.getNomUsuario());
            preparedStatement.setString(2, elAdmin.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al crear un nuevo usuario", ex);
        }
    }

    @Override
    public void update(Administrador elObjeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer elId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Administrador getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM administrador WHERE idadministrador = ?";
        Administrador elAdmin = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    elAdmin = rsRowToAdmin(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al obtener un prestador por ID", ex);
        }
        return elAdmin;
    }
    
    public Administrador autenticar(String nomUsuario, String pass){
        String query = "SELECT * FROM administrador WHERE nombre_usuario = ? AND password = ?";
        Administrador elAdmin= null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, nomUsuario);
            ps.setString(2,pass);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    elAdmin = rsRowToAdmin(resultSet);
                }
            } 
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return elAdmin;
    }
    
    private Administrador rsRowToAdmin(ResultSet rs) throws SQLException {
        String nomUsuario = rs.getString("nombre_usuario");
        String password = rs.getString("password");
        return new Administrador(nomUsuario,password);
    }
    
}
