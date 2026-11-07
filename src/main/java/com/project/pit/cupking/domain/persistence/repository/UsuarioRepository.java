package com.project.pit.cupking.domain.persistence.repository;

import com.project.pit.cupking.domain.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
