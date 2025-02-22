package io.github.pedromartinsl.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "autor")
@Getter
@Setter
@ToString(exclude = {"livros"})
@EntityListeners(AuditingEntityListener.class) //escutar operacoes na entidade e observar as anotações de data
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", 
    //cascade = CascadeType.ALL
    fetch = FetchType.LAZY)
    private List<Livro> livros;

    @CreatedDate //sempre que persistir
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate //
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
