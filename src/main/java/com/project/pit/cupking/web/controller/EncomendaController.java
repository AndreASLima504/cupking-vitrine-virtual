package com.project.pit.cupking.web.controller;

import com.project.pit.cupking.domain.persistence.entity.Cupcake;
import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import com.project.pit.cupking.domain.persistence.entity.EncomendaCupcake;
import com.project.pit.cupking.service.CupcakeService;
import com.project.pit.cupking.service.EncomendaService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@AllArgsConstructor
@Controller
@RequestMapping("/carrinho")
public class EncomendaController {
    @GetMapping
    public String visualizarCarrinho(HttpSession session, Model model) {
        Encomenda encomenda = obterEncomenda(session);

        encomenda.atualizarTotal();

        model.addAttribute("encomenda", encomenda);

        return "carrinho";
    }

    private final CupcakeService cupcakeService;
    private final EncomendaService encomendaService;

    @PostMapping("/itens")
    public String adicionarItem(
            @RequestParam Long cupcakeId,
            @RequestParam(defaultValue = "1") Integer quantidade,
            HttpSession session
    ) {
        Encomenda encomenda = obterEncomenda(session);

        Cupcake cupcake = cupcakeService.buscarEntidadePorId(cupcakeId);

        EncomendaCupcake item = new EncomendaCupcake();
        item.setCupcake(cupcake);
        item.setQuantidade(quantidade);

        encomenda.adicionarItem(item);
        encomenda.atualizarTotal();

        return "redirect:/carrinho";
    }

    @PostMapping("/itens/remover")
    public String removerItem(
            @RequestParam Long cupcakeId,
            HttpSession session
    ) {
        Encomenda encomenda = obterEncomenda(session);

        encomenda.getItens().removeIf(item ->
                item.getCupcake().getId().equals(cupcakeId)
        );

        encomenda.atualizarTotal();

        return "redirect:/carrinho";
    }

    @PostMapping("/itens/atualizar")
    public String atualizarQuantidade(
            @RequestParam Long cupcakeId,
            @RequestParam Integer quantidade,
            HttpSession session
    ) {
        Encomenda encomenda = obterEncomenda(session);

        if (quantidade == null || quantidade < 1) {
            return "redirect:/carrinho";
        }

        encomenda.getItens().stream()
                .filter(item -> item.getCupcake().getId().equals(cupcakeId))
                .findFirst()
                .ifPresent(item -> item.setQuantidade(quantidade));

        encomenda.atualizarTotal();

        return "redirect:/carrinho";
    }

    @GetMapping("/retirada")
    public String visualizarRetirada(HttpSession session, Model model) {
        Encomenda encomenda = obterEncomenda(session);

        if (encomenda.getItens().isEmpty()) {
            return "redirect:/carrinho";
        }

        encomenda.atualizarTotal();

        model.addAttribute("encomenda", encomenda);
        model.addAttribute(
                "dataHoraMinima",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                )
        );

        return "finalizacao-encomenda";
    }

    @PostMapping("/finalizar")
    public String finalizar(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataRetirada,

            @RequestParam
            @DateTimeFormat(pattern = "HH:mm")
            LocalTime horaRetirada,

            Authentication authentication,
            HttpSession session
    ) {
        Encomenda encomenda = obterEncomenda(session);

        if (encomenda.getItens().isEmpty()) {
            return "redirect:/carrinho";
        }

        LocalDateTime previsaoRetirada =
                LocalDateTime.of(dataRetirada, horaRetirada);

        if (!horarioValido(previsaoRetirada)) {
            return "redirect:/carrinho/retirada?erro=horario-invalido";
        }

        encomenda.setPrevisaoRetirada(previsaoRetirada);
        encomenda.setRetirada(true);

        Encomenda encomendaSalva = encomendaService.finalizar(
                encomenda,
                authentication.getName()
        );

        session.removeAttribute("encomenda");

        return "redirect:/carrinho/confirmacao/" + encomendaSalva.getId();
    }

    @GetMapping("/confirmacao/{id}")
    public String visualizarConfirmacao(
            @PathVariable Long id,
            Authentication authentication,
            Model model
    ) {
        Encomenda encomenda = encomendaService.buscarPorIdEEmailUsuario(
                id,
                authentication.getName()
        );

        model.addAttribute("encomenda", encomenda);

        return "confirmacao-encomenda";
    }


    private boolean horarioValido(LocalDateTime dataHora) {
        if (dataHora.isBefore(LocalDateTime.now())) {
            return false;
        }

        int diaSemana = dataHora.getDayOfWeek().getValue();
        LocalTime hora = dataHora.toLocalTime();

        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fim = diaSemana >= 6
                ? LocalTime.of(13, 0)
                : LocalTime.of(18, 0);

        return !hora.isBefore(inicio) && !hora.isAfter(fim);
    }


    private Encomenda obterEncomenda(HttpSession session) {
        Encomenda encomenda =
                (Encomenda) session.getAttribute("encomenda");

        if (encomenda == null) {
            encomenda = new Encomenda();
            encomenda.setItens(new ArrayList<>());
            session.setAttribute("encomenda", encomenda);
        }

        return encomenda;
    }


}
