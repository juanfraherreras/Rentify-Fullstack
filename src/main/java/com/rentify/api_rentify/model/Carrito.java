package com.rentify.api_rentify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double total;
    private Integer cantidadArticulos;

    @OneToOne // Un usuario suele tener un solo carrito activo
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}