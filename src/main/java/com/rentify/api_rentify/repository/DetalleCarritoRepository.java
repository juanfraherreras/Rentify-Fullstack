package com.rentify.api_rentify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentify.api_rentify.model.DetalleCarrito;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {
    List<DetalleCarrito> findByCarritoId(Long carritoId);
}