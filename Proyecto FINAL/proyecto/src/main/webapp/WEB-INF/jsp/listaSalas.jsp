<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/inicio.css">
        <title>Salas disponibles</title>
    </head>
    <body>
        <c:import url="navbar.jsp"/>
        <div class="elcontainer">
            <div class="container-inicial">
                <h1>Lista de salas</h1>
            </div>

                <c:choose>
                    <c:when test = "${not empty salas}">
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
                            <h1 >Sucursales</h1>
                        </div>
                        <div class="sedes-row">
                            <c:forEach items ="${salas}" var="sala">
                                <div class="sede-container">
                                    <div style="color:white">
                                        <h1 style = "font-size:30px">Sucursal</h1>
                                        <h2 style = "color:red;font-size:20px ">${sucursal.nombre}</h2>
                                        <p><strong style = "font-size:14px;text-decoration:underline">ID de Sala:</strong> ${sala.getIdSala()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Numero de Sala:</strong> ${sala.getNumSala()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Valor por hora:</strong> $ ${sala.getValorHora()}</p>
                                        <c:choose>
                                            <c:when test="${userLogueado.rol eq 'cliente'}">
                                                <br><br>
                                                <a href="${pageContext.request.contextPath}/reservas/create?idSala=${sala.getIdSala()}"><button class="botoncin">Alquilar sala</button></a>
                                                <br><br>
                                            </c:when>
                                            <c:otherwise>
                                                <br><br>
                                                <a href="${pageContext.request.contextPath}/salas/delete/${sala.getIdSala()}/${sucursal.getIdSucursal()}"><button class="botoncin">Eliminar sala</button></a>
                                                <a href="${pageContext.request.contextPath}/salas/edit/${sala.getIdSala()}/${sucursal.getIdSucursal()}"><button class="botoncin">Editar sala</button></a>
                                                <br><br>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                            <br><br><br>
                        </div>
                        <c:if test="${userLogueado.rol eq 'prestador'}">
                            <div class="centrarEnPag">
                                <a href="${pageContext.request.contextPath}/salas/create?idSucursal=${sucursal.getIdSucursal()}"><button class="botoncin">Crear sala</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                    </c:when>
                    <c:when test= "${empty salas}">
                        <div class="mensaje">
                            <br><br><br>
                            <h1>No hay salas disponibles</h1>
                        </div>
                        <c:if test="${userLogueado.rol eq 'prestador'}">
                            <div class="seccion centrarEnPag">
                                <br><br><br>
                                <h1 >¿Desea crear una sala??</h1>
                            </div>
                            <br><br><br>
                            <div class="centrarEnPag">
                                <a href="${pageContext.request.contextPath}/salas/create?idSucursal=${sucursal.getIdSucursal()}"><button class="botoncin">Crear sala</button></a>
                                <br><br><br>
                                
                            </div>
                        </c:if>
                    </c:when>
                </c:choose>
        </div>
        <c:import url ="footer.jsp" />
    </body>
</html>