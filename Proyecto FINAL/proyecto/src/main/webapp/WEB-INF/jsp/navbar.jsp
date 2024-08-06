<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/navbar.css"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <img src="../img/logo.png" alt="LogoDomi" class="logo-img">
    </a>
    <span class="navbar-text ml-auto eltitulo-container">
        <del class="eltitulo">DO MI</del><b class="eltitulo color"> Nation</b>
    </span>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${userLogueado == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Inicio</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="/inicio">Inicio</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
        <div class="ml-auto botones-container">
            <c:choose>
                <c:when test="${userLogueado == null}">
                    <a class="btn btn-outline-light botoncin" href="/login">Ingresar</a>
                    <a class="btn btn-outline-light botoncin" href="/registrarse">Registrarse</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-outline-light botoncin" href="/logout">Cerrar Sesi�n</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>