/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.SalaEnsayo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Seba
 */
public class SalaDAO implements DAO<SalaEnsayo,Integer>{

    @Override
    public List<SalaEnsayo> getAll() throws Exception {
        List<SalaEnsayo> salas = new LinkedList();
        String query = "SELECT * FROM sala"; //Creamos el SELECT * para la base de datos
        try(Connection conexion =ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);//Transformamos la cadena guardada en query, en una sentencia SQL
            ResultSet rs = ps.executeQuery();)
        {    
            while(rs.next()){
                salas.add(rsRowToSala(rs));
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        
        return salas;
    }

    @Override
    public void create(SalaEnsayo laSala) throws Exception {
        String query = "INSERT INTO sala (num_sala, valor_hora, sucursal_idsucursal) VALUES (?, ?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1,laSala.getNumSala());
            preparedStatement.setDouble(2,laSala.getValorHora());
            preparedStatement.setInt(3,laSala.getIdSede());
            
            
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    laSala.setIdSala(generatedKeys.getInt(1));// me sirve para obtener el ID generado y lo asigna a laSala
                } else {
                    throw new SQLException("Fallo al obtener el ID de la sala, no se generó automáticamente.");
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear una nueva sala por SQL", ex);
        }
    }

    @Override
    public void update(SalaEnsayo laSala) throws Exception {
        String query = "UPDATE sala SET num_sala = ?, valor_hora = ?, sucursal_idsucursal = ?  WHERE idsala = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1,laSala.getNumSala());
            preparedStatement.setDouble(2,laSala.getValorHora());
            preparedStatement.setInt(3,laSala.getIdSede());
            preparedStatement.setInt(4,laSala.getIdSala());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al actualizar la sala", ex);
        }
    }

    @Override
    public void delete(Integer elId) throws Exception {
        String query = "DELETE FROM sala WHERE idsala = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar una sala", ex);
        }
    }

    @Override
    public SalaEnsayo getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM sala WHERE idsala = ?";
        SalaEnsayo sala = null;

        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    sala = rsRowToSala(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al obtener una sala por ID", ex);
        }

        return sala;
    }

    private SalaEnsayo rsRowToSala(ResultSet rs) throws SQLException {
        int idSala = rs.getInt("idsala");
        int numSala = rs.getInt("num_sala");
        double monto = rs.getDouble("valor_hora");
        int idSede = rs.getInt("sucursal_idsucursal");
        
        return new SalaEnsayo(idSala,numSala,monto,idSede);
    }
    
}
