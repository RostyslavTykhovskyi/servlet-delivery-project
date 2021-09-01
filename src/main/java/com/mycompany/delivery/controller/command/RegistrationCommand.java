package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.model.entity.User;
import com.mycompany.delivery.model.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "WEB-INF/views/registration.jsp";
        }

        User user = new User();

        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(new BCryptPasswordEncoder().encode(request.getParameter("password")));
        user.setBalance(0);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        userService.saveUser(user);

        return "redirect:/";
    }
}
