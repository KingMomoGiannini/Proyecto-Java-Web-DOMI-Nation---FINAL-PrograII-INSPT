package com.domination.proyecto.services;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
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
    private ClienteService clienteService;

    @Autowired
    private SalaRepository salaRepository;
    
    @Autowired
    private SalaService salaService;

    public List<Reserva> getReservasByCliente(int clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId)
                                        .orElseThrow(() -> new ObjectNotFoundException("Cliente no encontrado con id: " + clienteId));
        return reservaRepository.findByCliente(cliente);
    }

    public List<Reserva> getReservasBySala(int salaId) {
        Sala sala = salaService.findById(salaId)
                                                .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada con id: " + salaId));
        return reservaRepository.findBySala(sala);
    }

    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    
    public void deleteReserva(Reserva reserva){
        reservaRepository.delete(reserva);
    }
    
    public List<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    public void deleteReservasBySala(Sala sala) {
        reservaRepository.deleteAllBySala(sala);
    }
    
    public Optional<Reserva> findByIdReserva(int idReserva){
        return reservaRepository.findById(idReserva);
    }
    
    public void deleteByIdReserva(int idReserva){
        reservaRepository.deleteByIdReserva(idReserva);
    }
    
    public void deleteById(int id){
        reservaRepository.deleteById(id);
    }
}
