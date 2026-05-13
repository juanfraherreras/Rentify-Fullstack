package com.rentify.api_rentify.controller;

import com.rentify.api_rentify.model.Producto;
import com.rentify.api_rentify.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    // Endpoint para actualizar un producto existente desde el Panel de Admin
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        // Le asignamos el ID de la URL al producto para que Spring Data JPA haga un UPDATE en lugar de un INSERT
        // y así evitar que se cree un producto nuevo.
        productoActualizado.setId(id);
        return ResponseEntity.ok(productoService.guardar(productoActualizado));
    }

    // Endpoint para rentar: PUT http://localhost:8080/api/productos/{id}/rentar
    @PutMapping("/{id}/rentar")
    public Producto rentar(@PathVariable Long id) {
        return productoService.rentarProducto(id);
    }

    // Endpoint para devolver: PUT http://localhost:8080/api/productos/{id}/devolver
    @PutMapping("/{id}/devolver")
    public Producto devolver(@PathVariable Long id) {
        return productoService.devolverProducto(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/disminuir-stock")
    public ResponseEntity<?> disminuirStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            productoService.actualizarStock(id, cantidad);
            return ResponseEntity.ok("Stock actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}