package com.rentify.api_rentify.service;

import com.rentify.api_rentify.model.Carrito;
import com.rentify.api_rentify.exception.UsuarioYaExisteException;
import com.rentify.api_rentify.model.Usuario;
import com.rentify.api_rentify.repository.CarritoRepository;
import com.rentify.api_rentify.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    // Usamos BCrypt para encriptar contraseñas
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificación: ¿Ya existe un usuario con este email?
        // Usamos el email porque más abajo se usa como username.
        if(usuarioRepository.findByUsername(usuario.getEmail()).isPresent()) {
            throw new UsuarioYaExisteException("El correo electrónico '" + usuario.getEmail() + "' ya está registrado.");
        }

        // 1. Encriptar la contraseña
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);

        // 2. Establecer el rol por defecto
        usuario.setRol("USER");

        // 3. Como tu entidad exige 'username' (nullable = false) y el front envía 'email',
        // utilizamos el email como username en caso de que este último sea nulo.
        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            usuario.setUsername(usuario.getEmail());
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crear un carrito automáticamente para el nuevo usuario
        Carrito carrito = new Carrito();
        carrito.setTotal(0.0);
        carrito.setCantidadArticulos(0);
        carrito.setUsuario(usuarioGuardado); // Asignamos el dueño del carrito
        carritoRepository.save(carrito);     // Guardamos el carrito en la BD

        return usuarioGuardado;
    }

    public boolean verificarPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}