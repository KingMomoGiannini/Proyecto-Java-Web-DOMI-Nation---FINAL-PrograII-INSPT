<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/css/formSede.css"/>
        <title>Reservas</title>
    </head>
    <body style="background-color: black">
        <c:import url="navbar.jsp"/>
        <div class="elcontainer">
            
            <div class="container-inicial">
                <h1>Formulario de Reserva</h1>
            </div>
            <c:choose>
                <c:when test="${Exito eq 'true'}">
                    <div class="mensaje-success">
                        <h1><c:out value="${mensaje}"/></h1>
                    </div>
                </c:when>
                <c:when test="${Exito eq 'false'}">
                    <div class="mensaje">
                        <h1><c:out value="${mensaje}"/></h1>
                    </div>
                </c:when>
            </c:choose>
            <div class="mensaje">
                <br><br><br>
                <c:if test="${sessionScope.Exito == true}">
                    <div class="mensaje">
                        <h1>${sessionScope.mensaje}</h1>
                    </div>
                </c:if>
                <br>
                <c:if test="${error != null}">
                    <div class="mensaje">
                        <h1>${sessionScope.error}</h1>
                    </div>
                </c:if>
                <c:remove var="mensaje" scope="session"/>
                <c:remove var="Exito" scope="session"/>
                <c:remove var="error" scope="session"/>
                <br><br>
            </div>
                <form action= "${pageContext.request.contextPath}/reservas/${action}" method="post">
                    <input type="hidden" name="action" value="${action}">
                    <c:choose>
                        <c:when test="${action eq 'create'}">
                            
                            <input type="hidden" name="idCliente" value="${userLogueado.getIdCliente()}">
                            <input type="hidden" name="idSala" value="${sala.getIdSala()}">
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
                                <button class="botoncin-submit" type="submit" name="confirmCreate" value="true">Reservar</button>
                                <a class="botoncin-cancel" href="/inicio">Cancelar</a>                            </div>
                        </c:when>
                        <c:when test="${action eq 'delete'}">
                            <input type="hidden" name="idReserva" value="${reserva.getIdReserva()}">
                            <input type="hidden" name="idSala" value="${sala.getIdSala()}">
                            <input type="hidden" name="idCliente" value="${cliente.getIdCliente()}">
                            <div class="inter-texto">
                                <label class="fuenteMin" >Reserva a eliminar:</label>
                                <input class="text-box-ajuste" type="text" value="ID de reserva:  ${reserva.getIdReserva()}" readonly>
                                <input class="text-box-ajuste" type="text" name="idSala" value="ID de la sala:  ${sala.getIdSala()}" readonly>
                                <input class="text-box-ajuste" type="text" value="Reserva en:  ${sala.getSucursal().nombre}" readonly>
                                <input class="text-box-ajuste" type="text" value="Fecha :  ${reserva.getSoloFecha()}" readonly>                                
                                <input class="text-box-ajuste" type="text" value="Desde las :  ${reserva.getHoraMinutoInicio()} hs" readonly>
                                <input class="text-box-ajuste" type="text" value="Hasta las :  ${reserva.getHoraMinutoFin()} hs" readonly>
                                <input class="text-box-ajuste" type="text" value="Monto a pagar:  $${reserva.getMonto()}" readonly>
                                <input class="text-box-ajuste" type="text" value="Usuario que alquila: ${cliente.getNombre()} ${cliente.getApellido()}" readonly>

                                <br>
                                <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar esta Reserva?</h3>
                            </div>
                            <br><br>
                            <div class="centrarEnPag">
                                <button class="botoncin-submit" type="submit" name="confirmDelete" value="true">Eliminar</button>
                                <a class="botoncin-cancel" href="/inicio">Cancelar</a>
                            </div>
                        </c:when>
                        <c:when test="${action eq 'update'}">
                            <input type="hidden" name="idReserva" value="${reserva.getIdReserva()}">
                            <input type="hidden" name="idSala" value="${sala.getIdSala()}">
                            <input type="hidden" name="idCliente" value="${cliente.getIdCliente()}">
                            <div class="centrarEnPag">
                                <div class="inter-texto">
                                    <label class="fuenteMin" for="fecha">Fecha:
                                    <input type="date" class="form-control fuente-mas-grande" id="fecha" name="fecha" data-provide="datepicker" value="${reserva.getSoloFecha()}" required></label>

                                    <label class="fuenteMin" for="horaInicio">Hora de Inicio:
                                    <input type="time" class="form-control fuente-mas-grande" id="horaInicio" name="horaInicio" value="${reserva.getHoraMinutoInicio()}" required></label>

                                    <label class="fuenteMin" for="horaFin">Hora de Fin:
                                    <input type="time" class="form-control fuente-mas-grande" id="horaFin" name="horaFin" value="${reserva.getHoraMinutoFin()}" required></label>
                                </div>
                            </div>
                            <div class="centrarEnPag"><br><br><br>
                                <button class="botoncin-submit" type="submit" name="confirmDelete" value="true">Editar</button>
                                <a class="botoncin-cancel" href="/inicio">Cancelar</a>
                            </div>
                        </c:when>
                    </c:choose> 
                </form>
            
           
        </div>
        <c:import url="footer.jsp"/>
    </body>
</html>
