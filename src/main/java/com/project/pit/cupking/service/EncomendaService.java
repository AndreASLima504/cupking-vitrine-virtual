package com.project.pit.cupking.service;

import com.project.pit.cupking.domain.persistence.entity.Encomenda;
import com.project.pit.cupking.domain.persistence.entity.EncomendaCupcake;
import com.project.pit.cupking.domain.persistence.entity.Usuario;
import com.project.pit.cupking.domain.persistence.repository.CupcakeRepository;
import com.project.pit.cupking.domain.persistence.repository.EncomendaRepository;
import com.project.pit.cupking.domain.persistence.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CupcakeRepository cupcakeRepository;

    @Transactional
    public Encomenda finalizar(Encomenda encomenda, String emailUsuario) {

        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCase(emailUsuario)
                .orElseThrow(() -> new IllegalStateException(
                        "Usuário não encontrado."
                ));

        encomenda.setUsuario(usuario);

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

    @Transactional
    public Encomenda buscarPorIdEEmailUsuario(Long id, String email) {
        return encomendaRepository
                .findByIdAndUsuarioEmailIgnoreCase(id, email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public List<Encomenda> buscarPorEmailUsuario(String email) {

        List<Encomenda> encomendas = encomendaRepository
                .findAllByUsuarioEmailIgnoreCaseOrderByDataCadastroDesc(email);

        for (Encomenda encomenda : encomendas) {
            for (EncomendaCupcake item : encomenda.getItens()) {
                if (item == null) {
                    System.out.println(
                            "ERRO: item nulo na encomenda " + encomenda.getId()
                    );
                } else if (item.getCupcake() == null) {
                    System.out.println(
                            "ERRO: cupcake nulo no item " + item.getId()
                    );
                }
            }
        }

        return encomendas;
    }
    
    

}
