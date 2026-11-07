package com.project.pit.cupking.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public record CupcakeCardDTO(
        Long id,
        String nome,
        BigDecimal precoUnitario,
        BigDecimal notaMedia,
        Integer totalAvaliacoes,
        String imagemPrincipal
) {}