package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descricao;

    @ManyToMany(mappedBy = "ingredientes")
    private Set<Cupcake> cupcakes = new HashSet<>();

    public Ingrediente() {}

}