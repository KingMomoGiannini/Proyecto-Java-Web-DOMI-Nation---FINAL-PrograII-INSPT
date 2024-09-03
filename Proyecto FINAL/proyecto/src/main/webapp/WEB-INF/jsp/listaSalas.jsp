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
                        <br><br>
                        <c:choose>
                            <c:when test="${Exito == true}">
                                <div class="mensaje-success">
                                    <h1>${mensaje}</h1>
                                </div>
                                <br><br>
                            </c:when>
                            <c:when test="${Exito == false}">
                                <div class="mensaje">
                                    <h1>${mensaje}</h1>
                                </div>
                                <br><br>
                            </c:when>
                        </c:choose>
                        <c:remove var="mensaje" scope="session"/>
                        <c:remove var="Exito" scope="session"/>
                        <div class="seccion">
                            <h1 >Salas de Ensayo</h1>
                        </div>
                        <div class="sedes-row">
                            <c:forEach items ="${salas}" var="sala">
                                <div class="sede-container">
                                    <div style="color:white">
                                        <h1 class="new-amsterdam-font">Sucursal</h1>
                                        <h2 class="new-amsterdam-font-roja">${sucursal.nombre}</h2>
                                        <p class="new-amsterdam-font-pmax">ID de Sala:</strong> ${sala.getIdSala()}</p>
                                        <p class="new-amsterdam-font-pmax">Numero de Sala:</strong> ${sala.getNumSala()}</p>
                                        <p class="new-amsterdam-font-pmax">Valor por hora:</strong> $ ${sala.getValorHora()}</p>
                                        <c:choose>
                                            <c:when test="${userLogueado.rol eq 'cliente'}">
                                                <br><br>
                                                <a href="${pageContext.request.contextPath}/reservas/create?idSala=${sala.getIdSala()}"><button class="boton-estilo">Alquilar sala</button></a>
                                                <br><br>
                                            </c:when>
                                            <c:otherwise>
                                                <br><br>
                                                <a href="${pageContext.request.contextPath}/salas/delete?idSala=${sala.getIdSala()}&idSucursal=${sucursal.getIdSucursal()}"><button class="boton-estilo">Eliminar sala</button></a>
                                                <a href="${pageContext.request.contextPath}/salas/edit?idSala=${sala.getIdSala()}&idSucursal=${sucursal.getIdSucursal()}"><button class="boton-estilo">Editar sala</button></a>
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
                                <a href="${pageContext.request.contextPath}/salas/create?idSucursal=${sucursal.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}"><button class="boton-estilo">Crear sala</button></a>
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
                                <a href="${pageContext.request.contextPath}/salas/create?idSucursal=${sucursal.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}"><button class="boton-estilo">Crear sala</button></a>
                                <br><br><br>
                                
                            </div>
                        </c:if>
                    </c:when>
                </c:choose>
        </div>
        <c:import url ="footer.jsp" />
    </body>
</html>