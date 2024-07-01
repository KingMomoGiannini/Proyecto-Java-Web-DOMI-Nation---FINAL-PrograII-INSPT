<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/css/formSede.css">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registrarse</title>
    </head>

    <body>   
        <c:import url="navbar.jsp" />
        <div class="elcontainer">
            <div class="form-container"> 
            
            <form action="registrarse" method="POST" >
                <h2 class="fuentePrinc">Registrate completando los datos solicitados</h2>
                <h3 class="fuentePrincFondo">Datos Personales</h3>
                <div class="inter-texto">
                    <label class="fuenteMin" for="name">Nombre:</label> <input class="text-box-ajuste" type="text" name="nomCliente" id="nombre" required>
                    <label class="fuenteMin" for="last-name">Apellido:</label> <input class="text-box-ajuste" type="text" id="apellido" name="apeCliente" required>
                    <label class="fuenteMin" for="celular">Celular:</label> <input class="text-box-ajuste" type="text" id="cel" name="celular">
                </div>
                <h3 class="fuentePrincFondo">Otros datos</h3>
                <div class="inter-texto">
                    <label class="fuenteMin" for="email">Correo Electrónico</label> <input class="text-box-ajuste" type="text" id="email" name="email" required>        
                    <label class="fuenteMin" for="user">Nombre de usuario:</label> <input class="text-box-ajuste" type="text" id="user" name="user" required>
                    <br>
                    
                    <label class="fuenteMin" for="pass">Contraseña:</label> <input class="text-box-ajuste" type="password" id="pass" name="pass" required>
                    <br><br>
                    <label class="fuenteMin" for="tipoUsuario">Tipo de usuario:<input type="checkbox" id="tipoUsuario" name="tipoUsuario" value="prestador"> Prestador</label>
                </div>
                
                <br><br>
            
                <div class="centrarEnPag">
                    <button class ="botoncin" type="submit" >Enviar</button>
                    <button class ="botoncin" type="reset" >Limpiar Formulario</button>
                </div>  <br>
                </form> 
            </div>
        </div> 
        <c:import url="footer.jsp" />
    </body>
</html>