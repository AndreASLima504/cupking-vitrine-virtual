package com.project.pit.cupking.domain.persistence.repository;

import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {
    @EntityGraph(attributePaths = {
            "itens",
            "itens.cupcake"
    })
    Optional<Encomenda> findById(Long id);

    Optional<Encomenda> findByIdAndUsuarioEmailIgnoreCase(Long id, String email);

    @EntityGraph(attributePaths = {
            "itens",
            "itens.cupcake",
    })
    List<Encomenda> findAllByUsuarioEmailIgnoreCaseOrderByDataCadastroDesc(
            String email
    );
}
