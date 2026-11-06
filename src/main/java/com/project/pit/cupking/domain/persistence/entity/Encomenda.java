package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "encomenda")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_retirada")
    private LocalDateTime dataRetirada;

    @Column(name = "previsao_retirada")
    private LocalDateTime previsaoRetirada;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(
            name = "preco_total",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal precoTotal = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(
            mappedBy = "encomenda",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EncomendaCupcake> itens = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }

    public Encomenda() {}

}