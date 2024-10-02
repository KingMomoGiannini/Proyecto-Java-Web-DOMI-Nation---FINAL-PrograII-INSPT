<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario creado</title>
        <link rel="stylesheet" href="/css/navbar.css">
        <link rel="stylesheet" href="/css/estiloFelic.css">
    </head>
<body class="clase-maestra">
    <c:import url="navbar.jsp"/>
    <br><br>
    <div class="container-texto">
        <h1 class="fuentePrincFondo">Felicitaciones ${elUsuario.nombre}! </h1>
        <div class="parrafin">
            <p1>Tu cuenta ha sido creada exitosamente! <br> Ya podes ingresar a la web
                con tu usuario y contraseña para consultar el próximo lugar donde
                vas a alquilar tu sala.
            </p1>
        </div>
        <br><br>
        <div>
            <a href="${pageContext.request.contextPath}/"><button class="botoncin" type="submit">Volver al inicio</button></a>&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/ingresar"><button class="botoncin" type="submit">ingresar</button></a>
        </div>
    </div><br>
    <c:import url="footer.jsp"/>
</body>
</html>
