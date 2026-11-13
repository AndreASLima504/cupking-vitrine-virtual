package com.project.pit.cupking.web.controller;

import com.project.pit.cupking.domain.persistence.entity.Avaliacao;
import com.project.pit.cupking.domain.persistence.entity.Cupcake;
import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import com.project.pit.cupking.domain.persistence.entity.Usuario;
import com.project.pit.cupking.service.AvaliacaoService;
import com.project.pit.cupking.service.CupcakeService;
import com.project.pit.cupking.service.EncomendaService;
import com.project.pit.cupking.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final EncomendaService encomendaService;
    private final CupcakeService cupcakeService;
    private final AvaliacaoService avaliacaoService;
    private final UsuarioService usuarioService;

    @PostMapping("/enviar")
    public String enviarAvaliacao(
            @RequestParam Long cupcakeId,
            @RequestParam Long encomendaId,
            @RequestParam BigDecimal nota,
            @RequestParam(required = false) String descricao,
            Authentication authentication
    ) {

        Encomenda encomenda = encomendaService
                .buscarPorIdEEmailUsuario(
                        encomendaId,
                        authentication.getName()
                );

        // Verifica se a previsão de retirada já passou
        if (encomenda.getPrevisaoRetirada() == null ||
                encomenda.getPrevisaoRetirada().isAfter(LocalDateTime.now())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Esta encomenda ainda não está disponível para avaliação."
            );
        }

        // Verifica se o cupcake pertence à encomenda
        boolean cupcakeNaEncomenda = encomenda.getItens()
                .stream()
                .anyMatch(item ->
                        item.getCupcake().getId().equals(cupcakeId)
                );

        if (!cupcakeNaEncomenda) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Cupcake cupcake = cupcakeService.buscarEntidadePorId(cupcakeId);

        Usuario usuario = usuarioService.buscarPorEmail(
                authentication.getName()
        );

        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setCupcake(cupcake);
        avaliacao.setUsuario(usuario);
        avaliacao.setNota(nota);
        avaliacao.setDescricao(
                descricao == null || descricao.isBlank()
                        ? null
                        : descricao.trim()
        );

        try {
            avaliacaoService.cadastrar(avaliacao);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }

        return "redirect:/pedidos?avaliacao=sucesso";
    }
}