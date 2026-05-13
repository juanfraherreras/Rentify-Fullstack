package com.rentify.api_rentify.repository;

import com.rentify.api_rentify.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí ya tenemos métodos automáticos como:
    // save() -> para guardar un producto de Rentify
    // findAll() -> para ver todos los productos
    // findById() -> para buscar uno solo

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.stockActual = p.stockActual - :cantidad " +
           "WHERE p.id = :id AND p.stockActual >= :cantidad")
    int disminuirStock(Long id, int cantidad);
}