package com.project.pit.cupking.domain.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cupcake_image")
public class CupcakeFoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupcake", nullable = false)
    private Cupcake cupcake;

    @Column(name="caminho", nullable = false, length = 500)
    private String caminho;

    @Column(name="is_thumbnail")
    private Boolean isThumbnail;

    @Column(name="ordem")
    private Integer ordem;
}
