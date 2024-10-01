package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Cliente;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.services.AdministradorService;
import com.domination.proyecto.services.ClienteService;
import com.domination.proyecto.services.PrestadorService;
import com.domination.proyecto.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

    private UsuarioService usuarioService;
    private PrestadorService prestadorService;
    private ClienteService clienteService;
    private AdministradorService administradorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PrestadorService prestadorService, ClienteService clienteService, AdministradorService administradorService) {
        this.usuarioService = usuarioService;
        this.prestadorService = prestadorService;
        this.clienteService = clienteService;
        this.administradorService = administradorService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model,HttpSession session) {
        try {
            String redir = null;
            if(session.getAttribute("userLogueado") instanceof Administrador){
                model.addAttribute("action", "create");
                redir =  "formUsuarios";
            }
            else if(session.getAttribute("userLogueado") instanceof Cliente){
                redir = "redirect:/usuarios/MiCuenta/edit?idUsuario=" + ((Cliente) session.getAttribute("userLogueado")).getIdUsuario();
            }
            else if(session.getAttribute("userLogueado") instanceof Prestador){
                redir = "redirect:/usuarios/MiCuenta/edit?idUsuario=" + ((Prestador) session.getAttribute("userLogueado")).getIdUsuario();
            }
            model.addAttribute("action", "create");
            return redir;
        }
        catch (Exception e) {
            return "redirect:/inicio";
        }
    }

    @GetMapping("/MiCuenta/edit")
    public String showEditForm(@RequestParam("idUsuario") int idUsuario, Model model, HttpSession session){
        try{
            //String redir = null;
            if(session.getAttribute("userLogueado") instanceof Administrador){
                throw new ObjectNotFoundException("Todavía no está disponible esta función.");
            }
            Usuario usuarioSesion = (Usuario) session.getAttribute("userLogueado");
            int idUsuarioSesion = usuarioSesion.getIdUsuario();
            if (idUsuario != idUsuarioSesion) {
                return "redirect:/usuarios/MiCuenta/edit?idUsuario=" + idUsuarioSesion;
                //redir = "redirect:/usuarios/MiCuenta/edit?idUsuario=" + idUsuarioSesion;
            }
            Usuario usuario = usuarioService.findById(idUsuario)
                                            .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));
            if(usuario != null) {
                setearFormAttributes(usuario, "edit", model);
            }
            return "formUsuarios";
            //return redir;
        }catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeError", e.getMessage());
            return "redirect:/inicio";
        }catch (DataIntegrityViolationException ex) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", "Ya existe un usuario con ese nombre.");
            return "formUsuarios"; // Devuelve al formulario de registro
        }

    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam("idUsuario") int idUsuario, Model model, HttpSession session) {
        try{
            String redir = null;
            if (session.getAttribute("userLogueado") instanceof Administrador) {
                Usuario usuario = usuarioService.findById(idUsuario)
                                                .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));
                if(usuario != null) {
                    setearFormAttributes(usuario, "delete", model);
                    redir = "formUsuarios";
                }
            }
            else {
                throw new ObjectNotFoundException("No tenes permisos para realizar esta acción todavía.");
            }
            return redir;

        }catch (ObjectNotFoundException e) {
                session.setAttribute("Exito", false);
                session.setAttribute("mensajeExito", e.getMessage());
                return "redirect:/inicio";
        }
    }

    @PostMapping("/create")
    public String crearUser(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,
                            @RequestParam String nombreUsuario, @RequestParam String password, @RequestParam(required = false) String tipoUsuario,
                            @RequestParam String telefono, Model model, HttpSession session) {
        try {
            password = passwordEncoder.encode(password);
            noAdminNombre(nombreUsuario);
            if("cliente".equals(tipoUsuario)){
                Cliente usuario = new Cliente(nombreUsuario, nombre, apellido, email, password, telefono, tipoUsuario);
                usuario.setAdministrador(administradorService.getDefaultAdministrador());
                clienteService.registerUser(usuario);
            } else if("prestador".equals(tipoUsuario)){
                Prestador usuario = new Prestador(nombreUsuario, nombre, apellido, email, password, telefono, tipoUsuario);
                usuario.setAdministrador(administradorService.getDefaultAdministrador());
                prestadorService.registerUser(usuario);
            }
            setearSuccessAttributes("Usuario creado con éxito", session);
        }
        catch (DataIntegrityViolationException ex) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje","Ya existe un usuario con ese nombre.");
            return "formUsuarios"; // Devuelve al formulario de registro
        }
        catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/inicio";
    }

    @PostMapping("/edit")
    public String editarUser(@RequestParam("idUsuario") int idUsuario, @RequestParam String nombre, @RequestParam String apellido,
                             @RequestParam String email, @RequestParam String nombreUsuario, @RequestParam String password,
                             @RequestParam String tipoUsuario, @RequestParam String telefono, Model model, HttpSession session) {
        try {
            Usuario usuario = usuarioService.findById(idUsuario)
                    .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));
            noAdminNombre(nombreUsuario);
            if(usuario != null) {
                password = passwordEncoder.encode(password);
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(email);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setPassword(password);
                usuario.setRol(tipoUsuario);
                usuario.setCelular(telefono);
                if ("cliente".equals(tipoUsuario))
                    clienteService.registerUser((Cliente) usuario);
                else if ("prestador".equals(tipoUsuario))
                    prestadorService.registerUser((Prestador) usuario);
                setearSuccessAttributes("Usuario editado con éxito", session);
            }
        }
        catch (DataIntegrityViolationException ex) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje","Ya existe un usuario con ese nombre.");
            return "formUsuarios"; // Devuelve al formulario de registro
        }
        catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/inicio";
    }

    @PostMapping("/delete")
    public String eliminarUser(@RequestParam("idUsuario") int idUsuario, Model model, HttpSession session) {
        try {
            Usuario usuario = usuarioService.findById(idUsuario)
                    .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));
            if(usuario != null) {
                usuarioService.delete(usuario);
                setearSuccessAttributes("Usuario eliminado con éxito", session);
            }
        } catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/inicio";
    }

    private void setearFormAttributes(Usuario usuario, String action, Model model){
        model.addAttribute("elUsuario", usuario);
        model.addAttribute("action", action);
    }

    private void setearSuccessAttributes(String mensaje, HttpSession session){
        session.setAttribute("Exito", true);
        session.setAttribute("mensajeExito", mensaje);
    }

    private void noAdminNombre(String nombreUsuario) {
        if (nombreUsuario.equals("admin") || nombreUsuario.equals("Admin") || nombreUsuario.equals("ADMIN")) {
            throw new DataIntegrityViolationException("No se puede crear un usuario con ese nombre de usuario");
        }
    }
}