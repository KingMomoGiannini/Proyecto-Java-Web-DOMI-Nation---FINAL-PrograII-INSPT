package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Sala;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Reserva;
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

    @GetMapping("/create")
    public String showCreateForm(@RequestParam("idSucursal") int idSucursal, Model model) {
        Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                                           .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("sala", new Sala());
        model.addAttribute("action", "create");
        return "formSalas";
    }

    @GetMapping("/salasDisponibles/{idSucursal}")
    public String listSalasDisponibles(@PathVariable("idSucursal") int idSucursal, Model model) {
        Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                                           .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        List<Sala> salas = salaService.findSalasBySucursal(sucursal);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("salas", salas);
        return "listaSalas";
    }

    @GetMapping("/edit/{idSala}/{idSucursal}")
    public String showEditForm(@PathVariable("idSala") int idSala, @PathVariable("idSucursal") int idSucursal, Model model) {
        Sala sala = salaService.findById(idSala)
                               .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada con id: " + idSala));
        Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                                           .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        model.addAttribute("sala", sala);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("action", "update");
        return "formSalas";
    }

    @GetMapping("/delete/{idSala}/{idSucursal}")
    public String showDeleteForm(@PathVariable("idSala") int idSala, @PathVariable("idSucursal") int idSucursal, Model model) {
        Sala sala = salaService.findById(idSala)
                               .orElseThrow(() -> new ObjectNotFoundException("Sala no encontrada con id: " + idSala));
        Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                                           .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        model.addAttribute("sala", sala);
        model.addAttribute("sucursal", sucursal);
        model.addAttribute("action", "delete");
        return "formSalas";
    }

    @PostMapping("/create")
    public String createSala(@ModelAttribute Sala sala, Model model, HttpSession session) {
        try {
            salaService.save(sala);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La Sala ha sido creada exitosamente");
            return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
        } catch (Exception e) {
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "Error al crear la Sala");
            return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
        }
    }

    @PostMapping("/update")
    public String updateSala(@RequestParam("idSucursal") int idSucursal,@ModelAttribute Sala sala, Model model, HttpSession session) {
        try {
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            sala.setSucursal(sucursal);
            salaService.save(sala);
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "La Sala ha sido actualizada exitosamente");
            return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
        } catch (Exception e) {
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "Error al actualizar la Sala");
            return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
        }
    }

    @PostMapping("/delete")
    public String deleteSala(@RequestParam("idSala") int idSala, Model model, HttpSession session) {
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
            return "redirect:/salas/salasDisponibles/" + sala.getSucursal().getIdSucursal();
        } catch (Exception e) {
            session.setAttribute("Exito", true);
            session.setAttribute("mensaje", "Error al eliminar la Sala");
            return "redirect:/salas/salasDisponibles/" + idSala;
        }
    }
}
