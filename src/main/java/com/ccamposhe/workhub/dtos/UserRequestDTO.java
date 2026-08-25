package com.ccamposhe.workhub.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "O nome é obrigatorio")
        String name,

        @NotBlank(message = "O email é obrigatorio")
        @Email(message = "formato de email invalido")
        String email,

        @NotBlank(message = "A senha é obrigatoria")
        @Size(min = 4, message = "A senha deve contar no minimo 6 caracteres")
        String password
) {
}
