package com.rentify.api_rentify.controller;

import com.rentify.api_rentify.model.Producto;
import com.rentify.api_rentify.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200") // Para que Angular no de error de CORS
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
}