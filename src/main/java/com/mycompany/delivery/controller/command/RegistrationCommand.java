package com.mycompany.delivery.controller.command;

import com.mycompany.delivery.exception.NotUniqueEntityException;
import com.mycompany.delivery.model.entity.User;
import com.mycompany.delivery.model.service.UserService;
import com.mycompany.delivery.model.validator.Validator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> errors = Validator.validateUserData(username, email, password);

        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("password", password);

            return "WEB-INF/views/registration.jsp";
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setBalance(0);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        try {
            userService.saveUser(user);
        } catch (NotUniqueEntityException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("password", password);

            return "WEB-INF/views/registration.jsp";
        }

        return "redirect:/";
    }
}
