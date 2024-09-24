package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.*;
import com.domination.proyecto.services.PrestadorService;
import com.domination.proyecto.services.SalaService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PrestadorService prestadorService;

    @GetMapping("/create")
    public String showCreateForm(@RequestParam("idSucursal") int idSucursal, @RequestParam("idPrestador") int idPrestador, Model model, HttpSession session ) {
        try {
            Prestador elUser = (Prestador)session.getAttribute("userLogueado");
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada" ));
            if (idPrestador != elUser.getIdPrestador()){
                return "redirect:/salas/salasDisponibles?idSucursal=" + idSucursal+"&idPrestador="+elUser.getIdPrestador();
            }
            validarSalaSucuPrest(elUser, sucursal);
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("sala", new Sala());
            model.addAttribute("action", "create");
            return "formSalas";
        } catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @GetMapping("/salasDisponibles")
    public String listSalasDisponibles(@RequestParam("idSucursal") int idSucursal, @RequestParam("idPrestador") int idPrestador, Model model, HttpSession session) {
        try{
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada" ));
            if (session.getAttribute("userLogueado") instanceof Administrador){
                Administrador elUser = (Administrador) session.getAttribute("userLogueado");
                validarSalaSucuPrest(sucursal.getPrestador(), sucursal);
            }
            else if (session.getAttribute("userLogueado") instanceof Usuario){
                Usuario elUser = (Usuario) session.getAttribute("userLogueado");
                if (elUser.getRol().equals("prestador")){
                    Prestador prestador = prestadorService.findByIdUsuario(elUser.getIdUsuario());
                    if (idPrestador != prestador.getIdPrestador()){
                        return "redirect:/salas/salasDisponibles?idSucursal=" + idSucursal+"&idPrestador="+prestador.getIdPrestador();
                    }
                    validarSalaSucuPrest(prestador, sucursal);
                }
                else if ((elUser.getRol().equals("cliente")) && sucursal.getPrestador().getIdPrestador() != idPrestador){
                    throw new ObjectNotFoundException("Sucursal no encontrada");
                }
            }
            List<Sala> salas = salaService.findSalasBySucursal(sucursal);
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("salas", salas);
            return "listaSalas";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio" ;
        }
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("idSala") int idSala, @RequestParam("idSucursal") int idSucursal, Model model, HttpSession session) {
        try {
            Prestador elUser = (Prestador)session.getAttribute("userLogueado");
            Sala sala = salaService.findById(idSala)
                    .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada"));
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            validarSalaSucuPrest(elUser, sucursal, sala);
            model.addAttribute("sala", sala);
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("action", "update");
            return "formSalas";
        }catch (ObjectNotFoundException e){
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam("idSala") int idSala, @RequestParam("idSucursal") int idSucursal, Model model, HttpSession session) {
        try{

            Sala sala = salaService.findById(idSala)
                                   .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada con id: " + idSala));
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                                               .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            model.addAttribute("sala", sala);
            model.addAttribute("sucursal", sucursal);
            model.addAttribute("action", "delete");
            return "formSalas";
        }catch (ObjectNotFoundException e){
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @PostMapping("/create")
    public String createSala(@RequestParam("idSucursal") int idSucursal, @RequestParam("idPrestador") int idPrestador, @ModelAttribute Sala sala, Model model, HttpSession session) {
        try {
            /*Prestador elPrestador = (Prestador)session.getAttribute("userLogueado");
            if (elPrestador.getIdPrestador() != idPrestador){
                idPrestador = elPrestador.getIdPrestador();
            }*/
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            sala.setSucursal(sucursal);
            repeticionNumSalas(sucursal, sala);
            salaService.save(sala);
            if (sala == null) {
                throw new ObjectNotFoundException("Error al crear la Sala");
            }
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La Sala ha sido creada exitosamente");
            return "redirect:/salas/salasDisponibles?idSucursal=" + idSucursal+"&idPrestador="+idPrestador;
        } catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", e.getMessage());
            return "redirect:/salas/salasDisponibles?idSucursal=" + idSucursal+"&idPrestador="+idPrestador;
        }
    }

    @PostMapping("/update")
    public String updateSala(@RequestParam("idSucursal") int idSucursal,@ModelAttribute Sala sala, Model model, HttpSession session) {
        try {
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            sala.setSucursal(sucursal);
            repeticionNumSalas(sucursal, sala);
            salaService.save(sala);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La Sala ha sido actualizada exitosamente");
            return "redirect:/salas/salasDisponibles?idSucursal=" + sala.getSucursal().getIdSucursal()+"&idPrestador="+sala.getSucursal().getPrestador().getIdPrestador();
        } catch (Exception e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", "Error al actualizar la Sala");
            return "redirect:/salas/salasDisponibles?idSucursal=" + sala.getSucursal().getIdSucursal()+"&idPrestador="+sala.getSucursal().getPrestador().getIdPrestador();
        }
    }

    @PostMapping("/delete")
    public String deleteSala(@RequestParam("idSala") int idSala, @RequestParam("idSucursal") int idSucursal, Model model, HttpSession session) {
        try {
            Sala sala = salaService.findById(idSala)
                                   .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada con id: " + idSala));
            /* Versión del algoritmo que verifica si la sala tiene reservas asociadas antes de eliminarla

            List<Reserva> reservas = reservaService.findReservasBySala(sala);
            if (reservas.isEmpty()) {
                salaService.deleteById(idSala);
                model.addAttribute("mensaje", "La Sala ha sido eliminada exitosamente");
                return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
            } else {
                model.addAttribute("mensaje", "No se puede eliminar la Sala porque tiene reservas asociadas");
                return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
            }*/
            salaService.deleteById(idSala);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La sala ha sido eliminada exitosamente");
            return "redirect:/salas/salasDisponibles?idSucursal=" + sala.getSucursal().getIdSucursal()+"&idPrestador="+sala.getSucursal().getPrestador().getIdPrestador();
        } catch (ObjectNotFoundException e) {
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada"));
            session.setAttribute("Exito", false);
            session.setAttribute("mensaje", "Error al eliminar la Sala");
            return "redirect:/salas/salasDisponibles?idSucursal=" + idSucursal+"&idPrestador="+sucursal.getPrestador().getIdPrestador();
        }

    }
    //metodo para validar si la sucursal le pertenece al prestador en sesión
    private void validarSalaSucuPrest(Prestador prestador, Sucursal sucursal){
        if (sucursal.getPrestador().getIdPrestador() != prestador.getIdPrestador()){
            throw new ObjectNotFoundException("Sucursal no encontrada");
        }
    }

    private void validarSalaSucuPrest(Prestador prestador, Sucursal sucursal, Sala sala){
        if (sucursal.getPrestador().getIdPrestador() != prestador.getIdPrestador()){
            throw new ObjectNotFoundException("Sucursal no encontrada");
        }
        if (sala.getSucursal().getIdSucursal() != sucursal.getIdSucursal()){
            throw new ObjectNotFoundException("Sala no encontrada");
        }

    }

    private void repeticionNumSalas(Sucursal sucursal, Sala sala){
        List<Sala> salas = sucursal.getSalas();
        if (salas.size() >= sucursal.getCantSalas()){
            throw new ObjectNotFoundException("No se pueden crear más salas en esta sucursal");
        }
        else if (salas.isEmpty()){
            if (sala.getNumSala() <= 0  || sala.getNumSala() > sucursal.getCantSalas() || sala.getNumSala() > 10){
                throw new ObjectNotFoundException("Por favor, Coloque un número de sala válido");
            }
        }
        //si se repite el numero de sala en la misma sucursal tirar una excepcion
        for (Sala s : salas) {
            if (sala.getNumSala() <= 0  || sala.getNumSala() > sucursal.getCantSalas() || sala.getNumSala() > 10){
                throw new ObjectNotFoundException("Por favor, Coloque un número de sala válido");
            }
            else if (s.getNumSala() == sala.getNumSala()) {
                throw new ObjectNotFoundException("Ya existe una sala con ese número en esta sucursal");
            }
        }

    }

}
