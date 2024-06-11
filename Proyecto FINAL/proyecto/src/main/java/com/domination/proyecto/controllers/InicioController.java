/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.domination.proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Seba
 */
@Controller
public class InicioController {

    //Método que muestra la página de inicio
    @GetMapping("/")
    public String mostrarInicio(){

        return "index";

    }
    
}
