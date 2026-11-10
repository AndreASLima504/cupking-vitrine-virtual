package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column(name = "retirada", nullable = false)
    private Boolean retirada = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
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

    public int getQuantidadeTotal() {
        return itens.stream()
                .mapToInt(EncomendaCupcake::getQuantidade)
                .sum();
    }

    public void adicionarItem(EncomendaCupcake item) {
        item.setEncomenda(this);
        itens.add(item);
    }

    public void removerItem(EncomendaCupcake item) {
        itens.remove(item);
        item.setEncomenda(null);
    }

    public void atualizarTotal() {
        this.precoTotal = itens.stream()
                .map(item -> item.getCupcake().getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Encomenda() {}

}