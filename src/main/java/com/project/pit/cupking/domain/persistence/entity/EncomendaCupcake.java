package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "encomenda_cupcakes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ec_item",
                        columnNames = {
                                "id_encomenda",
                                "id_cupcake",
                                "tamanho"
                        }
                )
        }
)
public class EncomendaCupcake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_encomenda", nullable = false)
    private Encomenda encomenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupcake", nullable = false)
    private Cupcake cupcake;

    @Column(nullable = false, length = 30)
    private String tamanho;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(
            name = "preco_unitario",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal precoUnitario;

    public EncomendaCupcake() {}

}