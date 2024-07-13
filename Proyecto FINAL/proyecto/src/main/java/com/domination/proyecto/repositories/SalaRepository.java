
package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer>{
    
}
