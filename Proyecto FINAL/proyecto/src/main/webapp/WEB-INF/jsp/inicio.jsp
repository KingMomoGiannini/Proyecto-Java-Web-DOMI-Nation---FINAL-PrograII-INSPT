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
                    <br><br><br>
                    <c:if test = "${sessionScope.Exito == true}">
                        <br><br>
                        <div class="mensaje">
                            <h1>${sessionScope.mensajeExito}</h1>
                            <br><br><br>
                        </div>
                    </c:if>
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
                                                <h1 style = "font-size:30px">Sucursal</h1>
                                                <h2 style = "color:red;font-size:20px ">${sede.nombre}</h2>
                                                <p><strong style = "font-size:14px;text-decoration:underline">ID de Sede:</strong> ${sede.getIdSucursal()}</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Cantidad de Salas:</strong> ${sede.cantSalas}</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Hora de Inicio:</strong> ${sede.horaInicio} hs</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Hora de Fin:</strong> ${sede.horaFin} hs</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Teléfono:</strong> ${sede.telefono}</p>
                                                <c:set var="elDomPag" value="${null}" />
                                                <c:if test = "${not empty domiciliosDeSedes}">
                                                    <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                        <c:choose>
                                                            <c:when test= "${dom.getSucursal().getIdSucursal() == sede.getIdSucursal()}">
                                                                <div style="color:white">
                                                                    <h1 style = "font-size:30px">Dirección</h1>
                                                                    <p><strong style = "font-size:14px;text-decoration:underline">Provincia:</strong> ${dom.provincia}</p>
                                                                    <p><strong style = "font-size:14px;text-decoration:underline">Localidad:</strong> ${dom.localidad}</p>
                                                                    <p><strong style = "font-size:14px;text-decoration:underline">Partido:</strong> ${dom.partido}</p>
                                                                    <p><strong style = "font-size:14px;text-decoration:underline">Calle:</strong> ${dom.calle}</p>
                                                                    <p><strong style = "font-size:14px;text-decoration:underline">Altura:</strong> ${dom.altura}</p>
                                                                    <br><br><br>
                                                                    <c:set var="elDomPag" value="${dom}" />
                                                                </div>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:forEach> 
                                                </c:if>
                                                <a href="sedes/delete/${sede.getIdSucursal()}"><button class="boton-estilo">Eliminar Sede</button></a>
                                                <a href="salas/salasDisponibles/${sede.getIdSucursal()}"><button class="boton-estilo">Ver Salas</button></a>
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
                                            <h1 style = "font-size:30px">Datos del usuario</h1>
                                            <p><strong style = "font-size:14px;text-decoration:underline">ID de usuario:</strong> ${user.getIdUsuario()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Nombre de usuario:</strong> ${user.getNombreUsuario()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Nombre:</strong> ${user.getNombre()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Apellido:</strong> ${user.getApellido()} </p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Email:</strong> ${user.getEmail()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Rol:</strong> ${user.getRol()}</p>
                                            
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
                    <br><br><br>
                    <div>
                        <c:if test = "${sessionScope.Exito == true}">
                            <br><br>
                            <div class="mensaje">
                                <h1>${sessionScope.mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
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
                                                    <h1 style = "font-size:30px">Sucursal</h1>
                                                    <h2 style = "color:red;font-size:20px ">${sede.nombre}</h2>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">ID de Sede:</strong> ${sede.getIdSucursal()}</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Cantidad de Salas:</strong> ${sede.cantSalas}</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de Inicio:</strong> ${sede.horaInicio} hs</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de Fin:</strong> ${sede.horaFin} hs</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Teléfono:</strong> ${sede.telefono}</p>
                                                    <c:set var="elDomPag" value="${null}" />
                                                    <c:if test = "${not empty domiciliosDeSedes}">
                                                        <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                            <c:choose>
                                                                <c:when test= "${dom.getSucursal().getIdSucursal() == sede.getIdSucursal()}">
                                                                    <div style="color:white">
                                                                        <h1 style = "font-size:30px">Dirección</h1>
                                                                        <p><strong style = "font-size:14px;text-decoration:underline">Provincia:</strong> ${dom.provincia}</p>
                                                                        <p><strong style = "font-size:14px;text-decoration:underline">Localidad:</strong> ${dom.localidad}</p>
                                                                        <p><strong style = "font-size:14px;text-decoration:underline">Partido:</strong> ${dom.partido}</p>
                                                                        <p><strong style = "font-size:14px;text-decoration:underline">Calle:</strong> ${dom.calle}</p>
                                                                        <p><strong style = "font-size:14px;text-decoration:underline">Altura:</strong> ${dom.altura}</p>
                                                                        <br><br><br>
                                                                        <c:set var="elDomPag" value="${dom}" />
                                                                    </div>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach> 
                                                    </c:if>
                                                    <a href="salas/salasDisponibles/${sede.getIdSucursal()}"><button class="boton-estilo">Ver Salas</button></a>
                                                    <a href="sedes/update/${sede.getIdSucursal()}"><button class="boton-estilo">Editar Sede</button></a>
                                                    <a href="sedes/delete/${sede.getIdSucursal()}"><button class="boton-estilo">Eliminar Sede</button></a>
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
                    <br><br><br>
                        <c:if test = "${sessionScope.Exito == true}">
                            <br><br>
                            <div class="mensaje">
                                <h1>${sessionScope.mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
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
                                            <a href="salas/salasDisponibles/${sede.getIdSucursal()}"><button class="boton-estilo">Ver Salas</button></a>
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
