package com.rentify.api_rentify.repository;

import com.rentify.api_rentify.model.Renta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
}