package com.ccamposhe.workhub.controllers;


import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.dtos.LoginRequestDTO;
import com.ccamposhe.workhub.infra.security.TokenService;
import com.ccamposhe.workhub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto){
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        if (passwordEncoder.matches(dto.password(), user.getPassword())){
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
    }
}
