package com.rentify.api_rentify.repository;

import com.rentify.api_rentify.model.DetalleRenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRentaRepository extends JpaRepository<DetalleRenta, Long> {
}