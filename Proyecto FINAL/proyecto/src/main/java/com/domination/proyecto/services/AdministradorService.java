package com.domination.proyecto.services;

import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.repositories.AdministradorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    
    @Autowired
    private AdministradorRepository adminRepository;
    
    public Administrador getDefaultAdministrador() {
        // Suponiendo que tienes un administrador por defecto con ID 1
        return adminRepository.findById(1).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }
    
    public List<Administrador> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Administrador registerUser(Administrador user) {
        return adminRepository.save(user);
    }
    
    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }

    public Optional<Administrador> getAdminById(int id) {
        return adminRepository.findById(id);
    }
    
    public Administrador findByNombreUsuarioAndPassword(String nombreUsuario, String password) {
        return adminRepository.findByNombreUsuarioAndPassword(nombreUsuario, password);
    }
}
