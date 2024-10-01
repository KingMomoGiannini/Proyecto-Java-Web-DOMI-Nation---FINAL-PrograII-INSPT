<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/inicio.css">
        <title>inicio</title>
    </head>

<body style="background-color: black">
    <c:import url ="navbar.jsp" />
    <div class="elcontainer">
        <div class="container-inicial">
            <h1>Bienvenido/a ${userLogueado.nombreUsuario}</h1>
        </div>
        <div>
            <c:choose>
                <c:when test="${userLogueado.getRol() == 'administrador'}">
                    <br><br>
                    <c:choose>
                        <c:when test="${sessionScope.Exito == true}">
                            <div class="mensaje-success">
                                <h1>${sessionScope.mensajeExito}</h1>
                            </div>
                            <br><br>
                        </c:when>
                        <c:when test="${sessionScope.Exito == false}">
                            <div class="mensaje">
                                <h1>${sessionScope.mensajeExito}</h1>
                            </div>
                            <br><br>
                        </c:when>
                    </c:choose>
                    <c:remove var="mensajeExito" scope="session"/>
                    <c:remove var="Exito" scope="session"/>
                    <c:if test="${not empty lasReservas}">
                        <div class="centrarEnPag">
                            <a href="/reservas/admin/listaReservas?idAdministrador=${userLogueado.getIdAdministrador()}"><button class="boton-estilo">Ver Reservas</button></a>
                            <br><br><br>
                        </div>
                    </c:if>
                    <c:if test = "${not empty sedesDelUsuario}">
                        <div class="seccion">
                            <h1 >Sucursales</h1>
                        </div>
                        <div class="sedes-row">
                            <c:forEach items ="${sedesDelUsuario}" var="sede">
                                        <div class="sede-container">
                                            <div style="color:white">
                                                <h1 class="new-amsterdam-font">Sucursal</h1>
                                                <h2 class="new-amsterdam-font-roja">${sede.nombre}</h2>
                                                <p class = "new-amsterdam-font-pmax">ID de Sede: ${sede.getIdSucursal()}</p>
                                                <p class = "new-amsterdam-font-pmax">Cantidad de Salas: ${sede.cantSalas}</p>
                                                <p class = "new-amsterdam-font-pmax">Hora de Inicio: ${sede.horaInicio} hs</p>
                                                <p class = "new-amsterdam-font-pmax">Hora de Fin: ${sede.horaFin} hs</p>
                                                <p class = "new-amsterdam-font-pmax">Teléfono: ${sede.telefono}</p>
                                                <c:set var="elDomPag" value="${null}" />
                                                <c:if test = "${not empty domiciliosDeSedes}">
                                                    <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                        <c:choose>
                                                            <c:when test= "${dom.getSucursal().getIdSucursal() == sede.getIdSucursal()}">
                                                                <div style="color:white">
                                                                    <h1 class="new-amsterdam-font">Dirección</h1>
                                                                    <p class = "new-amsterdam-font-pmax">Provincia: ${dom.provincia}</p>
                                                                    <p class = "new-amsterdam-font-pmax">Localidad: ${dom.localidad}</p>
                                                                    <p class = "new-amsterdam-font-pmax">Partido: ${dom.partido}</p>
                                                                    <p class = "new-amsterdam-font-pmax">Calle:${dom.calle}</p>
                                                                    <p class = "new-amsterdam-font-pmax">Altura: ${dom.altura}</p>
                                                                    <br><br><br>
                                                                    <c:set var="elDomPag" value="${dom}" />
                                                                </div>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:forEach> 
                                                </c:if>
                                                <a href="sedes/delete?idSucursal=${sede.getIdSucursal()}"><button class="boton-estilo">Eliminar Sede</button></a>
                                                <a href="sedes/update?idSucursal=${sede.getIdSucursal()}"><button class="boton-estilo">Editar Sede</button></a>
                                                <a href="salas/salasDisponibles?idSucursal=${sede.getIdSucursal()}&idPrestador=${sede.getPrestador().getIdPrestador()}"><button class="boton-estilo">Ver Salas</button></a>
                                            </div>
                                        </div>

                                    <c:if test = "${domiciliosDeSedes == null}">
                                        <p>No hay domicilios disponibles registrados </p>
                                    </c:if>
                            </c:forEach> 
                            <br><br><br>
                        </div>
                    </c:if>
                    <c:if test = "${not empty usuarios}">
                        <div class="seccion">
                            <h1 >Usuarios</h1>
                        </div>
                        
                        <div class="sedes-row">
                            <c:set var="elUserPag" value="${null}" />
                            <c:forEach items ="${usuarios}" var="user" >

                                    <div class="sede-container">
                                        <div style="color:white">
                                            <h1 class="new-amsterdam-font">Datos del usuario</h1>
                                            <p class = "new-amsterdam-font-pmax">ID de usuario: ${user.getIdUsuario()}</p>
                                            <c:choose>
                                                <c:when test="${user.getRol() == 'prestador'}">
                                                    <p class = "new-amsterdam-font-pmax">ID de prestador: ${user.getIdPrestador()}</p>
                                                </c:when>
                                                <c:when test="${user.getRol() == 'cliente'}">
                                                    <p class = "new-amsterdam-font-pmax">ID de cliente: ${user.getIdCliente()}</p>
                                                </c:when>
                                            </c:choose>
                                            <p class = "new-amsterdam-font-pmax">Nombre de usuario: ${user.getNombreUsuario()}</p>
                                            <p class = "new-amsterdam-font-pmax">Nombre: ${user.getNombre()}</p>
                                            <p class = "new-amsterdam-font-pmax">Apellido: ${user.getApellido()} </p>
                                            <p class = "new-amsterdam-font-pmax">Email: ${user.getEmail()}</p>
                                            <p class = "new-amsterdam-font-pmax">Rol: ${user.getRol()}</p>
                                            
                                            <br><br>

                                            <a href="usuarios/delete?idUsuario=${user.getIdUsuario()}"><button class="boton-estilo">Eliminar Usuario</button></a>
                                        </div>
                                    </div>

                            </c:forEach>
                            <br><br>
                        </div>
                        <div class="centrarEnPag">
                            <a href="usuarios/create"><button class="boton-estilo">Crear Usuario</button></a>
                        </div>
                    </c:if>
                    
                </c:when>
                <c:when test="${userLogueado.getRol() == 'prestador'}">
                    <br><br>
                    <div>
                        <c:choose>
                            <c:when test="${sessionScope.Exito == true}">
                                <div class="mensaje-success">
                                    <h1>${sessionScope.mensajeExito}</h1>
                                </div>
                                <br><br>
                            </c:when>
                            <c:when test="${sessionScope.Exito == false}">
                                <div class="mensaje">
                                    <h1>${sessionScope.mensajeExito}</h1>
                                </div>
                                <br><br>
                            </c:when>
                        </c:choose>
                        <c:remove var="mensajeExito" scope="session"/>
                        <c:remove var="Exito" scope="session"/>
                        <c:if test="${not empty lasReservas}">
                            <div class="centrarEnPag">
                                <a href="reservas/listaReservas?idPrestador=${userLogueado.getIdPrestador()}"><button class="boton-estilo">Ver Reservas</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test = "${not empty sedesDelUsuario}">
                            <div class="sedes-row">
                                <c:forEach items ="${sedesDelUsuario}" var="sede">
                                    <c:choose>
                                        <c:when test= "${sede.getPrestador() == userLogueado}">
                                            <div class="sede-container">
                                                <div style="color:white">
                                                    <h1 class="new-amsterdam-font">Sucursal</h1>
                                                    <h2 class="new-amsterdam-font-roja">${sede.nombre}</h2>
                                                    <p class = "new-amsterdam-font-pmax">Cantidad de Salas: ${sede.cantSalas}</p>
                                                    <p class = "new-amsterdam-font-pmax">Hora de Inicio: ${sede.horaInicio} hs</p>
                                                    <p class = "new-amsterdam-font-pmax">Hora de Fin: ${sede.horaFin} hs</p>
                                                    <p class = "new-amsterdam-font-pmax">Teléfono: ${sede.telefono}</p>
                                                    <c:set var="elDomPag" value="${null}" />
                                                    <c:if test = "${not empty domiciliosDeSedes}">
                                                        <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                            <c:choose>
                                                                <c:when test= "${dom.getSucursal().getIdSucursal() == sede.getIdSucursal()}">
                                                                    <div style="color:white">
                                                                        <h1 class="new-amsterdam-font">Dirección</h1>
                                                                        <p class = "new-amsterdam-font-pmax">Provincia: ${dom.provincia}</p>
                                                                        <p class = "new-amsterdam-font-pmax">Localidad: ${dom.localidad}</p>
                                                                        <p class = "new-amsterdam-font-pmax">Partido: ${dom.partido}</p>
                                                                        <p class = "new-amsterdam-font-pmax">Calle: ${dom.calle}</p>
                                                                        <p class = "new-amsterdam-font-pmax">Altura: ${dom.altura}</p>
                                                                        <br><br><br>
                                                                        <c:set var="elDomPag" value="${dom}" />
                                                                    </div>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach> 
                                                    </c:if>
                                                    <a href="salas/salasDisponibles?idSucursal=${sede.getIdSucursal()}&idPrestador=${userLogueado.getIdPrestador()}"><button class="boton-estilo">Ver Salas</button></a>
                                                    <a href="sedes/update?idSucursal=${sede.getIdSucursal()}"><button class="boton-estilo">Editar Sede</button></a>
                                                    <a href="sedes/delete?idSucursal=${sede.getIdSucursal()}"><button class="boton-estilo">Eliminar Sede</button></a>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                        <c:if test = "${empty domiciliosDeSedes}">
                                            <p>No hay domicilios disponibles registrados </p>
                                        </c:if>
                                </c:forEach> 
                                <br><br><br>
                            </div>
                        </c:if>
                        <div class="centrarEnPag">
                            <a class="centrarEnPag" href="sedes/create"><button class="boton-estilo">Crear Sede</button></a>
                        </div>      
                    </div>
                    <br><br><br>
                </c:when> <%-- --%>   
                <c:when test="${userLogueado.getRol() == 'cliente'}">
                    <br><br>
                    <c:choose>
                        <c:when test="${sessionScope.Exito == true}">
                            <div class="mensaje-success">
                                <h1>${sessionScope.mensajeExito}</h1>
                            </div>
                            <br><br>
                        </c:when>
                        <c:when test="${sessionScope.Exito == false}">
                            <div class="mensaje">
                                <h1>${sessionScope.mensajeExito}</h1>
                            </div>
                            <br><br>
                        </c:when>
                    </c:choose>

                        <c:remove var="mensajeExito" scope="session"/>
                        <c:remove var="Exito" scope="session"/>
                        <c:if test="${not empty lasReservas}">
                            <div class="centrarEnPag">
                                <a href="reservas/misReservas?idCliente=${userLogueado.getIdCliente()}"><button class="boton-estilo">Ver mis Reservas</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test = "${not empty sedesDelUsuario}">
                            <div class="sedes-row">
                                <c:forEach items ="${sedesDelUsuario}" var="sede">
                                    <div class="sede-container">
                                        <div>
                                            <h1 class="new-amsterdam-font">Sucursal</h1>
                                            <h2 class="new-amsterdam-font-roja">${sede.nombre}</h2>
                                            <p class="new-amsterdam-font-pmax">Cantidad de Salas: ${sede.cantSalas}</p>
                                            <p class="new-amsterdam-font-pmax">Hora de Inicio: ${sede.horaInicio} hs</p>
                                            <p class="new-amsterdam-font-pmax">Hora de Fin: ${sede.horaFin} hs</p>
                                            <p class="new-amsterdam-font-pmax">Teléfono: ${sede.telefono}</p>

                                            <c:if test = "${not empty domiciliosDeSedes}">
                                                <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                    <c:choose>
                                                        <c:when test= "${dom.getSucursal().getIdSucursal() == sede.getIdSucursal()}">
                                                            <div>
                                                                <h1 class="new-amsterdam-font">Dirección</h1>
                                                                <p class="new-amsterdam-font-pmax">Provincia: ${dom.provincia}</p>
                                                                <p class="new-amsterdam-font-pmax">Localidad: ${dom.localidad}</p>
                                                                <p class="new-amsterdam-font-pmax">Partido: ${dom.partido}</p>
                                                                <p class="new-amsterdam-font-pmax">Calle: ${dom.calle}</p>
                                                                <p class="new-amsterdam-font-pmax">Altura: ${dom.altura}</p>
                                                                <br><br><br>
                                                            </div>

                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach> 
                                            </c:if>
                                            <a href="salas/salasDisponibles?idSucursal=${sede.getIdSucursal()}&idPrestador=${sede.getPrestador().getIdPrestador()}"><button class="boton-estilo">Ver Salas</button></a>
                                        </div>
                                    </div>
                                    <c:if test = "${empty domiciliosDeSedes}">
                                        <p>No hay domicilios disponibles registrados </p>
                                    </c:if>
                                </c:forEach> 
                                <br><br><br>
                            </div>
                        </c:if>
                    
                </c:when> <%-- --%>   
            </c:choose>
        </div>
    </div>
    <c:import url ="footer.jsp" />
</body>
</html>
