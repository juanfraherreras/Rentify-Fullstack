package com.rentify.api_rentify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentify.api_rentify.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}