package com.rentify.api_rentify.controller;

import com.rentify.api_rentify.model.Usuario;
import com.rentify.api_rentify.repository.UsuarioRepository;
import com.rentify.api_rentify.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        // Delegamos la lógica de encriptación y preparación al servicio
        Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(usuarioGuardado); // Devuelve status 200 con el JSON del usuario
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        // Buscamos al usuario por su username
        return usuarioRepository.findByUsername(loginRequest.getUsername())
                .map(usuario -> {
                    // Verificamos la contraseña usando BCrypt (encriptada)
                    // NOTA: Si ya tenías usuarios creados en texto plano en tu BD, este login fallará para ellos.
                    if (usuarioService.verificarPassword(loginRequest.getPassword(), usuario.getPassword())) {
                        return ResponseEntity.ok(usuario); // Retornamos el objeto usuario completo
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }
}