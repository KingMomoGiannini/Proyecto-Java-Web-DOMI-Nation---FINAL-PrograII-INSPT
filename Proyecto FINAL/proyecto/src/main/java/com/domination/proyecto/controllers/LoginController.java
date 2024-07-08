package com.domination.proyecto.controllers;

import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.services.ClienteService;
import com.domination.proyecto.services.PrestadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PrestadorService prestadorService;

    @GetMapping("/ingresar")
    public String mostrarLoginForm() {
        return "login"; // Esto devuelve la vista "login.jsp"
    }

    @PostMapping("/ingresar")
    public String loginUser(@RequestParam String user, @RequestParam String pass, Model model, HttpSession session) {
        try {
            Usuario usuario = prestadorService.findByNombreUsuarioAndPassword(user, pass);
            if (usuario == null) {
                usuario = clienteService.findByNombreUsuarioAndPassword(user, pass);
            }
            
            if (usuario != null) {
                session.setAttribute("userLogueado", usuario);
                session.setMaxInactiveInterval(3600); // 1 hora

                if (usuario instanceof Prestador) {
                    return "redirect:/inicio"; // Vista de inicio para prestadores
                } else if (usuario instanceof Cliente) {
                    return "redirect:/inicio"; // Vista de inicio para clientes
                }
            } else {
                model.addAttribute("error", "Usuario o contraseña incorrectos.");
                return "login"; // Retorna a la vista de login con un mensaje de error
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Hubo un problema con el login: " + ex.getMessage());
            return "login"; // Retorna a la vista de login con un mensaje de error
        }
        return "login"; // Retorna a la vista de login en caso de que algo falle
    }
    
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "redirect:/"; // Redirige al usuario a la página de inicio
    }
    
}