package io.github.pedromartinsl.libraryapi.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;
    
    @Column
    private String senha;

    @Column
    private String email;

    @Type(ListArrayType.class)//tradutor de tipos
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
