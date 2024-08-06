package com.domination.proyecto.controllers;

import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.services.AdministradorService;
import com.domination.proyecto.services.ClienteService;
import com.domination.proyecto.services.PrestadorService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.DomicilioService;
import com.domination.proyecto.services.ReservaService;
import com.domination.proyecto.services.SalaService;
import com.domination.proyecto.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private DomicilioService domicilioService;
    
    @Autowired
    private AdministradorService adminService;
    
    @Autowired
    private SalaService salaService;
    
    @Autowired
    private ReservaService reservaService;

    @GetMapping("/login")
    public String mostrarLoginForm() {
        return "login"; // Esto devuelve la vista "login.jsp"
    }

    @PostMapping("/ingresar")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Usuario elUser = null;
        Administrador elAdmin = null;
        try {
            if ("admin".equals(username)) {
                elAdmin = adminService.findByNombreUsuarioAndPassword(username, password);
            } else {
                elUser = prestadorService.findByNombreUsuarioAndPassword(username, password);
                if (elUser == null) {
                    elUser = clienteService.findByNombreUsuarioAndPassword(username, password);
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
                return "login";
            }
        } catch (Exception ex) {
            session.setAttribute("hayError", true);
            session.setAttribute("mensajeError", "Hubo un problema con el login: " + ex.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "redirect:/"; // Redirige al usuario a la página de inicio
    }

    private void setupPrestadorSessionAttributes(HttpSession session, Prestador prestador) {
        List<Sucursal> sucursales = prestador.getSucursales();
        List<Domicilio> domicilios = new LinkedList<>();
        List<Reserva> reservas = new LinkedList<>();
        
        for (Sucursal sucursal : sucursales) {
            domicilios.add(sucursal.getDomicilio());
            List<Sala> salas = sucursal.getSalas();
            for (Sala sala : salas) {
                reservas.addAll(sala.getReservas());
            }
        }
        session.setAttribute("sedesDelUsuario", sucursales);
        session.setAttribute("domiciliosDeSedes", domicilios);
        session.setAttribute("lasReservas", reservas);
    }

    private void setupClienteSessionAttributes(HttpSession session, Cliente cliente) {
        List<Sucursal> sucursales = sucursalService.findAll();
        List<Domicilio> domicilios = domicilioService.findAll();
        List<Reserva> reservasCliente = cliente.getReservas();
        session.setAttribute("sedesDelUsuario", sucursales);
        session.setAttribute("domiciliosDeSedes", domicilios);
        session.setAttribute("lasReservas", reservasCliente);
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