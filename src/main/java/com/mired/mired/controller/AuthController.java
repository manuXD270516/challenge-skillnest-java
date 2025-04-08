package com.mired.mired.controller;

import com.mired.mired.dto.UserDto;
import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Tag(name = "Autenticación", description = "Registro, login y logout de usuarios")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Operation(summary = "Registro de usuario")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto dto, HttpSession session) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya está registrado");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);

        userRepository.save(user);
        session.setAttribute("user", user);
        return ResponseEntity.ok("Registro exitoso");
    }

    @Operation(summary = "Login de usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto dto, HttpSession session) {
        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        session.setAttribute("user", user);
        return ResponseEntity.ok("Login exitoso");
    }

    @Operation(summary = "Cerrar sesión actual")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada");
    }
}