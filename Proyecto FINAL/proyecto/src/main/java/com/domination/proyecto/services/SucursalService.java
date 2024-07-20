/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.domination.proyecto.services;

import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.repositories.SucursalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> findByIdSucursal(int idSucursal) {
        return sucursalRepository.findByIdSucursal(idSucursal);
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void deleteById(int id) {
        sucursalRepository.deleteById(id);
    }
    
    public List<Sucursal> findByPrestador(Prestador prestador) {
        return sucursalRepository.findByPrestador(prestador);
    }
}
