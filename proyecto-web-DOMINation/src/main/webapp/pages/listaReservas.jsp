<%-- 
    Document   : listaReservas
    Created on : 6 dic 2023, 18:20:02
    Author     : giann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/inicio.css">
        <title>Lista de reservas</title>
    </head>
    <body>
        <c:import url="../navbar.jsp"/>
        <input type="hidden" name="" value="${action}">
        <input type="hidden" name="action" value="${action}">
        <div class="elcontainer">
            <div class="container-inicial">
                <c:choose>
                    <c:when test="${userLogueado.rol eq 'cliente'}">
                        <h1>Mis reservas</h1>
                        <input type="hidden" name="idCliente" value="${userLoguedo.getIdCliente()}">
                    </c:when>
                    <c:otherwise>
                        <h1>Lista de reservas</h1>
                    </c:otherwise>
                </c:choose>
            </div>

                <c:choose>
                    <c:when test = "${not empty lasReservas}">
                        <c:if test = "${Exito==true}">
                            <div class="mensaje">
                                <h1>${mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
                        <div class="seccion">
                            <h1 >Reservas</h1>
                        </div>
                        <div class="sedes-row">
                            <c:forEach items ="${lasReservas}" var="reserva">
                                <div class="sede-container">
                                    <div style="color:white">
                                        <h1 style = "font-size:30px">Reserva</h1>
                                        <h2 style = "color:red;font-size:20px ">ID de Reserva: ${reserva.getIdReserva()}</h2>
                                        <p><strong style = "font-size:14px;text-decoration:underline">ID de Cliente:</strong> ${reserva.getIdCliente()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">ID de Sala:</strong> ${reserva.getIdSala()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Hora de entrada:</strong> ${reserva.getHoraInicio()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Hora de salida:</strong> ${reserva.getHoraFin()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Monto a pagar: $</strong> ${reserva.getMonto()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Duración: </strong> ${reserva.getDuracion()}Hs</p>
                                        <br><br>
                                        <a class="botoncin" href="${pageContext.request.contextPath}/reservas/edit?idReserva=${reserva.getIdReserva()}"><button>Editar Reserva</button></a>
                                    
                                        <br><br>
                                        <a class="botoncin" href="${pageContext.request.contextPath}/reservas/delete?idReserva=${reserva.getIdReserva()}"><button>Eliminar Reserva</button></a>
                                        <br><br>
                                        
                                    </div>
                                </div>
                            </c:forEach>
                            <br><br><br>
                        </div>
                        
                    </c:when>
                    <c:when test= "${empty lasReservas}">
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
                                <a class="botoncin" href="${pageContext.request.contextPath}/inicio"><button>Alquilar sala</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                    </c:when>
                </c:choose>
        </div>
        <c:import url ="../footer.jsp" />
    </body>
</html>
