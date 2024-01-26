<%-- 
    Document   : error
    Created on : 13 dic 2023, 15:52:16
    Author     : giann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recurso no Disponible</title>
    </head>
    <body>
        <c:import url="../navbar.jsp"/>
        <div>
            <h1>ERROR</h1>
            <p>${mensajeError}</p>    
        </div>
        <c:import url ="../footer.jsp" />
    </body>
</html>
