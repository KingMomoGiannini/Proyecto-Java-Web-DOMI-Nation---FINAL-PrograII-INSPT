package com.domination.proyecto.services;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.repositories.PrestadorRepository;
import com.domination.proyecto.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PrestadorService {
    
    @Autowired
    private PrestadorRepository prestadorRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Prestador> getAllPrestadores() {
        return prestadorRepository.findAll();
    }
    
    public Prestador registerUser(Prestador user) {
        return prestadorRepository.save(user);
    }
    
    public void deletePrestador(int id) {
        prestadorRepository.deleteById(id);
    }

    public Optional<Prestador> getPrestadorById(int idPrestador) {
        return prestadorRepository.findById(idPrestador);
    }
    
    public Prestador findByNombreUsuarioAndPassword(String nombreUsuario, String password) {
        return prestadorRepository.findByNombreUsuarioAndPassword(nombreUsuario, password);
    }

    public Prestador findByIdUsuario(int idUsuario) {
        return prestadorRepository.findByIdUsuario(idUsuario);
    }

    public Optional<Prestador> findByIdPrestador(int idPrestador){
        return prestadorRepository.findByIdPrestador(idPrestador);
    }
}
