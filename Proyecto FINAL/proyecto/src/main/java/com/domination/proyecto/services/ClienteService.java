package com.domination.proyecto.services;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.repositories.ClienteRepository;
import com.domination.proyecto.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    @Transactional
//    public void createCliente(Cliente cliente) {
//        Usuario usuario = cliente.getUsuario();
//        usuarioRepository.save(usuario);
//        cliente.setUsuario(usuario);
//        clienteRepository.save(cliente);
//    }

    public void deleteCliente(int id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> getClienteById(int id) {
        return clienteRepository.findById(id);
    }
}
