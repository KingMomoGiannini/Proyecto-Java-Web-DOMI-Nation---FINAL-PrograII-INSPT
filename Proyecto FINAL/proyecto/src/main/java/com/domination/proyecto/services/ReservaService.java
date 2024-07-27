package com.domination.proyecto.services;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import com.domination.proyecto.repositories.ClienteRepository;
import com.domination.proyecto.repositories.ReservaRepository;
import com.domination.proyecto.repositories.SalaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SalaRepository salaRepository;

    public List<Reserva> getReservasByCliente(int clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        return reservaRepository.findByCliente(cliente);
    }

    public List<Reserva> getReservasBySala(int salaId) {
        Sala sala = salaRepository.findById(salaId).orElse(null);
        return reservaRepository.findBySala(sala);
    }

    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    
    public void deleteReserva(Reserva reserva){
        reservaRepository.delete(reserva);
    }
    
    public void deleteReservaById(int idReserva){
        Reserva laReserva = reservaRepository.findById(idReserva).orElse(null);
        reservaRepository.delete(laReserva);
    }
    
    public List<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    public void deleteReservasBySala(Sala sala) {
        reservaRepository.deleteAllBySala(sala);
    }
    
}
