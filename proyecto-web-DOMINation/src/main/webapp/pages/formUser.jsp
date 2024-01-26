<%-- 
    Document   : formUsuario
    Created on : 1 dic 2023, 22:34:46
    Author     : Seba
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar/Eliminar cuentas</title>
        <link rel="stylesheet" href="../css/formSede.css"/>
    </head>
    <body>
        <c:import url="../navbar.jsp"/>
        <div class="elcontainer">
            <div class="container-inicial">
                <h1>Edición/Eliminación de Usuario</h1>
            </div>
            <br><br><br><!-- comment -->
            <form action="usuarios" method="post">
                <input type="hidden" name="idUser" value="${elUser.getIdUsuario()}">
                <input type="hidden" name="action" value="${action}">
                <input type="hidden" name="Exito" value=false>
                <c:choose>
                    <c:when test="${action eq 'delete'}">
                        <div class="inter-texto">
                            <label class="fuenteMin" for="usuario">Usuario a eliminar:</label>
                            <input class="text-box-ajuste" type="text" value="Nombre de Usuario:  ${elUser.getNomUsuario()}" readonly>
                            <input class="text-box-ajuste" type="text" value="Nombre:  ${elUser.nombre}" readonly>
                            <input class="text-box-ajuste" type="text" value="apellido:  ${elUser.apellido}" readonly>
                            <input class="text-box-ajuste" type="text" value="Id del usuario:  ${elUser.getIdUsuario()}" readonly>
                            <c:if test ="${elUser.rol eq 'cliente'}">
                                <input class="text-box-ajuste" type="text" value="Tipo de usuario:  ${elUser.getRol()}" readonly>
                                <input class="text-box-ajuste" type="text" value="ID de Cliente:  ${elUser.getIdCliente()}" readonly>
                                <input type="hidden" name="idUser" value="${elUser.getIdUsuario()}">
                                <input type="hidden" name="idCliente" value="${elUser.getIdCliente()}">
                                <input type="hidden" name="rolUser" value="${elUser.rol}">
                            </c:if>
                            <c:if test ="${elUser.rol eq 'prestador'}">
                                <input class="text-box-ajuste" type="text" value="Tipo de usuario:  ${elUser.getRol()}" readonly>
                                <input class="text-box-ajuste" type="text" value="ID de Prestador:  ${elUser.getIdPrestador()}" readonly>
                                <input type="hidden" name="idUser" value="${elUser.getIdUsuario()}">
                                <input type="hidden" name="idPrestador" value="${elUser.getIdPrestador()}">
                                <input type="hidden" name="rolUser" value="${elUser.rol}">
                            </c:if>
                            <br>
                            <h3 class="fuentePrincFondo">¿Está seguro que desea eliminar este usuario?</h3>
                        </div>
                        <br><br>
                        <div class="centrarEnPag">
                            <button class="botoncin" type="submit" name="confirmDelete" value="true">Eliminar</button>
                            <button class="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h3 class="fuentePrincFondo">Datos del Usuario</h3>
                        <div class="inter-texto">
                            <label class="fuenteMin" for="name">Nombre:</label>
                            <input class="text-box-ajuste" type="text" name="nombre" id="nombre"><br>

                            <label class="fuenteMin" for="last-name">Apellido:</label>
                            <input class="text-box-ajuste" type="text" id="apellido" name="apellido"><br>

                            <label class="fuenteMin" for="celular">Celular:</label>
                            <input class="text-box-ajuste" type="text" id="cel" name="celular">
                        </div>
                        <br>
                        <h3 class="fuentePrincFondo">Otros datos</h3>
                        <div class="inter-texto">
                            <label class="fuenteMin" for="email">Correo Electrónico:</label>
                            <input class="text-box-ajuste" type="text" id="email" name="email"><br>        

                            <label class="fuenteMin" for="user">Nombre de usuario:</label>
                            <input class="text-box-ajuste" type="text" id="user" name="user">

                            <br>

                            <label class="fuenteMin" for="pass">Cambiar Contraseña:</label>
                            <input class="text-box-ajuste" type="password" id="pass" name="pass"><br>

                            <label class="fuenteMin" for="pass-confirm">Confirme contraseña:</label>
                            <input class="text-box-ajuste" type="password" id="pass-confirm" name="pass-confirm"><br>
                            
                            <input type="hidden" name="rol" value="${elUser.getRol()}">
                            <br><br>

                            
                        </div>
                        <br><br>
                        <div class="centrarEnPag">
                            <button class ="botoncin" type="submit">Enviar</button>
                            <button class ="botoncin" type="reset" >Limpiar Formulario</button>
                            <button class ="botoncin" type="submit" name="cancelDelete" value="true">Cancelar</button>
                        </div>  
                        <br>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
        <c:import url="../footer.jsp"/>
    </body>
</html>

