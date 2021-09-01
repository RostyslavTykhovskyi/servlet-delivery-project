package com.mycompany.delivery.controller.filter;

import com.mycompany.delivery.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null
                && session.getAttribute("username") != null
                && session.getAttribute("userRole") != null;

        if (loggedIn) {
            String userRole = (String) session.getAttribute("userRole");
            if (userRole.equals("ROLE_USER")) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else if (userRole.equals("ROLE_ADMIN")) {
                filterChain.doFilter(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
