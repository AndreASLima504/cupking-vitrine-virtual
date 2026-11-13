package com.project.pit.cupking.web.controller;

import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import com.project.pit.cupking.service.AvaliacaoService;
import com.project.pit.cupking.service.EncomendaService;
import com.project.pit.cupking.service.UsuarioService;
import com.project.pit.cupking.web.dto.CadastroUsuarioDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final EncomendaService encomendaService;
    private final UsuarioService usuarioService;
    private final AvaliacaoService avaliacaoService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {

        model.addAttribute("cadastro", new CadastroUsuarioDTO());

        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("cadastro") CadastroUsuarioDTO dto,
            BindingResult result,
            Model model) {

        if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
            result.rejectValue(
                    "confirmarSenha",
                    "senha.diferente",
                    "As senhas não coincidem."
            );
        }

        if (result.hasErrors()) {
            return "cadastro";
        }

        try {
            usuarioService.cadastrar(
                    dto.getNome(),
                    dto.getEmail(),
                    dto.getSenha()
            );

            return "redirect:/login?cadastro";

        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "email.existente", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/pedidos")
    public String listarPedidos(
            Model model,
            Authentication authentication
    ) {
        model.addAttribute(
                "pedidos",
                encomendaService.buscarPorEmailUsuario(authentication.getName())
        );

        model.addAttribute("agora", LocalDateTime.now());

        model.addAttribute(
                "avaliacoesPorCupcake",
                avaliacaoService.buscarPorUsuario(authentication.getName())
        );

        return "pedidos";
    }
}