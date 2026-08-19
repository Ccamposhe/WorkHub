package com.ccamposhe.workhub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceRequestDTO(
        @NotBlank(message = "O nome da empresao nao pode ficar vazio") //NotBlank nao deixa nada passar nem espacos em branco
        String name,

        @NotBlank(message = "O codigo de convite é obrigatorio")
        @Size(min = 4, max = 10, message = "O codigo deve ter entre 4 e 10 caracteres") //define um minimo e um limite para o codigo de convite
        String inviteCode
) {
}
