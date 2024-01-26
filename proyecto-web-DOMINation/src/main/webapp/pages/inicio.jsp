<%-- 
    Document   : inicio
    Created on : 16 oct 2023, 21:15:13
    Author     : giann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/inicio.css">
        <title>inicio</title>
    </head>

<body>
    <c:import url ="../navbar.jsp" />
    <div class="elcontainer">
        <div class="container-inicial">
            <h1>Bienvenido/a ${userLogueado.nomUsuario}</h1>
        </div>
        <div>
            <c:choose>
                <c:when test="${userLogueado.getRol() == 'administrador'}">
                    <br><br><br>
                    <c:if test = "${Exito==true}">
                        <div class="mensaje">
                            <h1>${mensajeExito}</h1>
                            <br><br><br>
                        </div>
                    </c:if>
                    <c:if test="${not empty lasReservas}">
                        <div class="centrarEnPag">
                            <a class="botoncin" href="reservas/listaReservas"><button>Ver Reservas</button></a>
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
                                                <p><strong style = "font-size:14px;text-decoration:underline">ID de Sede:</strong> ${sede.getIdSede()}</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Cantidad de Salas:</strong> ${sede.cantSalas}</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Hora de Inicio:</strong> ${sede.horaInicio} hs</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Hora de Fin:</strong> ${sede.horaFin} hs</p>
                                                <p><strong style = "font-size:14px;text-decoration:underline">Teléfono:</strong> ${sede.telefono}</p>
                                                <c:set var="elDomPag" value="${null}" />
                                                <c:if test = "${not empty domiciliosDeSedes}">
                                                    <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                        <c:choose>
                                                            <c:when test= "${dom.getIdSucursal() == sede.getIdSede()}">
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
                                                <a class="botoncin" href="sedes/delete?id=${sede.getIdSede()}&idDom=${elDomPag.getId()}"><button>Eliminar Sede</button></a>
                                                <a class="botoncin" href="salas/salasDisponibles?idSede=${sede.getIdSede()}"><button>Ver Salas</button></a>
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
                                            <p><strong style = "font-size:14px;text-decoration:underline">Nombre de usuario:</strong> ${user.getNomUsuario()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Nombre:</strong> ${user.getNombre()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Apellido:</strong> ${user.getApellido()} </p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Email:</strong> ${user.getEmail()}</p>
                                            
                                            <c:set var="elUserPag" value="${user}" />
                                            <p><strong style = "font-size:14px;text-decoration:underline">Rol:</strong> ${elUserPag.getRol()}</p>
                                            <br><br>

                                            <a class="botoncin" href="usuarios/delete?id=${elUserPag.getIdUsuario()}"><button>Eliminar Usuario</button></a>
                                        </div>
                                    </div>

                            </c:forEach>
                        </div>
                    </c:if>
                    
                </c:when>
                <c:when test="${userLogueado.getRol() == 'prestador'}">
                    <br><br><br>
                    <div>
                        <c:if test = "${Exito==true}">
                            <div class="mensaje">
                                <h1>${mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty lasReservas}">
                            <div class="centrarEnPag">
                                <a class="botoncin" href="reservas/listaReservas"><button>Ver Reservas</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test = "${not empty sedesDelUsuario}">
                            <div class="sedes-row">
                                <c:forEach items ="${sedesDelUsuario}" var="sede">
                                    <c:choose>
                                        <c:when test= "${sede.getIdPrestador() == userLogueado.getIdPrestador()}">
                                            <div class="sede-container">
                                                <div style="color:white">
                                                    <h1 style = "font-size:30px">Sucursal</h1>
                                                    <h2 style = "color:red;font-size:20px ">${sede.nombre}</h2>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">ID de Sede:</strong> ${sede.getIdSede()}</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Cantidad de Salas:</strong> ${sede.cantSalas}</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de Inicio:</strong> ${sede.horaInicio} hs</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Hora de Fin:</strong> ${sede.horaFin} hs</p>
                                                    <p><strong style = "font-size:14px;text-decoration:underline">Teléfono:</strong> ${sede.telefono}</p>
                                                    <c:set var="elDomPag" value="${null}" />
                                                    <c:if test = "${not empty domiciliosDeSedes}">
                                                        <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                            <c:choose>
                                                                <c:when test= "${dom.getIdSucursal() == sede.getIdSede()}">
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
                                                    <a class="botoncin" href="salas/salasDisponibles?idSede=${sede.getIdSede()}"><button>Ver Salas</button></a>
                                                    <a class="botoncin" href="sedes/edit?id=${sede.getIdSede()}&idDom=${elDomPag.getId()}"><button>Editar Sede</button></a>
                                                    <a class="botoncin" href="sedes/delete?id=${sede.getIdSede()}&idDom=${elDomPag.getId()}"><button>Eliminar Sede</button></a>
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
                            <a class="botoncin centrarEnPag" href="sedes/create"><button>Crear Sede</button></a>
                        </div>      
                    </div>
                    <br><br><br>
                </c:when> <%-- --%>   
                <c:when test="${userLogueado.getRol() == 'cliente'}">
                    <br><br><br>
                        <c:if test = "${Exito==true}">
                            <div class="mensaje">
                                <h1>${mensajeExito}</h1>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty lasReservas}">
                            <div class="centrarEnPag">
                                <a class="botoncin" href="reservas/misReservas?idCliente=${userLogueado.getIdCliente()}"><button>Ver mis Reservas</button></a>
                                <br><br><br>
                            </div>
                        </c:if>
                        <c:if test = "${not empty sedesDelUsuario}">
                            <div class="sedes-row">
                                <c:forEach items ="${sedesDelUsuario}" var="sede">
                                    <div class="sede-container">
                                        <div style="color:white">
                                            <h1 style = "font-size:30px">Sucursal</h1>
                                            <h2 style = "color:red;font-size:20px ">${sede.nombre}</h2>
                                            <p><strong style = "font-size:14px;text-decoration:underline">ID de Sede:</strong> ${sede.getIdSede()}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Cantidad de Salas:</strong> ${sede.cantSalas}</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Hora de Inicio:</strong> ${sede.horaInicio} hs</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Hora de Fin:</strong> ${sede.horaFin} hs</p>
                                            <p><strong style = "font-size:14px;text-decoration:underline">Teléfono:</strong> ${sede.telefono}</p>

                                            <c:if test = "${not empty domiciliosDeSedes}">
                                                <c:forEach items ="${domiciliosDeSedes}" var="dom">
                                                    <c:choose>
                                                        <c:when test= "${dom.getIdSucursal() == sede.getIdSede()}">
                                                            <div style="color:white">
                                                                <h1 style = "font-size:30px">Dirección</h1>
                                                                <p><strong style = "font-size:14px;text-decoration:underline">Provincia:</strong> ${dom.provincia}</p>
                                                                <p><strong style = "font-size:14px;text-decoration:underline">Localidad:</strong> ${dom.localidad}</p>
                                                                <p><strong style = "font-size:14px;text-decoration:underline">Partido:</strong> ${dom.partido}</p>
                                                                <p><strong style = "font-size:14px;text-decoration:underline">Calle:</strong> ${dom.calle}</p>
                                                                <p><strong style = "font-size:14px;text-decoration:underline">Altura:</strong> ${dom.altura}</p>
                                                                <br><br><br>
                                                            </div>

                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach> 
                                            </c:if>
                                            <a class="botoncin" href="salas/salasDisponibles?idSede=${sede.getIdSede()}"><button>Ver Salas</button></a>
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
    <c:import url ="../footer.jsp" />
</body>
</html>
