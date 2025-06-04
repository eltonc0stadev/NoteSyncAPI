package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.controllers.request.AuthRequest;
import dev.eltoncosta.notesyncapi.controllers.request.UsuarioRequest;
import dev.eltoncosta.notesyncapi.controllers.response.AuthResponse;
import dev.eltoncosta.notesyncapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notesync/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<Void> registrar(@RequestBody UsuarioRequest request) {
        authService.registrar(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

