package com.project.pit.cupking.domain.persistence.repository;

import com.project.pit.cupking.domain.persistence.entity.Cupcake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CupcakeRepository extends JpaRepository<Cupcake, Long> {
    List<Cupcake> findAllByOrderByNomeAsc();

    List<Cupcake> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    Optional<Cupcake> findFirstByOrderByIdAsc();

    @Query("""
        SELECT c
        FROM Cupcake c
        WHERE c.notaMedia IS NOT NULL
        ORDER BY c.notaMedia DESC, c.id ASC
    """)
    List<Cupcake> buscarMelhorAvaliado(
            org.springframework.data.domain.Pageable pageable
    );

    @Query("""
    SELECT DISTINCT c
    FROM Cupcake c
    LEFT JOIN FETCH c.fotos
    WHERE c.id = :id
""")
    Optional<Cupcake> findByIdComFotos(@Param("id") Long id);
}
