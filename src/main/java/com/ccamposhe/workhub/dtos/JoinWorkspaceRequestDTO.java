package com.ccamposhe.workhub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JoinWorkspaceRequestDTO(

        @NotBlank(message = "O codigo de convite é obrigatorio")
        String inviteCode
) {
}
