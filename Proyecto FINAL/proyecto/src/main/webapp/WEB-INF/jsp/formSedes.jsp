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
    <title>Registrar/Editar/Eliminar Sede</title>
    <link rel="stylesheet" href="/css/formSede.css"/>
</head>
<body style="background-color: black">
    <c:import url="navbar.jsp"/>
    <div class="elcontainer">
        <div class="container-inicial">
            <h1>Registro/Edición/Eliminación de Sede</h1>
        </div>
        <br><br><br>
        <c:if test="${not empty mensaje}">
            <div class="mensaje">
                <h1><c:out value="${mensaje}"/></h1>
            </div>
            <br><br>
        </c:if>
        <c:remove var="mensaje"/>
        <form action="${pageContext.request.contextPath}/sedes/${action}" method="post">
            <c:choose>
                <c:when test="${userLogueado.rol eq 'prestador'}">
                    <input type="hidden" name="idPrestador" value="${userLogueado.getIdPrestador()}">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="idPrestador" value="${elPrestador.getIdPrestador()}">
                </c:otherwise>
            </c:choose>
            <input type="hidden" name="idSede" value="${laSede.getIdSucursal()}">
            <input type="hidden" name="idDom" value="${elDom.getIdDomicilio()}">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="Exito" value=false>
            <c:choose>
                <c:when test="${action eq 'delete'}">
                    <div class="inter-texto">
                        <label class="fuenteMin" for="sede">Sede a eliminar:</label>
                        <input class="text-box-ajuste" type="text" value="${laSede.nombre}" readonly>
                        <input class="text-box-ajuste" type="text" value="${laSede.getIdSucursal()}" readonly>
                        <label class="fuenteMin" for="sede">Usuario al que le pertenece:</label>
                        <input class="text-box-ajuste" type="text" value="id de Prestador: ${elPrestador.getIdPrestador()}" readonly>
                        <input class="text-box-ajuste" type="text" value="id de Usuario: ${elPrestador.getIdUsuario()}" readonly>
                        <input class="text-box-ajuste" type="text" value="Nombre y apellido: ${elPrestador.getNombre()} ${elPrestador.getApellido()}" readonly>
                        <br>
                        <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar esta sede?</h3>
                    </div>
                    <br><br>
                    <div class="centrarEnPag">
                        <button class="botoncin" type="submit" name="confirmDelete" value="true">Eliminar</button>
                        <a class="botoncin-cancel" href="/inicio">Cancelar</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <h3 class="fuentePrincFondo">Datos de la sede</h3>
                    <div class="inter-texto">
                        <label class="fuenteMin" for="nomSede">Nombre del local:</label>
                        <input class="text-box-ajuste" type="text" name="nomSede" id="nombre" value="${laSede.nombre}">
                        <label class="fuenteMin" for="cantSalas">Cantidad de salas:</label>
                        <select name="salas" id="salas">
                            <c:forEach var="i" begin="1" end="10">
                                <option value="${i}" <c:if test="${i eq laSede.cantSalas}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                        <label class="fuenteMin" for="celular">Telefono/Celular:</label>
                        <input class="text-box-ajuste" type="text" id="cel" name="celular" value="${laSede.telefono}">
                        <label class="fuenteMin" for="horaInicio">Apertura del local:</label>
                        <input type="time" class="form-control fuente-mas-grande" id="horaInicio" name="horaInicio" value="${laSede.getHoraInicio()}" required>
                        <label class="fuenteMin" for="horaCierre">Cierre del local:</label>
                        <input type="time" class="form-control fuente-mas-grande" id="horaCierre" name="horaCierre" value="${laSede.getHoraFin()}" required>
                    </div>
                    <br>
                    <h3 class="fuentePrincFondo">Domicilio de la misma</h3>
                    <div class="inter-texto">
                        <label class="fuenteMin" for="calle">Calle:</label>
                        <input class="text-box-ajuste" type="text" id="calle" name="calle" value="${elDom.calle}">
                        <label class="fuenteMin" for="altura">Altura:</label>
                        <input class="text-box-ajuste" type="text" id="altura" name="altura" value="${elDom.altura}">
                        <label class="fuenteMin" for="localidad">Localidad:</label>
                        <input class="text-box-ajuste" type="text" id="localidad" name="localidad" value="${elDom.localidad}">
                        <label class="fuenteMin" for="partido">Partido:</label>
                        <input class="text-box-ajuste" type="text" id="partido" name="partido" value="${elDom.partido}">
                        <label class="fuenteMin" for="provincia">Provincia:</label>
                        <input class="text-box-ajuste" type="text" id="provincia" name="provincia" value="${elDom.provincia}">
                    </div>
                    <br><br>
                    <div class="centrarEnPag">
                        <button class="botoncin-submit" type="submit">Enviar</button>
                        <button class="botoncin" type="reset">Limpiar Formulario</button>
                        <a class="botoncin-cancel" href="/inicio">Cancelar</a>
                    </div>
                    <br>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
    <c:import url="footer.jsp"/>
</body>
</html>

