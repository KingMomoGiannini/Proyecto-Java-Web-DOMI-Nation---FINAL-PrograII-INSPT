package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.models.Reserva;
import com.domination.proyecto.models.Sala;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.services.DomicilioService;
import com.domination.proyecto.services.ReservaService;
import com.domination.proyecto.services.SalaService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.UsuarioService;
import com.domination.proyecto.services.PrestadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sedes")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("laSede", new Sucursal());
        return "formSedes";
    }

    @GetMapping("/update/{idSucursal}")
    public String showEditForm(@PathVariable int idSucursal, Model model) {
        Sucursal laSede = sucursalService.findByIdSucursal(idSucursal)
                                         .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        Domicilio elDom = laSede.getDomicilio();
        Prestador elPrestador = laSede.getPrestador();
        model.addAttribute("elPrestador", elPrestador);
        model.addAttribute("laSede", laSede);
        model.addAttribute("elDom", elDom);
        model.addAttribute("action", "update");
        return "formSedes";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") int idSucursal, Model model) {
        Sucursal laSede = sucursalService.findByIdSucursal(idSucursal)
                                         .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
        Domicilio elDom = laSede.getDomicilio();
        Prestador elPrestador = laSede.getPrestador();
        model.addAttribute("elPrestador",elPrestador);
        model.addAttribute("laSede", laSede);
        model.addAttribute("elDom", elDom);
        model.addAttribute("action", "delete");
        return "formSedes";
    }

    @PostMapping("/create")
    public String createSede(@RequestParam String nomSede,
                             @RequestParam int salas,
                             @RequestParam String celular,
                             @RequestParam String horaInicio,
                             @RequestParam String horaCierre,
                             @RequestParam int idPrestador,
                             @RequestParam String provincia,
                             @RequestParam String localidad,
                             @RequestParam String partido,
                             @RequestParam String calle,
                             @RequestParam String altura,
                             HttpSession session) {
        try {
            System.out.println("el id del prestador es " + idPrestador);
            Prestador elPrestador = prestadorService.findByIdPrestador(idPrestador)
                                                    .orElseThrow(() -> new ObjectNotFoundException("Prestador no encontrado con id: " + idPrestador));
            System.out.println("el nombre del prestador es: " + elPrestador.getNombre());
            Sucursal laSede = obtenerSucursalDesdeRequest(nomSede, salas, celular, horaInicio, horaCierre, elPrestador);
            sucursalService.save(laSede);
            Domicilio elDom = obtenerDomicilioDesdeRequest(provincia, localidad, partido, calle, altura, laSede);
            domicilioService.save(elDom);
            setAttributesForSuccess(session, "La sede ha sido creada exitosamente", laSede, elDom);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al crear la sede.");
        }
        return "redirect:/inicio";
    }

    @PostMapping("/update")
    public String updateSede(@RequestParam int idSede,
                             @RequestParam int idDom,
                             @RequestParam String nomSede,
                             @RequestParam int salas,
                             @RequestParam String celular,
                             @RequestParam String horaInicio,
                             @RequestParam String horaCierre,
                             @RequestParam int idPrestador,
                             @RequestParam String provincia,
                             @RequestParam String localidad,
                             @RequestParam String partido,
                             @RequestParam String calle,
                             @RequestParam String altura,
                             HttpSession session) {
        try {
            Prestador elPrestador = prestadorService.findByIdPrestador(idPrestador)
                                                    .orElseThrow(() -> new ObjectNotFoundException("Prestador no encontrado con id: " + idPrestador));
            Sucursal laSede = obtenerSucursalDesdeRequest(nomSede, salas, celular, horaInicio, horaCierre, elPrestador);
            laSede.setIdSucursal(idSede);
            sucursalService.save(laSede);
            Domicilio elDom = obtenerDomicilioDesdeRequest(provincia, localidad, partido, calle, altura, laSede);
            elDom.setIdDomicilio(idDom);
            domicilioService.save(elDom);
            setAttributesForSuccess(session, "La sede ha sido actualizada exitosamente", laSede, elDom);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al actualizar la sede.");
        }
        return "redirect:/inicio";
    }

    @PostMapping("/delete")
    public String deleteSede(@RequestParam int idSede,
                             HttpSession session) {
        try {
            Sucursal laSucu = sucursalService.findByIdSucursal(idSede)
                                             .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSede));
            for (Sala sala : laSucu.getSalas()) {
                for (Reserva reserva : reservaService.getReservasBySala(sala.getIdSala())) {
                    reservaService.deleteReserva(reserva);
                }
                salaService.deleteById(sala.getIdSala());
            }
            domicilioService.deleteById(laSucu.getDomicilio().getIdDomicilio());
            sucursalService.deleteById(idSede);
            setAttributesForSuccess(session, "La sede ha sido eliminada exitosamente", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al eliminar la sede.");
        }
        return "redirect:/inicio";
    }

    private Sucursal obtenerSucursalDesdeRequest(String nomSede, int salas, String celular, String horaInicio, String horaCierre, Prestador prestador) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaInicioParsed = LocalTime.parse(horaInicio, formatter);
        LocalTime horaCierreParsed = LocalTime.parse(horaCierre, formatter);
        return new Sucursal(nomSede, salas, horaInicioParsed, horaCierreParsed, celular, prestador);
    }

    private Domicilio obtenerDomicilioDesdeRequest(String provincia, String localidad, String partido, String calle, String altura, Sucursal sucursal) {
        return new Domicilio(calle, altura, localidad, partido, provincia, sucursal);
    }

    private void setAttributesForSuccess(HttpSession session, String mensaje, Sucursal laSede, Domicilio elDom) {
        session.setAttribute("Exito", true);
        session.setAttribute("mensajeExito", mensaje);
        session.setAttribute("sede", laSede);
        session.setAttribute("domicilio", elDom);
        session.setAttribute("sedesDelUsuario", sucursalService.findAll());
        session.setAttribute("domiciliosDeSedes", domicilioService.findAll());
        session.setAttribute("lasReservas", reservaService.findAll());
    }
}
