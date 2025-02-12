package io.github.pedromartinsl.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)//proteção para as pag html, pra fazer req de forma autenticada, precisa de um token csrf no backend - desabilitar para conseguir fazer uma requisição através de outras requisições
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })// formulário padrão
                .httpBasic(Customizer.withDefaults())// através do postman, fiormulário de login ou outras aplicações
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
                    authorize.anyRequest().authenticated(); // a req precisa estar autenticada
                    //regras abaixo não serão atendidas
                }) 
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); //criptografa e produz um hash,  o10 é indicar quantas vezes passa pelo bcrypt
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user1 = User.builder()
            .username("usuario")
            .password(encoder.encode("123"))
            .roles("USER")
            .build();

        UserDetails user2 = User.builder()
            .username("admin")
            .password(encoder.encode("321"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
