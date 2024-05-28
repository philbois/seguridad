package com.example.seguridad.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.seguridad.models.AuthResponse;
import com.example.seguridad.models.LoginRequest;
import com.example.seguridad.models.RegisterRequest;
import com.example.seguridad.services.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
// este se comunica con el authservice para acceder al token
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    // en este caso busca el usuario q esta autenticado
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // en este caso se crea un nuevo registro de usuario
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
