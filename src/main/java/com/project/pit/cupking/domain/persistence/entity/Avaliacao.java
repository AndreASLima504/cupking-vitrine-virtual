package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(
        name = "avaliacao",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_avaliacao_usuario_cupcake",
                        columnNames = {"id_usuario", "id_cupcake"}
                )
        }
)
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nota")
    private BigDecimal nota;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupcake", nullable = false)
    private Cupcake cupcake;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
