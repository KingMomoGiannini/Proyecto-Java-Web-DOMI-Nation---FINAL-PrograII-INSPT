/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.domination.proyecto.services;

import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.repositories.DomicilioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomicilioService {
    
    @Autowired
    private DomicilioRepository domicilioRepository;

    public List<Domicilio> findAll() {
        return domicilioRepository.findAll();
    }

    public Domicilio findById(int id) {
        return domicilioRepository.findById(id).orElse(null);
    }

    public Domicilio save(Domicilio domicilio) {
        return domicilioRepository.save(domicilio);
    }

    public void deleteById(int id) {
        domicilioRepository.deleteById(id);
    }
}
