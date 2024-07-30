package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByNombreUsuarioAndPassword(String nombreUsuario, String password);
    
    @Query("SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    public Optional<Cliente> findByIdCliente(int idCliente);
    
}
