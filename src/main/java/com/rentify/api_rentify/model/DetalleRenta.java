package com.rentify.api_rentify.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detalle_renta")
@Getter
@Setter
public class DetalleRenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_renta")
    private Renta renta;

    private Long idProducto; 
    private Double precioUnitario;
    private LocalDateTime fechaEntregaEsperada;
    private LocalDateTime fechaEntregaReal; 
    @Enumerated(EnumType.STRING)
    private EstadoItem estadoItem; 
    private String comentarios;

    // Getters y Setters
}
