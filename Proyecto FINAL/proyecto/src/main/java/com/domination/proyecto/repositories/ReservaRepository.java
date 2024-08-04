package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import java.util.List;

import com.domination.proyecto.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer>{
    List<Reserva> findByCliente(Cliente cliente);
    List<Reserva> findBySala(Sala sala);
    public Reserva findByIdReserva(int idReserva);
    public void deleteAllBySala(Sala sala);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Reserva r WHERE r.idReserva = :idReserva")
    public void deleteByIdReserva(int idReserva);

    List<Reserva> findBySalaSucursal(Sucursal sucursal);
}
