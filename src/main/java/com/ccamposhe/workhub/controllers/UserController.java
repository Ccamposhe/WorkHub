package com.ccamposhe.workhub.controllers;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.dtos.UserRequestDTO;
import com.ccamposhe.workhub.dtos.UserResponseDTO;
import com.ccamposhe.workhub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto){
        User newUser = new User();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPassword(passwordEncoder.encode(dto.password()));

        User savedUser = service.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(savedUser));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(@AuthenticationPrincipal User loggedUser){
        return ResponseEntity.ok(new UserResponseDTO(loggedUser));
    }
}