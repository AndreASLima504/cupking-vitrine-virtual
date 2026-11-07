package com.project.pit.cupking.web.controller;

import com.project.pit.cupking.service.CupcakeService;
import com.project.pit.cupking.web.dto.CupcakeDetalhesDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class CustomerController {

    private final CupcakeService cupcakeService;

    @GetMapping
    public String homeProdutos(
            @RequestParam(required = false) String busca,
            Model model
    ) {

        model.addAttribute(
                "cupcakes",
                cupcakeService.listarCards(busca)
        );

        model.addAttribute("busca", busca);

        return "home-produtos";
    }

    @GetMapping("/cupcakes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        CupcakeDetalhesDTO cupcake = cupcakeService.buscarDetalhes(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("cupcake", cupcake);

        return "cupcake-detalhes";
    }

}
