package com.example.prs.Controller;

import com.example.prs.models.Person;
import com.example.prs.models.Role;
import com.example.prs.repositories.PersonRepository;
import com.example.prs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("")
public class RegistrationController {

    private final UserService userService;
    private final PersonRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public RegistrationController(UserService userService, PersonRepository userRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser( @ModelAttribute("user") Person user, BindingResult result, HttpServletRequest request, HttpServletResponse response, Model model) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (userRepository.existsByLogin(user.getLogin())) {
            result.rejectValue("login", "error.user", "Данный логин уже существует");
            return "registration";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Данный email уже существует");
            return "registration";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRoles(Collections.singleton(Role.USER));
        userService.createUser(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,securityContext);
        return "redirect:/login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest response) {
        HttpSession session =  response.getSession();
        session.invalidate();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        return "redirect:/login";
    }

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model) {
        Person user = new Person();
        model.addAttribute("user", user);

        return "registration";
    }
}