package io.github.pedromartinsl.libraryapi.security;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import io.github.pedromartinsl.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientService.obterPorClientID(clientId);

        if (client == null) {
            return null;
        }

        return RegisteredClient
        .withId(client.getId().toString())
        .clientId(client.getClientId())
        .clientSecret(client.getClientSecret())
        .redirectUri(client.getRedirectURI())
        .scope(client.getScope())
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .tokenSettings(tokenSettings)
        .clientSettings(clientSettings)
        .build();
    }

    @Override
    public RegisteredClient findById(String clientId) {
        return null;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
    }

}
