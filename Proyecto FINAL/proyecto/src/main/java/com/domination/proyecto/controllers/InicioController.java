package com.domination.proyecto.controllers;

import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.services.AdministradorService;
import com.domination.proyecto.services.DomicilioService;
import com.domination.proyecto.services.ReservaService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @Autowired
    private SucursalService sucursalService;
    
    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AdministradorService adminService;
    
    
    //Método que muestra la página de inicio
    @GetMapping("/")
    public String mostrarInicio(){

        return "index";

    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Obtén el nombre de usuario autenticado
        String username = authentication.getName();

        // Utiliza el nombre de usuario para obtener el usuario desde tu servicio
        Usuario userLogueado = usuarioService.findByNombreUsuario(username);
        Administrador adminLogueado = null;
        // Añade el usuario o admin a la sesion
        if ("admin".equals(username)) {
            adminLogueado = adminService.getDefaultAdministrador();
            session.setAttribute("userLogueado", adminLogueado);
        }
        else{
            session.setAttribute("userLogueado", userLogueado);
        }
        
        if (userLogueado != null) {
            if (userLogueado.getRol().equals("prestador")) {
                Prestador elPrestador = (Prestador)userLogueado;
                    model.addAttribute("sedesDelUsuario", sucursalService.findByPrestador(elPrestador));
                    List<Reserva> reservasHechas = new LinkedList<>();
                    List<Domicilio> domSucursales = new LinkedList<>();
                    for (Sucursal sucursal : elPrestador.getSucursales()) {
                        domSucursales.add(sucursal.getDomicilio());
                        for (Sala sala : sucursal.getSalas()) {
                            for (Reserva reserva : sala.getReservas()) {
                                reservasHechas.add(reserva);
                            }
                        }
                    }
                    model.addAttribute("domiciliosDeSedes", domSucursales);
                    model.addAttribute("lasReservas", reservasHechas);
            }
            else if (userLogueado.getRol().equals("cliente")) {
                Cliente elCliente = (Cliente)userLogueado;
                model.addAttribute("sedesDelUsuario", sucursalService.findAll());
                model.addAttribute("domiciliosDeSedes", domicilioService.findAll());
                model.addAttribute("lasReservas", reservaService.getReservasByCliente(elCliente.getIdCliente()));
            }
        }
        else if (adminLogueado != null) {
            model.addAttribute("sedesDelUsuario", sucursalService.findAll());
            model.addAttribute("domiciliosDeSedes", domicilioService.findAll());
            model.addAttribute("usuarios", usuarioService.findAll());
            model.addAttribute("lasReservas", reservaService.findAll());
        }
        
        
//        switch (userLogueado.getRol()||adminLogueado.getRol()) {
//            case "administrador":
//                model.addAttribute("sedesDelUsuario", sucursalService.findAll());
//                model.addAttribute("domiciliosDeSedes", domicilioService.findAll());
//                model.addAttribute("usuarios", usuarioService.findAll());
//                model.addAttribute("lasReservas", reservaService.findAll());
//                break;
//            case "prestador":
//                Prestador elPrestador = (Prestador)userLogueado;
//                model.addAttribute("sedesDelUsuario", sucursalService.findByPrestador(elPrestador));
//                List<Reserva> reservasHechas = new LinkedList<>();
//                List<Domicilio> domSucursales = new LinkedList<>();
//                for (Sucursal sucursal : elPrestador.getSucursales()) {
//                    domSucursales.add(sucursal.getDomicilio());
//                    for (Sala sala : sucursal.getSalas()) {
//                        for (Reserva reserva : sala.getReservas()) {
//                            reservasHechas.add(reserva);
//                        }
//                    }
//                }
//                model.addAttribute("domiciliosDeSedes", domSucursales);
//                model.addAttribute("lasReservas", reservasHechas);
//                break;
//            case "cliente":
//                Cliente elCliente = (Cliente)userLogueado;
//                model.addAttribute("sedesDelUsuario", sucursalService.findAll());
//                model.addAttribute("domiciliosDeSedes", domicilioService.findAll());
//                model.addAttribute("lasReservas", reservaService.getReservasByCliente(elCliente.getIdCliente()));
//                break;
//        }  CAMBIÉ ESTO DEBIDO A QUE

        return "inicio";
    }
}
