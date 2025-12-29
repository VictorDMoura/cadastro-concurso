package com.concurso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aprovados")
public class Aprovado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$", 
             message = "Telefone inválido. Use o formato: (XX) XXXXX-XXXX")
    @Column(nullable = false, length = 20)
    private String telefone;

    @ElementCollection
    @CollectionTable(name = "aprovado_concursos", joinColumns = @JoinColumn(name = "aprovado_id"))
    @Column(name = "concurso", nullable = false, length = 200)
    private List<String> concursos = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imagem;

    @Column(length = 100)
    private String imagemNome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    public Aprovado() {
    }

    public Aprovado(Long id, String nome, String email, String telefone, List<String> concursos, 
                   byte[] imagem, String imagemNome, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.concursos = concursos;
        this.imagem = imagem;
        this.imagemNome = imagemNome;
        this.dataCadastro = dataCadastro;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<String> getConcursos() {
        return concursos;
    }

    public void setConcursos(List<String> concursos) {
        this.concursos = concursos;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemNome() {
        return imagemNome;
    }

    public void setImagemNome(String imagemNome) {
        this.imagemNome = imagemNome;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
