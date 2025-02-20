package io.github.pedromartinsl.libraryapi.config;



import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Library, API",
        version = "v1",
        contact = @Contact(
            name = "Pedro Martins",
            email = "pedromartinsdelemos@gmail.com",
            url = "libraryapi.com"
        )
    ),
    security = {
        @SecurityRequirement(name = "bearerAuth") //Requisito de segurança, autenticação bearer
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER

)
public class OpenApiConfiguration {
}
