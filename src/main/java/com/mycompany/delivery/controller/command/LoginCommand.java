package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.model.entity.User;
import com.mycompany.delivery.model.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "WEB-INF/views/login.jsp";
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.findByUsername(username);

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return "WEB-INF/views/login.jsp";
        }

        HttpSession session = request.getSession();

        session.setAttribute("username", user.getUsername());
        session.setAttribute("userRole", user.getRole().getName());

        return "redirect:/ ";
    }
}
