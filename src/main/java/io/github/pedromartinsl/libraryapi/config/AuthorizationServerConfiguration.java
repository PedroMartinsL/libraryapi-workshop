package io.github.pedromartinsl.libraryapi.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
        .accessTokenTimeToLive(Duration.ofMinutes(60))
        .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
        .requireAuthorizationConsent(false)//tela de consentimento do google - trabalhar com as info
        .build();  
    }

}
