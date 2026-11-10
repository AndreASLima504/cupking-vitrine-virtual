package com.project.pit.cupking.service;

import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import com.project.pit.cupking.domain.persistence.repository.EncomendaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;

    @Transactional
    public Encomenda finalizar(Encomenda encomenda) {
        encomenda.setDataCadastro(LocalDateTime.now());
        encomenda.atualizarTotal();

        return encomendaRepository.save(encomenda);
    }

    @Transactional
    public Encomenda buscarPorId(Long id) {
        return encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Encomenda não encontrada: " + id
                ));
    }

}
