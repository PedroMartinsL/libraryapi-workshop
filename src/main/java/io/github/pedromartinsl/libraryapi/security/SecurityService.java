package io.github.pedromartinsl.libraryapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.github.pedromartinsl.libraryapi.model.Usuario;
import io.github.pedromartinsl.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityService {
    
    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof CustomAuthentication customAuth) {
            return customAuth.getUsuario();
        }

        return null;
    }
}
