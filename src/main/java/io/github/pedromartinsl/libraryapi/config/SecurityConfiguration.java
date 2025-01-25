package io.github.pedromartinsl.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)//proteção para as pag html, pra fazer req de forma autenticada, precisa de um token csrf no backend - desabilitar para conseguir fazer uma requisição através de outras requisições
                .formLogin(Customizer.withDefaults())// formulário padrão
                .httpBasic(Customizer.withDefaults())// através do postman, fiormulário de login ou outras aplicações
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // a req precisa estar autenticada
                .build();
    }
}
