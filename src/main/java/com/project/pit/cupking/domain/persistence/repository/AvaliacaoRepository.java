package com.project.pit.cupking.domain.persistence.repository;

import com.project.pit.cupking.domain.persistence.entity.Avaliacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByUsuario_IdAndCupcake_Id(Long usuarioId, Long cupcakeId);

    List<Avaliacao> findAllByUsuario_EmailIgnoreCase(String email);

    @Query("""
        SELECT AVG(a.nota)
        FROM Avaliacao a
        WHERE a.cupcake.id = :cupcakeId
    """)
    BigDecimal calcularNotaMediaPorCupcake(Long cupcakeId);
}