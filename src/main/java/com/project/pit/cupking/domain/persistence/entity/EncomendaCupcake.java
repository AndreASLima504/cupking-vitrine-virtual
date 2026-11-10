package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(
        name = "encomenda_cupcakes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ec_item",
                        columnNames = {
                                "id_encomenda",
                                "id_cupcake",
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

    @Column(nullable = false)
    private Integer quantidade;

    public EncomendaCupcake() {}

    public BigDecimal getSubtotal() {
        return cupcake.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(quantidade));
    }

}