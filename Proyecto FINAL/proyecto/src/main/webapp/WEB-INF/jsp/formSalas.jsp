<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear/Editar/Eliminar Sala</title>
        <link rel="stylesheet" href="/css/formSede.css"/>
    </head>
    <body style="background-color: black">
        <c:import url="navbar.jsp"/>
        <div class="elcontainer">
            <div class="container-inicial">
                <h1>Creación - Edición - Eliminación de salas</h1>
            </div>
            <br><br><br>
            <c:choose>
                <c:when test="${action eq 'create'}">
                    <form action="${pageContext.request.contextPath}/salas/create" method="post">
                        <input type="hidden" name="sucursal.idSucursal" value="${sucursal.idSucursal}">
                        <label class="fuenteMin" for="numSala">Numero de sala:
                            <input class="text-box-ajuste" type="text" name="numSala" required>
                        </label>
                        <label class="fuenteMin" for="valorHora">Valor por hora:
                            <input class="text-box-ajuste" type="text" name="valorHora" pattern="^\d+(\.\d+)?$" required>
                        </label>
                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin-submit" type="submit">Crear</button>
                            <a class="botoncin-cancel" href="/salas/salasDisponibles?idSucursal=${sucursal.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}">Cancelar</a>
                        </div>
                    </form>
                </c:when>
                <c:when test="${action eq 'update'}">
                    <form action="${pageContext.request.contextPath}/salas/update" method="post">
                        <input type="hidden" name="idSala" value="${sala.idSala}">
                        <input type="hidden" name="idSucursal" value="${sucursal.idSucursal}">
                        <label class="fuenteMin" for="numSala">Numero de sala:
                            <input class="text-box-ajuste" type="text" name="numSala" value="${sala.numSala}" required>
                        </label>
                        <label class="fuenteMin" for="valorHora">Valor por hora:
                            <input class="text-box-ajuste" type="text" name="valorHora" pattern="^\d+(\.\d+)?$" value="${sala.valorHora}" required>
                        </label>
                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit">Editar</button>
                            <a class="botoncin-cancel" href="/salas/salasDisponibles?idSucursal=${sucursal.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}">Cancelar</a>
                        </div>
                    </form>
                </c:when>
                <c:when test="${action eq 'delete'}">
                    <form action="${pageContext.request.contextPath}/salas/delete" method="post">
                        <input type="hidden" name="idSala" value="${sala.idSala}">
                        <input type="hidden" name="idSucursal" value="${sucursal.idSucursal}">
                        <p class="fuenteMin">Numero de sala: ${sala.numSala}</p>
                        <p class="fuenteMin">Nombre de la sede: ${sucursal.nombre}</p>
                        <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar esta sala?</h3>
                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit">Eliminar</button>
                            <a class="botoncin-cancel" href="/salas/salasDisponibles?idSucursal=${sucursal.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}">Cancelar</a>
                        </div>
                    </form>
                </c:when>
            </c:choose>
        </div>
        <c:import url="footer.jsp"/>
    </body>
</html>
