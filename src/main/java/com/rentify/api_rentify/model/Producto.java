package com.rentify.api_rentify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer stockMaximo; // Capacidad total
    private Integer stockActual; // Disponibilidad real para renta
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}