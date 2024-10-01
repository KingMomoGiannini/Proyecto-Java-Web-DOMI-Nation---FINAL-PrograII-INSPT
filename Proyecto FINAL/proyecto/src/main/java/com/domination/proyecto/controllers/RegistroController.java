package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.services.AdministradorService;
import com.domination.proyecto.services.ClienteService;
import com.domination.proyecto.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class RegistroController {

    @Autowired
    private ClienteService usuarioClienteService;

    @Autowired
    private PrestadorService usuarioPrestadorService;
    
    @Autowired
    private AdministradorService administradorService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registrarse")
    public String showRegistrationForm() {
        return "registro"; // Esto devuelve la vista "registro.jsp"
    }

    @PostMapping("/registrarse")
    public String registerUser(@RequestParam String nomCliente, @RequestParam String apeCliente, @RequestParam String celular, 
                               @RequestParam String email, @RequestParam String user, @RequestParam String pass, 
                               @RequestParam(required = false) String tipoUsuario, Model model) {
        try {
            String redireccion = null;
            Administrador administrador = administradorService.getDefaultAdministrador(); // Obtener el administrador semi hardcodeado
            pass = passwordEncoder.encode(pass);
            if (user.equals(administradorService.getDefaultAdministrador().getNombreUsuario()) || user.equals("Admin") || user.equals("ADMIN")){
                throw new ObjectNotFoundException("El nombre de usuario que intentas utilizar no es valido.");
            }
            if ("prestador".equals(tipoUsuario)) {
                // Si el checkbox se encuentra tildado, crea un usuario de tipo prestador
                Prestador elPrestador = new Prestador(user, nomCliente, apeCliente, email, pass, celular,tipoUsuario);
                elPrestador.setAdministrador(administrador);
                usuarioPrestadorService.registerUser(elPrestador);
                model.addAttribute("elUsuario", elPrestador);
            } else {
                // Por defecto, se crea un usuario de tipo cliente
                if (tipoUsuario == null) {
                    tipoUsuario = "cliente";
                }

                Cliente elUsuario = new Cliente(user, nomCliente, apeCliente, email, pass, celular, "cliente");
                elUsuario.setAdministrador(administrador);
                usuarioClienteService.registerUser(elUsuario);
                model.addAttribute("elUsuario", elUsuario);
            }

            return "felicitacion"; // Esto devuelve la vista "felicitacion.jsp"
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", "El nombre de usuario ya existe. Por favor, elige otro.");
            return "registro"; // Devuelve al formulario de registro
        } catch (ObjectNotFoundException ex){
            model.addAttribute("error", ex.getMessage());
            return "registro";
        } catch (Exception ex) {
            model.addAttribute("error", "Hubo un problema con el registro: " + ex.getMessage());
            return "registro";
        }
    }
}