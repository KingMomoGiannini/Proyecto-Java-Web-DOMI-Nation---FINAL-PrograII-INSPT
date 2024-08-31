package com.domination.proyecto.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("userLogueado") != null) {
            response.sendRedirect("/inicio"); // redirige a la página de inicio si el usuario ya ha iniciado sesión
            return false;
        }
        return true;
    }
}