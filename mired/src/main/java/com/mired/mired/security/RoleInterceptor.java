package com.mired.mired.security;

import com.mired.mired.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) return true;

        RoleRequired required = handlerMethod.getMethodAnnotation(RoleRequired.class);
        if (required == null) return true;

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No autenticado");
            return false;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No autenticado");
            return false;
        }

        boolean hasRole = Arrays.asList(required.value()).contains(user.getRole());
        if (!hasRole) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("No autorizado");
            return false;
        }

        return true;
    }
}