<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/inicio.css">
        <title>Error</title>
    </head>
    <body style = "background-color: black">
        <c:import url="navbar.jsp"/>
        <div class="elcontainer">
            <br><br>
            <div class="container-error">
                <img src="${pageContext.request.contextPath}/img/error.jpg" alt="Error">
                <div class="error-message">
                    <h1>SE TE CAYÓ UN ERROR, MASTER. </h1>
                    <h1>LA URL QUE ESTAS INTENTANDO BUSCAR NO EXISTE.</h1>
                </div>
            </div>
            <br><br>
            <div class="centrarEnPag">
                <a href="${pageContext.request.contextPath}/inicio"><button class="boton-estilo">Volver al inicio</button></a>
            </div>
            <br><br>
        </div>
        <c:import url="footer.jsp"/>
    </body>
</html>