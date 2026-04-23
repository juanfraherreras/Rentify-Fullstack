package com.rentify.api_rentify.service;

import com.rentify.api_rentify.model.Renta;
import com.rentify.api_rentify.model.DetalleRenta;
import com.rentify.api_rentify.model.EstadoItem;
import com.rentify.api_rentify.repository.RentaRepository;
import com.rentify.api_rentify.repository.DetalleRentaRepository;
// ... el resto de imports
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentaService {

    @Autowired
    private RentaRepository rentaRepository;

    @Autowired
    private DetalleRentaRepository detalleRepository;

    @Transactional
    public Renta guardarRenta(Renta renta) {
        renta.setFechaRenta(LocalDateTime.now());
        
        // Aseguramos la relación bidireccional para que se guarden los detalles
        if (renta.getDetalles() != null) {
            renta.getDetalles().forEach(detalle -> detalle.setRenta(renta));
        }
        
        return rentaRepository.save(renta);
    }

    public List<Renta> listarTodas() {
        return rentaRepository.findAll();
    }

    @Transactional
    public DetalleRenta registrarDevolucion(Long idDetalle, String comentario) {
        DetalleRenta detalle = detalleRepository.findById(idDetalle)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el detalle con ID: " + idDetalle));

        detalle.setFechaEntregaReal(LocalDateTime.now());
        detalle.setEstadoItem(EstadoItem.DEVUELTO);// O usa un Enum si lo definiste
        
        if (comentario != null) {
            detalle.setComentarios(comentario);
        }

        return detalleRepository.save(detalle);
    }
}