package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "adm_flag", nullable = false)
    private Boolean admFlag = false;

    @OneToMany(mappedBy = "usuario")
    private List<Encomenda> encomendas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Avaliacao> avaliacoes = new ArrayList<>();
    public Usuario() {}

}