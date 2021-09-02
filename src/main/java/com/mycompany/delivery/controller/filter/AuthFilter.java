package com.mycompany.delivery.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
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

        String loginURI = request.getContextPath() + "/login";
        String registrationURI = request.getContextPath() + "/registration";
        String mainURI = request.getContextPath() + "/";
        String orderURI = request.getContextPath() + "/order";
        String imgURI = request.getContextPath() + "/img";

        boolean loggedIn = session != null && session.getAttribute("username") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean registrationRequest = request.getRequestURI().equals(registrationURI);
        boolean mainRequest = request.getRequestURI().equals(mainURI);
        boolean orderRequest = request.getRequestURI().equals(orderURI);
        boolean imgRequest = request.getRequestURI().contains(imgURI);

        if (loggedIn || loginRequest || registrationRequest || mainRequest || orderRequest || imgRequest) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}
