package io.github.pedromartinsl.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.pedromartinsl.libraryapi.model.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByClientId(String clientId);

}
