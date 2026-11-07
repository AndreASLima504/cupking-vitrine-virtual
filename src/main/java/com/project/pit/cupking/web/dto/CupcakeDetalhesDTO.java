package com.project.pit.cupking.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record CupcakeDetalhesDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal precoUnitario,
        BigDecimal notaMedia,
        List<String> fotos,
        List<String> ingredientes
) {}