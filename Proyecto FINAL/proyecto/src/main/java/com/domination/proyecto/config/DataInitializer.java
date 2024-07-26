package com.domination.proyecto.config;

import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.repositories.AdministradorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {
        if (administradorRepository.count() == 0) {
            
            Administrador admin = new Administrador(1,"admin", passwordEncoder.encode("admin"),"administrador");
            administradorRepository.save(admin);
        }
    }
}
