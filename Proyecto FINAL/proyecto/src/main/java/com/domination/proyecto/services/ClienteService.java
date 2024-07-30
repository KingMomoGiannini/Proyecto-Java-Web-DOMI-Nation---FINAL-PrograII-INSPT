package com.domination.proyecto.services;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.repositories.ClienteRepository;
import com.domination.proyecto.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
    
    public Cliente registerUser(Cliente user) {
        return clienteRepository.save(user);
    }

    public void deleteCliente(int id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> getClienteById(int idCliente) {
        return clienteRepository.findByIdCliente(idCliente);
    }
    
    public Cliente findByNombreUsuarioAndPassword(String nombreUsuario, String password) {
        return clienteRepository.findByNombreUsuarioAndPassword(nombreUsuario, password);
    }
}
