

package com.rentify.api_rentify.controller;

import com.rentify.api_rentify.model.Carrito;
import com.rentify.api_rentify.model.DetalleCarrito;
import com.rentify.api_rentify.repository.DetalleCarritoRepository;
import com.rentify.api_rentify.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-carrito")
public class DetalleCarritoController {

    @Autowired 
    private DetalleCarritoRepository detalleRepository;

    @Autowired
    private CarritoService carritoService;

    // Obtener todos los productos de un carrito específico
    @GetMapping("/carrito/{usuarioId}")
    public List<DetalleCarrito> listarPorCarrito(@PathVariable Long usuarioId) {
        // Angular está enviando el usuarioId, así que buscamos el carrito real de ese usuario
        Carrito carritoReal = carritoService.obtenerCarritoPorUsuario(usuarioId);
        if (carritoReal != null) {
            return detalleRepository.findByCarritoId(carritoReal.getId());
        }
        return List.of();
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/{id}")
    public void eliminarDetalle(@PathVariable Long id) {
        detalleRepository.deleteById(id);
    }

    // Método para agregar un producto al carrito (lo que llama el botón de Angular)
    @PostMapping("/agregar")
    public DetalleCarrito agregarAlCarrito(@RequestBody DetalleCarrito nuevoDetalle) {
        // Validación de seguridad para evitar NullPointerException
        if (nuevoDetalle.getCarrito() == null || nuevoDetalle.getCarrito().getId() == null) {
            throw new RuntimeException("El JSON no incluye la información del carrito/usuario.");
        }

        // 1. Extraemos el ID que mandó Angular (que en realidad es el id del usuario)
        Long usuarioId = nuevoDetalle.getCarrito().getId();
        
        // 2. Buscamos el Carrito REAL que le pertenece a ese usuario
        Carrito carritoReal = carritoService.obtenerCarritoPorUsuario(usuarioId);
        
        if (carritoReal == null) {
            throw new RuntimeException("El usuario no tiene un carrito asignado en la BD.");
        }
        
        // 3. Le asignamos el carrito correcto al detalle
        nuevoDetalle.setCarrito(carritoReal);
        
        // 4. Guardamos usando CarritoService para que también actualice los totales
        return carritoService.agregarProducto(nuevoDetalle);
    }
}