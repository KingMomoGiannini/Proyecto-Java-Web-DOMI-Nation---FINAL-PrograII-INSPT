/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.domination.proyecto.services;

import com.domination.proyecto.models.Sala;
import com.domination.proyecto.repositories.SalaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaService {
    
    @Autowired
    private SalaRepository salaRepository;

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(int id) {
        return salaRepository.findById(id).orElse(null);
    }

    public Sala save(Sala sala) {
        return salaRepository.save(sala);
    }

    public void deleteById(int id) {
        salaRepository.deleteById(id);
    }
}
