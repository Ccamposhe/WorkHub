package com.ccamposhe.workhub.dtos;

import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WorkspaceRequestDTO(
        @NotBlank(message = "O nome da empresao nao pode ficar vazio") //NotBlank nao deixa nada passar nem espacos em branco
        String name,

        @NotBlank(message = "O codigo de convite é obrigatorio")
        @Size(min = 4, max = 10, message = "O codigo deve ter entre 4 e 10 caracteres") //define um minimo e um limite para o codigo de convite
        String inviteCode,

        @NotNull(message = "O ID do usuario criador é obrigatorio")
        UUID userId
) {
}
