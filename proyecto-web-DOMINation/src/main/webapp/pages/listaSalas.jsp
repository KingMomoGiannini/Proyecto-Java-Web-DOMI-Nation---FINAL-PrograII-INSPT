<%-- 
    Document   : listaSalas
    Created on : 4 dic 2023, 12:56:15
    Author     : Seba
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/inicio.css">

        <title>Salas disponibles</title>
    </head>
    <body>
        <c:import url="../navbar.jsp"/>
        <div class="elcontainer">
            <div class="container-inicial">
                <h1>Lista de salas</h1>
            </div>

                <c:choose>
                    <c:when test = "${not empty lasSalas}">
                        <c:if test = "${Exito==true}">
                            <div class="mensaje">
                                <h1>${mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
                        <div class="seccion">
                            <h1 >Sucursales</h1>
                        </div>
                        <div class="sedes-row">
                            <c:forEach items ="${lasSalas}" var="sala">
                                <div class="sede-container">
                                    <div style="color:white">
                                        <h1 style = "font-size:30px">Sucursal</h1>
                                        <h2 style = "color:red;font-size:20px ">${laSede.nombre}</h2>
                                        <p><strong style = "font-size:14px;text-decoration:underline">ID de Sala:</strong> ${sala.getIdSala()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Numero de Sala:</strong> ${sala.getNumSala()}</p>
                                        <p><strong style = "font-size:14px;text-decoration:underline">Valor por hora:</strong> $ ${sala.getValorHora()}</p>
                                        <c:choose>
                                            <c:when test="${userLogueado.rol eq 'cliente'}">
                                                <br><br>
                                                <a class="botoncin" href="${pageContext.request.contextPath}/reservas/create?idSala=${sala.getIdSala()}"><button>Alquilar sala</button></a>
                                                <br><br>
                                            </c:when>
                                            <c:otherwise>
                                                <br><br>
                                                <a class="botoncin" href="${pageContext.request.contextPath}/salas/delete?idSala=${sala.getIdSala()}&idSede=${laSede.getIdSede()}"><button>Eliminar sala</button></a>
                                                <a class="botoncin" href="${pageContext.request.contextPath}/salas/edit?idSala=${sala.getIdSala()}&idSede=${laSede.getIdSede()}"><button>Editar sala</button></a>
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
                                <a class="botoncin" href="${pageContext.request.contextPath}/salas/create?idSede=${laSede.getIdSede()}"><button>Crear sala</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                    </c:when>
                    <c:when test= "${empty lasSalas}">
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
                                <a class="botoncin" href="${pageContext.request.contextPath}/salas/create?idSede=${laSede.getIdSede()}"><button>Crear sala</button></a>
                                <br><br><br>
                                
                            </div>
                        </c:if>
                    </c:when>
                </c:choose>
        </div>
        <c:import url ="../footer.jsp" />
    </body>
</html>
