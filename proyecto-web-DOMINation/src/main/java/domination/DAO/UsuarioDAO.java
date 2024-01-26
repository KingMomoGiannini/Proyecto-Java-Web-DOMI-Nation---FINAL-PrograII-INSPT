/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.Usuario;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author giann
 */
public class UsuarioDAO implements DAO<Usuario,Integer>{ 
        
    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios  = new LinkedList();
        String query = "SELECT * FROM usuario"; //Creamos el SELECT * para la base de datos
        try(Connection conexion =ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);//Transformamos la cadena guardada en query, en una sentencia SQL
            ResultSet rs = ps.executeQuery();)
        {    
            while(rs.next()){
                usuarios.add(rsRowToUsuario(rs));//El metodo utilizado debería convertir una rs en un objeto de tipo Usuario
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        
        return usuarios;
    }

    @Override
    public void create(Usuario elUser) throws Exception {
        String query = "INSERT INTO usuario (nombre_usuario, nombre, apellido, email, password, celular, administrador_idadministrador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, elUser.getNomUsuario());
            preparedStatement.setString(2, elUser.getNombre());
            preparedStatement.setString(3, elUser.getApellido());
            preparedStatement.setString(4, elUser.getEmail());
            preparedStatement.setString(5, elUser.getPassword());
            preparedStatement.setString(6, elUser.getCelular());
            preparedStatement.setInt(7, elUser.getIdAdmin());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    elUser.setIdUsuario(generatedKeys.getInt(1));// me sirve para obtener el ID generado y lo asigna al usuario
                } else {
                    throw new SQLException("Fallo al obtener el ID del Usuario, no se generó automáticamente.");
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear un nuevo usuario", ex);
        }
    }

    @Override
    public void update(Usuario elUser) throws Exception {
        String query = "UPDATE usuario SET nombre_usuario = ?, nombre = ?, apellido = ?, email = ?, password = ?, celular = ? WHERE idusuario = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, elUser.getNomUsuario());
            preparedStatement.setString(2, elUser.getNombre());
            preparedStatement.setString(3, elUser.getApellido());
            preparedStatement.setString(4, elUser.getEmail());
            preparedStatement.setString(5, elUser.getPassword());
            preparedStatement.setString(6, elUser.getCelular());
            preparedStatement.setInt(7, elUser.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al actualizar el usuario", ex);
        }
    }

    @Override
    public void delete(Integer elId) throws Exception {
        String query = "DELETE FROM usuario WHERE idusuario = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar un usuario", ex);
        }
    }

    @Override
    public Usuario getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM usuario WHERE idusuario = ?";
        Usuario usuario = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = rsRowToUsuario(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al obtener un usuario por ID", ex);
        }
        return usuario;
    }
    
    private Usuario rsRowToUsuario(ResultSet rs) throws SQLException {

        int id = rs.getInt("idusuario");
        String nomUsuario = rs.getString("nombre_usuario");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String celular = rs.getString("celular");
        
        return new Usuario(id,nomUsuario,nombre,apellido,email,password,celular,"prestador");
    }
    
    public Usuario autenticar(String nomUsuario, String pass){
        String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND password = ?";
        Usuario elUser= null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, nomUsuario);
            ps.setString(2,pass);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    elUser = rsRowToUsuario(resultSet);
                }
            } 
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return elUser;
    }
}
