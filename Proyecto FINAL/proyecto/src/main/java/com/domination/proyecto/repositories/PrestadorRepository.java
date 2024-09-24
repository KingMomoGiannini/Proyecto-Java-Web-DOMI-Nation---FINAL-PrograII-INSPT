package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Prestador;
import java.util.Optional;

import com.domination.proyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Integer> {
    
    @Query("SELECT p FROM Prestador p WHERE p.idPrestador = :idPrestador")
    public Optional<Prestador> findByIdPrestador(@Param("idPrestador") Integer idPrestador);
    
    Prestador findByNombreUsuarioAndPassword(String nombreUsuario, String password);
    Prestador findByIdUsuario(int idUsuario);

}
