package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cupcake")
public class Cupcake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer estoque = 0;

    @Column(length = 1000)
    private String descricao;

    @Column(
            name = "preco_unitario",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal precoUnitario;

    @Column(name = "nota_media")
    private BigDecimal notaMedia;

    @ManyToMany
    @JoinTable(
            name = "cupcake_ingredientes",
            joinColumns = @JoinColumn(name = "id_cupcake"),
            inverseJoinColumns = @JoinColumn(name = "id_ingrediente")
    )
    private Set<Ingrediente> ingredientes = new HashSet<>();

    @OneToMany(mappedBy = "cupcake")
    private List<EncomendaCupcake> itensEncomenda = new ArrayList<>();

    public Cupcake() {}
    @OneToMany(
            mappedBy = "cupcake",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("ordem ASC")
    private List<CupcakeFoto> fotos = new ArrayList<>();

}