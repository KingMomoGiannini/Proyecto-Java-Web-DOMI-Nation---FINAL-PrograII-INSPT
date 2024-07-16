package com.domination.proyecto.controllers;

import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.services.AdministradorService;
import com.domination.proyecto.services.ClienteService;
import com.domination.proyecto.services.PrestadorService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.DomicilioService;
import com.domination.proyecto.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AdministradorService adminService;

    @GetMapping("/ingresar")
    public String mostrarLoginForm() {
        return "login"; // Esto devuelve la vista "login.jsp"
    }

    @PostMapping("/ingresar")
    public String loginUser(@RequestParam String user, @RequestParam String pass, Model model, HttpSession session) {
        Usuario elUser = null;
        Administrador elAdmin = null;
        try {
            if ("admin".equals(user)) {
                elAdmin = adminService.findByNombreUsuarioAndPassword(user, pass);
            } else {
                elUser = prestadorService.findByNombreUsuarioAndPassword(user, pass);
                if (elUser == null) {
                    elUser = clienteService.findByNombreUsuarioAndPassword(user, pass);
                }
            }

            if (elUser != null) {
                session.setAttribute("userLogueado", elUser);
                session.setMaxInactiveInterval(3600); // 1 hora

                if (elUser instanceof Prestador) {
                    setupPrestadorSessionAttributes(session, (Prestador) elUser);
                } else if (elUser instanceof Cliente) {
                    setupClienteSessionAttributes(session, (Cliente) elUser);
                }
                return "redirect:/inicio";
            }
            else if (elAdmin != null) {
                session.setAttribute("userLogueado", elAdmin);
                session.setMaxInactiveInterval(3600);
                setupAdminSessionAttributes(session);
                return "redirect:/inicio";
            }
            else {
                model.addAttribute("hayError", true);
                model.addAttribute("mensajeError", "Usuario o contraseña incorrectos.");
                return "login";
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Hubo un problema con el login: " + ex.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "redirect:/"; // Redirige al usuario a la página de inicio
    }

    private void setupPrestadorSessionAttributes(HttpSession session, Prestador prestador) {
        List<Sucursal> sucursales = sucursalService.findByPrestador(prestador);
        List<Domicilio> domicilios = null;
        for (Sucursal sucursal : sucursales) {
            domicilios.add(sucursal.getDomicilio());
        }
        session.setAttribute("sedesDelUsuario", sucursales);
        session.setAttribute("domiciliosDeSedes", domicilios);
    }

    private void setupClienteSessionAttributes(HttpSession session, Cliente cliente) {
        List<Sucursal> sucursales = sucursalService.findAll();
        List<Domicilio> domicilios = domicilioService.findAll();
        session.setAttribute("sedesDelUsuario", sucursales);
        session.setAttribute("domiciliosDeSedes", domicilios);
    }

    private void setupAdminSessionAttributes(HttpSession session) {
        List<Sucursal> sucursales = sucursalService.findAll();
        List<Domicilio> domicilios = domicilioService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        session.setAttribute("sedesDelUsuario", sucursales);
        session.setAttribute("domiciliosDeSedes", domicilios);
        session.setAttribute("usuarios", usuarios);
    }
}