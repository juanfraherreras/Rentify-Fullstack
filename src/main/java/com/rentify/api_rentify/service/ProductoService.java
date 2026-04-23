package com.rentify.api_rentify.service;

import com.rentify.api_rentify.model.Producto;
import com.rentify.api_rentify.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Lógica core de Rentify: Reducir stock al rentar
    public Producto rentarProducto(Long id) {
        Producto p = obtenerPorId(id);
        if (p != null && p.getStockActual() > 0) {
            p.setStockActual(p.getStockActual() - 1);
            return productoRepository.save(p);
        }
        return null; // O podrías lanzar una excepción si no hay stock
    }

    // Lógica para cuando devuelven el producto
    public Producto devolverProducto(Long id) {
        Producto p = obtenerPorId(id);
        if (p != null && p.getStockActual() < p.getStockMaximo()) {
            p.setStockActual(p.getStockActual() + 1);
            return productoRepository.save(p);
        }
        return p;
    }
}