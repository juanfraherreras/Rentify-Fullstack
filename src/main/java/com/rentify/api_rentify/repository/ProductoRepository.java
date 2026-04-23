package com.rentify.api_rentify.repository;

import com.rentify.api_rentify.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí ya tenemos métodos automáticos como:
    // save() -> para guardar un producto de Rentify
    // findAll() -> para ver todos los productos
    // findById() -> para buscar uno solo
}