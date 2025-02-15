package io.github.pedromartinsl.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.pedromartinsl.libraryapi.security.CustomUserDetailsService;
import io.github.pedromartinsl.libraryapi.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) //permite autorização pelos controllers
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)//proteção para as pag html, pra fazer req de forma autenticada, precisa de um token csrf no backend - desabilitar para conseguir fazer uma requisição através de outras requisições
                .formLogin(Customizer.withDefaults())// formulário padrão
                .httpBasic(Customizer.withDefaults())// através do postman, fiormulário de login ou outras aplicações
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    
                    authorize.anyRequest().authenticated(); // a req precisa estar autenticada
                    //regras abaixo não serão atendidas
                }) 
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); //criptografa e produz um hash,  o10 é indicar quantas vezes passa pelo bcrypt
    }

    //@Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        // carregar os detalhes do usuário a partir do banco de dados ou de qualquer outro repositório de usuários durante a autenticação.
        return new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
