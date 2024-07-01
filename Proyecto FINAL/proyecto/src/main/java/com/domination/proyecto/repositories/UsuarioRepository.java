package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Usuario findByNombreUsuario(String nombreUsuario);
    
}
