package com.ccamposhe.workhub.dtos;

import com.ccamposhe.workhub.domain.User;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
