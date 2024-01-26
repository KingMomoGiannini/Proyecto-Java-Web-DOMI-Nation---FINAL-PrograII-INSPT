<%-- 
    Document   : inicio
    Created on : 21 sept 2023, 9:29:58
    Author     : giann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicia Sesión</title>
        <link rel="stylesheet" href="css/estiloLog.css">
    </head>

<body>
    <c:import url ="../navbar.jsp" />
    <br><br><br>
    <c:if test="${hayError == true }">
        <div class="mensajeError">
            
            <h1>${mensajeError}</h1>
            <br><br><br>
        </div>
    </c:if>

    <form action="ingresar" method="post" >
        <div class= "centrarEnPag" >

            <label class="fuenteMin" for="user">Usuario:</label> <input type="text" id="user" name="user">
            <br/><br/>
            <label class="fuenteMin" for="pass">Clave:</label> <input type="password" id="pass" name="pass">
            <br/><br/>
            <button class="botoncin" type="submit" >Enviar</button></a>
        </div>
    </form>
    <br><br><br>
    <c:import url="../footer.jsp"/>
</body>

</html>