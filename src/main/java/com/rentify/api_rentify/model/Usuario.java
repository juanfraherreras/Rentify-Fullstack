package com.rentify.api_rentify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Aquí guardaremos el hash de la clave

    private String nombre;
    private String email;
    private String rol; // "ADMIN" para dueños, "USER" para arrendatarios
}