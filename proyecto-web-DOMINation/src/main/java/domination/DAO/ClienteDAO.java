/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.UsuarioCliente;
import domination.mvc.model.UsuarioPrestador;
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
public class ClienteDAO implements DAO<UsuarioCliente,Integer> {

    @Override
    public List<UsuarioCliente> getAll() throws Exception {
        List<UsuarioCliente> clientes = new LinkedList<>();
        String query = "SELECT * FROM cliente p JOIN usuario u ON p.usuario_idusuario = u.idusuario";
        try (Connection conexion = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                clientes.add(rsRowToCliente(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return clientes;
    }

    @Override
    public void create(UsuarioCliente elCliente) throws Exception {
        String query = "INSERT INTO usuario (nombre_usuario, nombre, apellido, email, password, celular, administrador_idadministrador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, elCliente.getNomUsuario());
            preparedStatement.setString(2, elCliente.getNombre());
            preparedStatement.setString(3, elCliente.getApellido());
            preparedStatement.setString(4, elCliente.getEmail());
            preparedStatement.setString(5, elCliente.getPassword());
            preparedStatement.setString(6, elCliente.getCelular());
            preparedStatement.setInt(7, elCliente.getIdAdmin());

            preparedStatement.executeUpdate();

            // Obtener el ID generado para identificar como cliente
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuarioGenerado = generatedKeys.getInt(1);
                    // Ahora insertar en la tabla cliente
                    insertarEnCliente(idUsuarioGenerado);
                } else {
                    throw new SQLException("Error al obtener el ID del Cliente generado.");
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear un nuevo Cliente", ex);
        }
    }

    @Override
    public void update(UsuarioCliente elObjeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer elId) throws Exception {
        String query = "DELETE FROM cliente WHERE idcliente = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar un cliente", ex);
        }
    }

    @Override
    public UsuarioCliente getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM cliente WHERE idcliente = ?";
        UsuarioCliente usuario = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = rsRowToCliente(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al obtener un prestador por ID", ex);
        }
        return usuario;
    }
    
     private UsuarioCliente rsRowToCliente(ResultSet rs) throws SQLException, Exception {
        int idUsuario = rs.getInt("usuario_idusuario");
        String nomUsuario = rs.getString("nombre_usuario");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String celular = rs.getString("celular");
        int idCliente = rs.getInt("idcliente");

        return new UsuarioCliente(idCliente,idUsuario, nomUsuario, nombre, apellido, email, password, celular,"cliente");
    }
    
    private void insertarEnCliente(int idUsuarioGenerado) throws SQLException {
        String query = "INSERT INTO cliente (usuario_idusuario) VALUES (?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, idUsuarioGenerado);
            preparedStatement.executeUpdate();
        }
    }
    
}
