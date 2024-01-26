<%-- 
    Document   : felicitacion
    Created on : 24 sept 2023, 16:48:17
    Author     : giann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario creado</title>
        <link rel="stylesheet" href="css/estiloFelic.css">
    </head>
<body>
    <div class="container-texto">
        <h1 class="fuentePrincFondo">Felicitaciones ${elUsuario.nombre}! </h1>
        <div class="parrafin">
            <p1>Tu cuenta ha sido creada exitosamente! <br> Ya podes ingresar a la web
                con tu usuario y contraseña para consultar el próximo lugar donde
                vas a alquilar tu sala.
            </p1>
        </div>
        <br><br>
        <div class="botoncin">
            
            <a href="${pageContext.request.contextPath}"><button type="submit">Volver al inicio</button></a>&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/ingresar"><button type="submit">ingresar</button></a>
        </div>
    </div><br>
</body>
</html>
