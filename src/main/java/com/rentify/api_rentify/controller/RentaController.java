package com.rentify.api_rentify.controller; // Asegúrate que termine en .controller

import com.rentify.api_rentify.model.Renta;         // Antes decía com.rentify.model
import com.rentify.api_rentify.model.DetalleRenta;  // Antes decía com.rentify.model
import com.rentify.api_rentify.service.RentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentas")
@CrossOrigin(origins = "*") 
public class RentaController {

    @Autowired
    private RentaService rentaService;

    @GetMapping
    public List<Renta> obtenerTodas() {
        return rentaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Renta> crear(@RequestBody Renta renta) {
        try {
            Renta nuevaRenta = rentaService.guardarRenta(renta);
            return new ResponseEntity<>(nuevaRenta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para devolver una sola computadora del grupo rentado
    @PatchMapping("/detalle/{id}/devolver")
    public ResponseEntity<DetalleRenta> devolverProducto(
            @PathVariable Long id,
            @RequestParam(required = false) String nota) {
        try {
            DetalleRenta actualizado = rentaService.registrarDevolucion(id, nota);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}