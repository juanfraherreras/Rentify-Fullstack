package com.rentify.api_rentify.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rentify.api_rentify.model.Carrito;
import com.rentify.api_rentify.model.DetalleCarrito;
import com.rentify.api_rentify.service.CarritoService;
    

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:4200")
public class CarritoController {
    @Autowired private CarritoService carritoService;

    @GetMapping("/usuario/{id}")
    public Carrito verCarrito(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorUsuario(id);
    }

    @PostMapping("/agregar")
    public DetalleCarrito agregar(@RequestBody DetalleCarrito detalle) {
        return carritoService.agregarProducto(detalle);
    }
}