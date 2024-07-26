<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicia Sesión</title>
        <link rel="stylesheet" href="/css/estiloLog.css">
    </head>

<body>
    <c:import url ="navbar.jsp" />
    <br><br><br>
    <c:if test="${hayError == true }">
        <div class="mensajeError">
            
            <h1>${mensajeError}</h1>
            <br><br><br>
        </div>
    </c:if>

    <form action="<c:url value='/ingresar'/>" method="post" >
        <div class= "centrarEnPag" >

            <label class="fuenteMin" for="username">Usuario:</label> <input type="text" id="username" name="username">
            <br/><br/>
            <label class="fuenteMin" for="password">Clave:</label> <input type="password" id="password" name="password">
            <br/><br/>
            <button class="botoncin" type="submit" >Enviar</button></a>
        </div>
    </form>
    <br><br><br>
    <c:import url="footer.jsp"/>
</body>

</html>