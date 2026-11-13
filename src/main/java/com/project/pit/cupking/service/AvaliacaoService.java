package com.project.pit.cupking.service;

import com.project.pit.cupking.domain.persistence.entity.Avaliacao;
import com.project.pit.cupking.domain.persistence.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public Avaliacao cadastrar(Avaliacao avaliacao) {

        if (avaliacao.getNota() == null ||
                avaliacao.getNota().compareTo(BigDecimal.ONE) < 0 ||
                avaliacao.getNota().compareTo(BigDecimal.valueOf(5)) > 0) {

            throw new IllegalArgumentException(
                    "A nota deve estar entre 1 e 5."
            );
        }

        if (avaliacao.getUsuario() == null || avaliacao.getCupcake() == null) {
            throw new IllegalArgumentException(
                    "Usuário e cupcake são obrigatórios."
            );
        }

        boolean jaAvaliado = avaliacaoRepository.existsByUsuario_IdAndCupcake_Id(
                avaliacao.getUsuario().getId(),
                avaliacao.getCupcake().getId()
        );

        if (jaAvaliado) {
            throw new IllegalStateException(
                    "Você já avaliou este cupcake."
            );
        }

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        BigDecimal media = avaliacaoRepository.calcularNotaMediaPorCupcake(
                avaliacao.getCupcake().getId()
        );

        avaliacao.getCupcake().setNotaMedia(
                media != null ? media : BigDecimal.ZERO
        );

        return salva;
    }

    @Transactional(readOnly = true)
    public Map<Long, Avaliacao> buscarPorUsuario(String email) {

        return avaliacaoRepository.findAllByUsuario_EmailIgnoreCase(email)
                .stream()
                .collect(Collectors.toMap(
                        avaliacao -> avaliacao.getCupcake().getId(),
                        avaliacao -> avaliacao
                ));
    }
}