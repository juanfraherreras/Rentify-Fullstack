package com.rentify.api_rentify.service;

import com.rentify.api_rentify.model.DetalleCarrito;
import com.rentify.api_rentify.repository.DetalleCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleCarritoService {

    @Autowired
    private DetalleCarritoRepository detalleRepository;

    // Listar todos los artículos de un carrito específico
    public List<DetalleCarrito> obtenerDetallesPorCarrito(Long carritoId) {
        return detalleRepository.findByCarritoId(carritoId);
    }

    // Guardar un nuevo item en el carrito
    public DetalleCarrito guardarDetalle(DetalleCarrito detalle) {
        return detalleRepository.save(detalle);
    }

    // Eliminar un item (por ejemplo, si el usuario quita un taladro del coche)
    public void eliminarDetalle(Long id) {
        detalleRepository.deleteById(id);
    }

    // Buscar un detalle específico por ID
    public DetalleCarrito buscarPorId(Long id) {
        return detalleRepository.findById(id).orElse(null);
    }
}