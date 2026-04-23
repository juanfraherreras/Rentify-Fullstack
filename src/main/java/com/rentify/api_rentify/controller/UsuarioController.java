package com.rentify.api_rentify.controller;

import com.rentify.api_rentify.model.Usuario;
import com.rentify.api_rentify.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        // En una app real, aquí encriptaríamos la contraseña
        usuario.setRol("USER");
        return usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        // Buscamos al usuario por su username
        return usuarioRepository.findByUsername(loginRequest.getUsername())
                .map(usuario -> {
                    // Verificamos la contraseña (en texto plano por ahora para tu proyecto)
                    if (usuario.getPassword().equals(loginRequest.getPassword())) {
                        return ResponseEntity.ok(usuario); // Retornamos el objeto usuario completo
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }
}