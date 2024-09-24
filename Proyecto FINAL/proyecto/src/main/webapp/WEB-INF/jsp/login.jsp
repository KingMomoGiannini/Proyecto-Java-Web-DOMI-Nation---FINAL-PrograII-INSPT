<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicia Sesión</title>
        <link rel="stylesheet" href="/css/formSede.css">
    </head>

<body style="background-color: black" >
    <c:import url ="navbar.jsp" />
    <br><br>
    <c:if test="${sessionScope.hayError == true }">
            <div class="mensaje">
                <h1>${sessionScope.mensajeError}</h1>
            <br><br>
        </div>
    </c:if>
    <c:remove var="mensajeError" scope="session"/>
    <c:remove var="hayError" scope="session"/>
    <div class="elcontainer">
        <br>
        <form action= "<c:url value='/ingresar'/>"  method="post">
         <div class="centrarEnPag">
                <div class="inter-texto">
                    <label class="fuenteMin" for="username">Username:
                    <input type="text" class="form-control fuente-mas-grande" id="username" name="username" required>
                    </label><br>
                    <label class="fuenteMin" for="password">Password:
                    <input type="password" class="form-control fuente-mas-grande" id="password" name="password" required>
                    </label>
                </div>
            </div>
            <div class="centrarEnPag"><br><br><br>
                <button class="botoncin" type="submit" >Enviar</button></a>
            </div>
        </form>
    </div>

    <br><br><br>
    <c:import url="footer.jsp"/>
</body>

</html>