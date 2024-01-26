/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.Domicilio;
import domination.mvc.model.Sede;
import domination.mvc.model.UsuarioPrestador;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giann
 */
public class PrestadorDAO implements DAO<UsuarioPrestador,Integer>{ 
        
    @Override
    public List<UsuarioPrestador> getAll() throws SQLException, Exception {
        List<UsuarioPrestador> prestadores = new LinkedList<>();
        String query = "SELECT * FROM prestador p JOIN usuario u ON p.usuario_idusuario = u.idusuario";
        try (Connection conexion = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                prestadores.add(rsRowToPrestador(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return prestadores;
    }

    @Override
    public void create(UsuarioPrestador elPrestador) throws Exception {
        String query = "INSERT INTO usuario (nombre_usuario, nombre, apellido, email, password, celular, administrador_idadministrador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, elPrestador.getNomUsuario());
            preparedStatement.setString(2, elPrestador.getNombre());
            preparedStatement.setString(3, elPrestador.getApellido());
            preparedStatement.setString(4, elPrestador.getEmail());
            preparedStatement.setString(5, elPrestador.getPassword());
            preparedStatement.setString(6, elPrestador.getCelular());
            preparedStatement.setInt(7, elPrestador.getIdAdmin());

            preparedStatement.executeUpdate();
            
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {// Obtener el ID generado para identificar como prestador.
                if (generatedKeys.next()) {
                    int idUsuarioGenerado = generatedKeys.getInt(1);
                    insertarEnPrestador(idUsuarioGenerado);// Ahora insertar en la tabla prestador - Metodo, mas abajo.
                } else {
                    throw new SQLException("Error al obtener el ID del prestador generado.");
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear un nuevo prestador", ex);
        }
    }

    @Override
    public void update(UsuarioPrestador elUser) throws Exception {
        String query = "UPDATE prestador SET nombre_usuario = ?, nombre = ?, apellido = ?, email = ?, password = ?, celular = ? WHERE idusuario = ?";
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
        String query = "DELETE FROM prestador WHERE idprestador = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar un prestador", ex);
        }
    }

    @Override
    public UsuarioPrestador getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM prestador WHERE idprestador = ?";
        UsuarioPrestador usuario = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = rsRowToPrestador(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al obtener un prestador por ID", ex);
        }
        return usuario;
    }
    
   private UsuarioPrestador rsRowToPrestador(ResultSet rs) throws SQLException, Exception {
        int idUsuario = rs.getInt("usuario_idusuario");
        String nomUsuario = rs.getString("nombre_usuario");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String celular = rs.getString("celular");
        int idPrestador = rs.getInt("idprestador");

        return new UsuarioPrestador(idPrestador,idUsuario, nomUsuario, nombre, apellido, email, password, celular,"prestador");
    }
    
    public UsuarioPrestador autenticar(String nomUsuario, String pass) throws Exception{
        String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND password = ?";
        UsuarioPrestador elUser= null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, nomUsuario);
            ps.setString(2,pass);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    elUser = rsRowToPrestador(resultSet);
                }
            } 
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return elUser;
    }
    
    // para insertar el ID que se genera en la tabla prestador después de insertar en usuario
    private void insertarEnPrestador(int idUsuarioGenerado) throws SQLException {
        String query = "INSERT INTO prestador (usuario_idusuario) VALUES (?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, idUsuarioGenerado);
            preparedStatement.executeUpdate();
        }
    }

}
