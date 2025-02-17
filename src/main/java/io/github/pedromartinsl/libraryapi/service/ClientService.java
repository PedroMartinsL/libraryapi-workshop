package io.github.pedromartinsl.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.pedromartinsl.libraryapi.model.Client;
import io.github.pedromartinsl.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client) {
        String senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterPorClientID(String clientId) {
        return repository.findByClientId(clientId);
    }
}
