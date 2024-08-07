<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/inicio.css">
        <title>Lista de reservas</title>
    </head>
    <body>
        <c:import url="navbar.jsp"/>
        <input type="hidden" name="" value="${action}">
        <input type="hidden" name="action" value="${action}">
        <div class="elcontainer">
            <div class="container-inicial">
                <c:choose>
                    <c:when test="${userLogueado.rol eq 'cliente'}">
                        <h1>Mis reservas</h1>
                        <input type="hidden" name="idCliente" value="${userLogueado.getIdCliente()}">
                    </c:when>
                    <c:when test="${userLogueado.rol eq 'prestador'}">
                        <h1>Lista de reservas</h1>
                        <input type="hidden" name="idPrestador" value="${userLogueado.getIdPrestador()}">
                    </c:when>
                    <c:when test="${userLogueado.rol eq 'administrador'}">
                        <h1>Lista de reservas</h1>
                        <input type="hidden" name="idAdministrador" value="${userLogueado.getIdAdministrador()}">
                    </c:when>
                </c:choose>
            </div>
            <c:choose>
                <c:when test = "${not empty reservas}">
                    <c:if test = "${Exito==true}">
                        <br><br>
                        <div class="mensaje">
                            <h1>${mensaje}</h1>
                            <br><br><br>
                        </div>
                    </c:if>
                    <c:remove var="mensaje" scope="session"/>
                    <c:remove var="Exito" scope="session"/>
                    <div class="seccion">
                        <h1 >Reservas</h1>
                    </div>
                    <div class="sedes-row">
                        <c:forEach items ="${reservas}" var="reserva">
                            <div class="sede-container">
                                <div style="color:white">
                                    <h1 style = "font-size:30px">Reserva</h1>
                                    <h2 style = "color:red;font-size:20px ">ID de Reserva: ${reserva.getIdReserva()}</h2>
                                    <p><strong style = "font-size:14px;text-decoration:underline">ID de Cliente:</strong> ${reserva.getCliente().getIdCliente()}</p>
                                    <p><strong style = "font-size:14px;text-decoration:underline">ID de Sala:</strong> ${reserva.getSala().getIdSala()}</p>
                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de entrada:</strong> ${reserva.getHoraInicio()}</p>
                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de salida:</strong> ${reserva.getHoraFin()}</p>
                                    <p><strong style = "font-size:14px;text-decoration:underline">Monto a pagar: $</strong> ${reserva.getMonto()}</p>
                                    <p><strong style = "font-size:14px;text-decoration:underline">Duración: </strong> ${reserva.getDuracion()}Hs</p>
                                    <br><br>
                                    <a href="${pageContext.request.contextPath}/reservas/edit?idReserva=${reserva.getIdReserva()}"><button class="botoncin">Editar Reserva</button></a>

                                    <br><br>
                                    <a href="${pageContext.request.contextPath}/reservas/delete?idReserva=${reserva.getIdReserva()}"><button class="botoncin">Eliminar Reserva</button></a>
                                    <br><br>

                                </div>
                            </div>
                        </c:forEach>
                        <br><br><br>
                    </div>
                </c:when>
                <c:when test= "${empty reservas}">
                    <div class="mensaje">
                        <br><br><br>
                        <h1>No hay reservas disponibles</h1>
                    </div>
                    <c:if test="${userLogueado.rol eq 'cliente'}">
                        <div class="seccion centrarEnPag">
                            <br><br><br>
                            <h1 >¿Desea realizar una Reserva??</h1>
                        </div>
                        <br><br><br>
                        <div class="centrarEnPag">
                            <a href="${pageContext.request.contextPath}/inicio"><button class="botoncin">Alquilar sala</button></a>
                            <br><br><br>
                        </div>
                    </c:if>
                </c:when>
            </c:choose>
        </div>
        <c:import url ="footer.jsp" />
    </body>
</html>
