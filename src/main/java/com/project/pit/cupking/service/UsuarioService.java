package com.project.pit.cupking.service;

import com.project.pit.cupking.domain.persistence.entity.Usuario;
import com.project.pit.cupking.domain.persistence.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario cadastrar(String nome, String email, String senha) {

        email = email.trim().toLowerCase();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(nome.trim());
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setAdmFlag(false);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado: " + email
                ));
    }
}