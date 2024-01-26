<%-- 
    Document   : formReservas
    Created on : 5 dic 2023, 15:53:20
    Author     : Seba
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="../css/formSede.css"/>
        <title>Reservas</title>
    </head>
    <body style="background-color: black">
        <c:import url="../navbar.jsp"/>
        <div class="elcontainer">
            
            <div class="container-inicial">
                <h1>Formulario de Reserva</h1>
            </div>
                <br><br><br>
                <form action="reservas" method="post">
                    <input type="hidden" name="action" value="${action}">
                    <c:choose>
                        <c:when test="${action eq 'create'}">
                            
                            <input type="hidden" name="idCliente" value="${userLogueado.getIdCliente()}">
                            <input type="hidden" name="idSala" value="${laSala.getIdSala()}">
                            <div class="centrarEnPag">
                                <div class="inter-texto">
                                    <label class="fuenteMin" for="fecha">Fecha:
                                        <input type="date" class="form-control fuente-mas-grande" id="fecha" name="fecha" data-provide="datepicker" required></label>

                                    <label class="fuenteMin" for="horaInicio">Hora de Inicio:
                                        <input type="time" class="form-control fuente-mas-grande" id="horaInicio" name="horaInicio" required></label>

                                    <label class="fuenteMin" for="horaFin">Hora de Fin:
                                    <input type="time" class="form-control fuente-mas-grande" id="horaFin" name="horaFin" required></label>
                                </div>
                            </div>
                            <div class="centrarEnPag"><br><br><br>
                                <button class="botoncin" type="submit" name="confirmDelete" value="true">Reservar</button>
                                <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                            </div>
                        </c:when>
                        <c:when test="${action eq 'delete'}">
                            <input type="hidden" name="idReserva" value="${laReserva.getIdReserva()}">
                            <input type="hidden" name="idSala" value="${laSala.getIdSala()}">
                            <input type="hidden" name="idCliente" value="${elCliente.getIdCliente()}">
                            <div class="inter-texto">
                                <label class="fuenteMin" >Reserva a eliminar:</label>
                                <input class="text-box-ajuste" type="text" value="ID de reserva:  ${laReserva.getIdReserva()}" readonly>
                                <input class="text-box-ajuste" type="text" name="idSala" value="ID de la sala:  ${laSala.getIdSala()}" readonly>
                                <input class="text-box-ajuste" type="text" value="Reserva en:  ${laSede.getNombre()}" readonly>
                                <input class="text-box-ajuste" type="text" value="Fecha :  ${laReserva.getSoloFecha()}" readonly>                                
                                <input class="text-box-ajuste" type="text" value="Desde las :  ${laReserva.getHoraMinutoInicio()} hs" readonly>
                                <input class="text-box-ajuste" type="text" value="Hasta las :  ${laReserva.getHoraMinutoFin()} hs" readonly>
                                <input class="text-box-ajuste" type="text" value="Monto a pagar:  $${laReserva.getMonto()}" readonly>
                                <input class="text-box-ajuste" type="text" value="Usuario que alquila: ${elCliente.getNombre()} ${elCliente.getApellido()}" readonly>

                                <br>
                                <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar esta Reserva?</h3>
                            </div>
                            <br><br>
                            <div class="centrarEnPag">
                                <button class="botoncin" type="submit" name="confirmDelete" value="true">Eliminar</button>
                                <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                            </div>
                        </c:when>
                        <c:when test="${action eq 'update'}">
                            <input type="hidden" name="idReserva" value="${laReserva.getIdReserva()}">
                            <input type="hidden" name="idSala" value="${laSala.getIdSala()}">
                            <input type="hidden" name="idCliente" value="${elCliente.getIdCliente()}">
                            <div class="centrarEnPag">
                                <div class="inter-texto">
                                    <label class="fuenteMin" for="fecha">Fecha:
                                    <input type="date" class="form-control fuente-mas-grande" id="fecha" name="fecha" data-provide="datepicker" value="${laReserva.getSoloFecha()}" required></label>

                                    <label class="fuenteMin" for="horaInicio">Hora de Inicio:
                                    <input type="time" class="form-control fuente-mas-grande" id="horaInicio" name="horaInicio" value="${laReserva.getHoraMinutoInicio()}" required></label>

                                    <label class="fuenteMin" for="horaFin">Hora de Fin:
                                    <input type="time" class="form-control fuente-mas-grande" id="horaFin" name="horaFin" value="${laReserva.getHoraMinutoFin()}" required></label>
                                </div>
                            </div>
                            <div class="centrarEnPag"><br><br><br>
                                <button class="botoncin" type="submit" name="confirmDelete" value="true">Editar</button>
                                <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                            </div>
                        </c:when>
                    </c:choose> 
                </form>
            
           
        </div>
        <c:import url="../footer.jsp"/>
    </body>
</html>
