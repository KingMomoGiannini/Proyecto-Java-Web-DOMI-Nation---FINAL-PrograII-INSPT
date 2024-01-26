<%-- 
    Document   : formSalas
    Created on : 3 dic 2023, 21:00:45
    Author     : Seba
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar/Eliminar salas</title>
        <link rel="stylesheet" href="../css/formSede.css"/>
    </head>
    <body>
        <c:import url="../navbar.jsp"/>
        <div class="elcontainer">
            <div class="container-inicial">
                <h1>Creación - Edición - Eliminación de salas</h1>
            </div>
            <br><br><br>
            <form action="salas" method="post">
                <input type="hidden" name="idSala" value="${laSala.getIdSala()}">
                <input type="hidden" name="action" value="${action}">
                <input type="hidden" name="Exito" value=false>
                <c:choose>
                    <c:when test="${action eq 'create'}">
                        <%-- acá ponemos todo el form de creación --%>
                        <input class="text-box-ajuste" type="text" value="Nombre de la sede:  ${laSede.nombre}" readonly>
                        <input class="text-box-ajuste" type="text" value="ID de la sede:  ${laSede.getIdSede()}" readonly>
                        <label class="fuenteMin" for="numeroSala">Numero de sala:<input class="text-box-ajuste" type="text" name="numSala" required></label>
                        <label class="fuenteMin" for="numeroSala">Valor por hora:<input class="text-box-ajuste" type="text" name="monto" pattern="^\d+(\.\d+)?$" required></label>

                        <input type="hidden" name="idSede" value="${laSede.getIdSede()}">

                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit" name="confirmDelete" value="true">Crear</button>
                            <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                        </div>

                    </c:when>
                    <c:when test="${action eq 'update'}">
                        <%-- acá ponemos todo el form de edicion --%>
                        <input class="text-box-ajuste" type="text" value="Nombre de la sede:  ${laSede.nombre}" readonly>
                        <input type="hidden" name="idSede" value="${laSede.getIdSede()}">
                        <input class="text-box-ajuste" type="text" value="ID de la sede:  ${laSede.getIdSede()}" readonly>
                        <input type="hidden" name="idSala" value="${laSala.getIdSala()}">
                        <label class="fuenteMin" for="numeroSala">Numero de sala:<input class="text-box-ajuste" type="text" name="numSala" value="${laSala.getNumSala()}" required></label>
                        <label class="fuenteMin" for="numeroSala">Valor por hora:<input class="text-box-ajuste" type="text" name="monto" pattern="^\d+(\.\d+)?$" value="${laSala.getValorHora()}"></label>

                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit" name="confirmDelete" value="true">Editar</button>
                            <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                        </div>
                        
                    </c:when>
                    <c:when test="${action eq 'delete'}">
                        <%-- acá ponemos la confirmación o cancelación de delete --%>
                        <div class="inter-texto">
                            <label class="fuenteMin" for="usuario">Sala a eliminar:</label>
                            <input class="text-box-ajuste" type="text" value="Numero de sala:  ${laSala.getNumSala()}" readonly>
                            <input class="text-box-ajuste" type="text" name="idSala" value="ID de la sala:  ${laSala.getIdSala()}" readonly>
                            <input class="text-box-ajuste" type="text" value="Nombre de la sede:  ${laSede.nombre}" readonly>
                            <input class="text-box-ajuste" type="text" value="ID de la sede:  ${laSede.getIdSede()}" readonly>

                            <br>
                            <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar esta sala?</h3>
                        </div>
                            <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit" name="confirmDelete" value="true">Eliminar</button>
                            <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                        </div>
                        </div>
                    </c:when>
                </c:choose>
            </form>
        </div>
    <c:import url ="../footer.jsp" />
    </body>
</html>
