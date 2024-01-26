/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.DAO;

import domination.mvc.model.Domicilio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giann
 */
public class DomicilioDAO implements DAO<Domicilio,Integer> {  
        
    @Override
    public List<Domicilio> getAll() throws SQLException {
        List<Domicilio> domicilios  = new LinkedList();
        String query = "SELECT * FROM domicilio"; //Creamos el SELECT * para la base de datos
        try(Connection conexion =ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);//Transformamos la cadena guardada en query, en una sentencia SQL
            ResultSet rs = ps.executeQuery();)
        {    
            while(rs.next()){
                domicilios.add(rsRowToDomicilio(rs));//El metodo utilizado debería convertir una rs en un objeto de tipo Usuario
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        
        return domicilios;
    }

    @Override
    public void create(Domicilio elDom) throws Exception {
        String query = "INSERT INTO domicilio (calle, altura, localidad, partido, provincia, sucursal_idsucursal) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, elDom.getCalle());
            preparedStatement.setString(2, elDom.getAltura());
            preparedStatement.setString(3, elDom.getLocalidad());
            preparedStatement.setString(4, elDom.getPartido());
            preparedStatement.setString(5, elDom.getProvincia());
            preparedStatement.setInt(6, elDom.getIdSucursal());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    elDom.setId(generatedKeys.getInt(1));// me sirve para obtener el ID generado y lo asigna a laSede
                } else {
                    throw new SQLException("Fallo al obtener el ID del domicilio, no se generó automáticamente.");
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Error al crear un nuevo domicilio", ex);
        }
    }

    @Override
    public void update(Domicilio elDom) throws Exception {
        String query = "UPDATE domicilio SET calle = ?, altura = ?, localidad = ?, partido = ?, provincia = ?, sucursal_idsucursal = ? WHERE iddomicilio = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, elDom.getCalle());
            preparedStatement.setString(2, elDom.getAltura());
            preparedStatement.setString(3, elDom.getLocalidad());
            preparedStatement.setString(4, elDom.getPartido());
            preparedStatement.setString(5, elDom.getProvincia());
            preparedStatement.setInt(6, elDom.getIdSucursal());
            preparedStatement.setInt(7, elDom.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al actualizar el domicilio", ex);
        }
    }

    @Override
    public void delete(Integer elId) throws Exception {
        String query = "DELETE FROM domicilio WHERE iddomicilio = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar un domicilio", ex);
        }
    }

    private Domicilio rsRowToDomicilio(ResultSet rs) throws SQLException {

        int id = rs.getInt("iddomicilio");
        String calle = rs.getString("calle");
        String altura= rs.getString("altura");
        String localidad = rs.getString("localidad");
        String partido = rs.getString("partido");
        String provincia = rs.getString("provincia");
        int idSucursal = rs.getInt("sucursal_idsucursal");
        
        return new Domicilio (id, provincia, localidad, partido, calle, altura,idSucursal);
    }

    @Override
    public Domicilio getByID(Integer elId) throws Exception {
        String query = "SELECT * FROM domicilio WHERE iddomicilio = ?";
        Domicilio domicilio = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    domicilio = new Domicilio(
                            resultSet.getInt("iddomicilio"),
                            resultSet.getString("provincia"),
                            resultSet.getString("localidad"),
                            resultSet.getString("partido"),
                            resultSet.getString("calle"),
                            resultSet.getString("altura"),
                            resultSet.getInt("sucursal_idsucursal")
                    );
                }
            }
        }
        return domicilio;
    }
    
    public Domicilio getByIdSede(Integer elId) throws Exception {
        String query = "SELECT * FROM domicilio WHERE sucursal_idsucursal= ?";
        Domicilio domicilio = null;
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    domicilio = new Domicilio(
                            resultSet.getInt("iddomicilio"),
                            resultSet.getString("provincia"),
                            resultSet.getString("localidad"),
                            resultSet.getString("partido"),
                            resultSet.getString("calle"),
                            resultSet.getString("altura"),
                            resultSet.getInt("sucursal_idsucursal")
                    );
                }
            }
        }
        return domicilio;
    }
    
    /**
     *
     * @param elId
     * @throws Exception
     */
    public void deleteByIdSede(Integer elId) throws Exception {
        String query = "DELETE FROM domicilio WHERE sucursal_idsucursal = ?";
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, elId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar un domicilio", ex);
        }
    }
}
