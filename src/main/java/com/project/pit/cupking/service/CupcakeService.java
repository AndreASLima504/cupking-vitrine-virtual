package com.project.pit.cupking.service;

import com.project.pit.cupking.domain.persistence.entity.Cupcake;
import com.project.pit.cupking.domain.persistence.entity.CupcakeFoto;
import com.project.pit.cupking.domain.persistence.entity.Ingrediente;
import com.project.pit.cupking.domain.persistence.repository.CupcakeRepository;
import com.project.pit.cupking.web.dto.CupcakeCardDTO;
import com.project.pit.cupking.web.dto.CupcakeDetalhesDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CupcakeService {
    private final CupcakeRepository cupcakeRepository;

    public CupcakeService(CupcakeRepository cupcakeRepository) {
        this.cupcakeRepository = cupcakeRepository;
    }

    /**
     * Lista todos os cupcakes ou filtra pelo nome.
     */
    @Transactional
    public List<CupcakeCardDTO> listarCards(String busca) {

        List<Cupcake> cupcakes;

        if (busca == null || busca.isBlank()) {
            cupcakes = cupcakeRepository.findAllByOrderByNomeAsc();
        } else {
            cupcakes = cupcakeRepository
                    .findByNomeContainingIgnoreCaseOrderByNomeAsc(busca.trim());
        }

        return cupcakes.stream()
                .map(this::converterParaCard)
                .toList();
    }

    @Transactional
    public Cupcake buscarEntidadePorId(Long id) {
        return cupcakeRepository.findByIdComFotos(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cupcake não encontrado: " + id
                ));
    }

    @Transactional
    public Optional<CupcakeDetalhesDTO> buscarDetalhes(Long id) {
        return cupcakeRepository.findById(id)
                .map(cupcake -> {
                    List<String> fotos = cupcake.getFotos()
                            .stream()
                            .map(CupcakeFoto::getCaminho)
                            .toList();

                    if (fotos.isEmpty()) {
                        fotos = List.of("/images/cupcake-placeholder.png");
                    }

                    List<String> ingredientes = cupcake.getIngredientes()
                            .stream()
                            .map(Ingrediente::getDescricao)
                            .sorted()
                            .toList();

                    return new CupcakeDetalhesDTO(
                            cupcake.getId(),
                            cupcake.getNome(),
                            cupcake.getDescricao(),
                            cupcake.getPrecoUnitario(),
                            cupcake.getNotaMedia(),
                            fotos,
                            ingredientes
                    );
                });
    }

    private CupcakeCardDTO converterParaCard(Cupcake cupcake) {

        String imagemPrincipal = cupcake.getFotos()
                .stream()
                .filter(foto -> Boolean.TRUE.equals(foto.getIsThumbnail()))
                .map(CupcakeFoto::getCaminho)
                .findFirst()
                .orElseGet(() ->
                        cupcake.getFotos()
                                .stream()
                                .map(CupcakeFoto::getCaminho)
                                .findFirst()
                                .orElse("/images/cupcake-placeholder.png")
                );

        return new CupcakeCardDTO(
                cupcake.getId(),
                cupcake.getNome(),
                cupcake.getPrecoUnitario(),
                cupcake.getNotaMedia(),
                null,
                imagemPrincipal
        );
    }
}
