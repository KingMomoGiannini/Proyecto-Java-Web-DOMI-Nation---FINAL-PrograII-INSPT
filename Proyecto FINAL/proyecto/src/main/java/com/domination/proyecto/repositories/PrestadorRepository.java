package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Integer> {
    Prestador findByNombreUsuarioAndPassword(String nombreUsuario, String password);   
}
