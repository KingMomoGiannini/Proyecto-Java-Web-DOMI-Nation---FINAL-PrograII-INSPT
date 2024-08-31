<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/formSede.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Registrar/Editar/Eliminar Usuario</title>
</head>
<body style="background-color: black">
    <c:import url="navbar.jsp"/>
    <div class="elcontainer">
        <div class="container-inicial">
            <h1>Registro/Edición/Eliminación de Usuario</h1>
        </div>

        <br><br><br>
        <c:if test="${not empty error}">
            <div class="mensaje">
                <h1><c:out value="${error}"/></h1>
            </div>
        <br><br>
        </c:if>
        <c:remove var="error"/>
        <form action="${pageContext.request.contextPath}/usuarios/${action}" method="post">
            <input type="hidden" name="idUsuario" value="${elUsuario.getIdUsuario()}">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="Exito" value=false>
            <c:choose>
                <c:when test="${action eq 'delete'}">
                    <div class="inter-texto">
                        <label class="fuenteMin" for="usuario">Usuario a eliminar:</label>
                        <input class="text-box-ajuste" type="text" value="${elUsuario.nombreUsuario}" readonly>
                        <input class="text-box-ajuste" type="text" value="${elUsuario.getIdUsuario()}" readonly>
                        <label class="fuenteMin" for="usuario">Rol:</label>
                        <input class="text-box-ajuste" type="text" value="${elUsuario.rol}" readonly>
                        <br>
                        <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar este usuario?</h3>
                    </div>
                    <br><br>
                    <div class="centrarEnPag">
                        <button class="botoncin" type="submit" name="confirmDelete" value="true">Eliminar</button>
                        <a class="botoncin-cancel" href="/inicio">Cancelar</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <h3 class="fuentePrincFondo">Datos del usuario</h3>
                    <div class="inter-texto">
                        <label class="fuenteMin" for="nomUsuario">Nombre de usuario:</label>
                        <input class="text-box-ajuste" type="text" name="nombreUsuario" id="nombre" value="${elUsuario.nombreUsuario}">
                        <label class="fuenteMin" for="nombre">Nombre:</label>
                        <input class="text-box-ajuste" type="text" name="nombre" id="nombre" value="${elUsuario.nombre}">
                        <label class="fuenteMin" for="apellido">Apellido:</label>
                        <input class="text-box-ajuste" type="text" name="apellido" id="apellido" value="${elUsuario.apellido}">
                        <label class="fuenteMin" for="email">Email:</label>
                        <input class="text-box-ajuste" type="text" name="email" id="email" value="${elUsuario.email}">
                        <label class="fuenteMin" for="celular">Celular:</label>
                        <input class="text-box-ajuste" type="text" name="telefono" id="telefono" value="${elUsuario.celular}">
                        <c:choose>
                            <c:when test="${action eq 'create'}">
                                <label class="fuenteMin" for="rol">Rol:
                                    <select name="tipoUsuario" id="rol">
                                        <option value="cliente" <c:if test="${elUsuario.rol eq 'cliente'}">selected</c:if>>Cliente</option>
                                        <option value="prestador" <c:if test="${elUsuario.rol eq 'prestador'}">selected</c:if>>Prestador</option>
                                    </select>
                                </label>
                                <br>
                                <h3 class="fuentePrincFondo">Contraseña</hbr>
                            </c:when>
                            <c:otherwise>
                                <label class="fuenteMin" for="rol">Rol:</label>
                                <input class="text-box-ajuste" type="text" name="tipoUsuario" id="rol" value="${elUsuario.rol}" readonly>
                                <br>
                                <h3 class="fuentePrincFondo">Cambiar contraseña</hbr>
                            </c:otherwise>
                        </c:choose>
                        <br><br>
                        <label class="fuenteMin" for="password">Contraseña:</label>
                        <input class="text-box-ajuste" type="password" name="password" id="password">
                        <label class="fuenteMin" for="password2">Repetir contraseña:</label>
                        <input class="text-box-ajuste" type="password" name="password2" id="password2">
                    </div>
                    <br><br>
                    <div class="centrarEnPag">
                        <button class="botoncin-submit" type="submit" name="confirm" value="true">Confirmar</button>
                        <a href="/inicio" class="botoncin-cancel">Cancelar</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</body>
</html>
