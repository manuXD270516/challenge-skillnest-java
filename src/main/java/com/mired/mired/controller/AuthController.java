package com.mired.mired.controller;

import com.mired.mired.dto.ApiResponse;
import com.mired.mired.dto.AuthResponse;
import com.mired.mired.dto.LoginRequest;
import com.mired.mired.dto.UserDto;
import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.repository.UserRepository;
import com.mired.mired.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder().message("El correo ya está registrado.").build());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);
        userRepository.save(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtService.generateToken(dto.getEmail());

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .token(token)
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.builder().message("Credenciales inválidas.").build());
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .token(token)
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        String userEmail = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Sesión cerrada exitosamente" + (userEmail != null ? " para: " + userEmail : ""))
                .build());
    }
}
