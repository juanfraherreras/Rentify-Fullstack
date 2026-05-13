

package com.rentify.api_rentify.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rentify.api_rentify.model.Carrito;
import com.rentify.api_rentify.model.DetalleCarrito;
import com.rentify.api_rentify.model.Producto;
import com.rentify.api_rentify.repository.CarritoRepository;
import com.rentify.api_rentify.repository.DetalleCarritoRepository;
import com.rentify.api_rentify.repository.ProductoRepository;

@Service
public class CarritoService {
    @Autowired private CarritoRepository carritoRepository;
    @Autowired private DetalleCarritoRepository detalleRepository;
    @Autowired private ProductoRepository productoRepository;

    public Carrito obtenerCarritoPorUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).orElse(null);
    }

    @Transactional
    public DetalleCarrito agregarProducto(DetalleCarrito detalle) {
        // Asegurarnos de que el producto viene en la petición
        if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
            throw new RuntimeException("El detalle no contiene un producto válido.");
        }
        
        // Obtener el producto REAL de la BD para tener su precio y stock
        Producto productoReal = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la BD"));
        detalle.setProducto(productoReal);

        // 1. Guardar el detalle
        DetalleCarrito nuevoDetalle = detalleRepository.save(detalle);
        
        // 2. Actualizar totales del carrito
        Carrito carrito = detalle.getCarrito();
        List<DetalleCarrito> detalles = detalleRepository.findByCarritoId(carrito.getId());
        
        carrito.setCantidadArticulos(detalles.size());
        carrito.setTotal(detalles.stream().mapToDouble(d -> d.getProducto().getPrecio()).sum());
        
        carritoRepository.save(carrito);
        return nuevoDetalle;
    }
}