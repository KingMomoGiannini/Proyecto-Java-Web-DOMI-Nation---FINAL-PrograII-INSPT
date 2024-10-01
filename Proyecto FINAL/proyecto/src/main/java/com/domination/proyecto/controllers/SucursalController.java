package com.domination.proyecto.controllers;

import com.domination.proyecto.exceptions.ObjectNotFoundException;
import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Domicilio;
import com.domination.proyecto.models.Sucursal;
import com.domination.proyecto.models.Prestador;
import com.domination.proyecto.services.DomicilioService;
import com.domination.proyecto.services.ReservaService;
import com.domination.proyecto.services.SucursalService;
import com.domination.proyecto.services.PrestadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    private PrestadorService prestadorService;

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("laSede", new Sucursal());
        return "formSedes";
    }

    @GetMapping("/update")
    public String showEditForm(@RequestParam("idSucursal") int idSucursal, Model model, HttpSession session){
        try {
            Sucursal sucursal = sucursalService.findByIdSucursal(idSucursal)
                    .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            if (session.getAttribute("userLogueado") instanceof Administrador) {
                Prestador prestador = sucursal.getPrestador();
                validarSucuPrest(prestador, sucursal);
            }
            else if (session.getAttribute("userLogueado") instanceof Prestador) {
                Prestador prestador = (Prestador) session.getAttribute("userLogueado");
                validarSucuPrest(prestador, sucursal);
            }
            establecerAtributos(model, sucursal.getPrestador(), sucursal, sucursal.getDomicilio(), "update");
            return "formSedes";
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito",false);
            session.setAttribute("mensajeExito",e.getMessage());
            return "redirect:/inicio";
        }
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam("idSucursal") int idSucursal, Model model, HttpSession session) {
        try{
            //Prestador prestador = (Prestador) session.getAttribute("userLogueado");
            Sucursal laSede = sucursalService.findByIdSucursal(idSucursal)
                                             .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSucursal));
            if (session.getAttribute("userLogueado") instanceof Administrador) {
                Prestador prestador = laSede.getPrestador();
                validarSucuPrest(prestador, laSede);
            }
            else if (session.getAttribute("userLogueado") instanceof Prestador) {
                Prestador prestador = (Prestador) session.getAttribute("userLogueado");
                validarSucuPrest(prestador, laSede);
            }
            establecerAtributos(model, laSede.getPrestador(), laSede, laSede.getDomicilio(), "delete");
        }
        catch (ObjectNotFoundException e) {
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito",e.getMessage());
            return "redirect:/inicio";
        }
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
            Prestador elPrestador = prestadorService.findByIdPrestador(idPrestador)
                                                    .orElseThrow(() -> new ObjectNotFoundException("Prestador no encontrado con id: " + idPrestador));
            Sucursal laSede = obtenerSucursalDesdeRequest(nomSede, salas, celular, horaInicio, horaCierre, elPrestador);
            sucursalService.save(laSede);
            Domicilio elDom = obtenerDomicilioDesdeRequest(provincia, localidad, partido, calle, altura, laSede);
            domicilioService.save(elDom);
            session.setAttribute("Exito", true);
            session.setAttribute("mensajeExito", "La sucursal ha sido creada exitosamente");
            setAttributesForSuccess(session, laSede, elDom);
            return "redirect:/inicio";
        }catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al crear la sucursal");
            return "redirect:/inicio";
        }
        catch (DataIntegrityViolationException e) { //cuando se lance una excepcion SQL por tener un valor duplicado (como el nombre de la sucursal)
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al crear la sucursal. El nombre de la sucursal ya existe.");
            return "redirect:/inicio";
        }

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
            session.setAttribute("Exito", true);
            session.setAttribute("mensajeExito", "La sucursal ha sido actualizada exitosamente");
            setAttributesForSuccess(session, laSede, elDom);
            return "redirect:/inicio";
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al actualizar la sucursal");
            return "redirect:/inicio";
        }
        catch (DataIntegrityViolationException e) { //cuando se lance una excepcion SQL por tener un valor duplicado (como el nombre de la sucursal)
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al actualizar la sucursal. El nombre de la sucursal ya existe.");
            return "redirect:/inicio";
        }
    }

    @PostMapping("/delete")
    public String deleteSede(@RequestParam int idSede,
                             HttpSession session) {
        try {
            Sucursal laSucu = sucursalService.findByIdSucursal(idSede)
                                             .orElseThrow(() -> new ObjectNotFoundException("Sucursal no encontrada con id: " + idSede));
            /*for (Sala sala : laSucu.getSalas()) {
                for (Reserva reserva : reservaService.getReservasBySala(sala.getIdSala())) {
                    reservaService.deleteReserva(reserva);
                }
                salaService.deleteById(sala.getIdSala());
            }*/
            domicilioService.deleteById(laSucu.getDomicilio().getIdDomicilio());
            sucursalService.deleteById(idSede);
            session.setAttribute("Exito", true);
            session.setAttribute("mensajeExito", "La sucursal ha sido eliminada exitosamente");
            setAttributesForSuccess(session, null, null);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("Exito", false);
            session.setAttribute("mensajeExito", "Error al eliminar la sucursal.");
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

    private void setAttributesForSuccess(HttpSession session, Sucursal laSede, Domicilio elDom) {

        session.setAttribute("sede", laSede);
        session.setAttribute("domicilio", elDom);
        session.setAttribute("sedesDelUsuario", sucursalService.findAll());
        session.setAttribute("domiciliosDeSedes", domicilioService.findAll());
        session.setAttribute("lasReservas", reservaService.findAll());

    }
    private void validarSucuPrest(Prestador prestador, Sucursal sucursal){
        if (sucursal.getPrestador().getIdPrestador() != prestador.getIdPrestador()){
            throw new ObjectNotFoundException("Sucursal no encontrada");
        }
    }

    private void establecerAtributos(Model model, Prestador elPrestador, Sucursal laSede, Domicilio elDom, String action){
        model.addAttribute("elPrestador",elPrestador);
        model.addAttribute("laSede", laSede);
        model.addAttribute("elDom", elDom);
        model.addAttribute("action", action);
    }
}
