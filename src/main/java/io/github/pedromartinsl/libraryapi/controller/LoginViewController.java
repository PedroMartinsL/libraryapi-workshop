package io.github.pedromartinsl.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody // Para não esperar uma página e sim um corpo de resposta
    public String paginaHome(Authentication authentication) {
        return "Olá " + authentication.getName();
    }
    
}
