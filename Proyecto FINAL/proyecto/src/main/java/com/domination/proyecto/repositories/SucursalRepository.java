/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.domination.proyecto.repositories;

import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Sucursal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer>{

    //public List<Sucursal> findByIdPrestador(int idPrestador);

    public List<Sucursal> findByPrestador(Prestador prestador);
    
}
