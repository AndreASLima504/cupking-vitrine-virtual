package com.project.pit.cupking.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroUsuarioDTO {

    @NotBlank(message = "Informe seu nome.")
    @Size(max = 100, message = "O nome deve ter até 100 caracteres.")
    private String nome;

    @NotBlank(message = "Informe seu e-mail.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 50, message = "O e-mail deve ter até 50 caracteres.")
    private String email;

    @NotBlank(message = "Informe uma senha.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String senha;

    @NotBlank(message = "Confirme sua senha.")
    private String confirmarSenha;
}