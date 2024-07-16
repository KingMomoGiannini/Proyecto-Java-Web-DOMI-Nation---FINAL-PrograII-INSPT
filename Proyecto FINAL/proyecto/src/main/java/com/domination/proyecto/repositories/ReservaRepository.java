package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer>{
    List<Reserva> findByCliente(Cliente cliente);
    List<Reserva> findBySala(Sala sala);
}
