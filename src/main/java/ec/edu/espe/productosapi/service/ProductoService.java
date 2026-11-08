package ec.edu.espe.productosapi.service;

import ec.edu.espe.productosapi.exception.ProductoInvalidoException;
import ec.edu.espe.productosapi.exception.ProductoNoEncontradoException;
import ec.edu.espe.productosapi.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Capa de servicio: contiene las reglas de negocio y gestiona
 * los productos almacenados en una coleccion en memoria.
 * La anotacion @Service registra esta clase como un bean administrado por Spring.
 */
@Service
public class ProductoService {

    private final List<Producto> productos = new CopyOnWriteArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public ProductoService() {
        // Datos iniciales de ejemplo
        productos.add(new Producto(secuencia.incrementAndGet(), "Laptop Lenovo IdeaPad", 650.00, 10));
        productos.add(new Producto(secuencia.incrementAndGet(), "Mouse inalambrico Logitech", 18.50, 40));
        productos.add(new Producto(secuencia.incrementAndGet(), "Teclado mecanico Redragon", 45.99, 25));
    }

    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto registrar(Producto producto) {
        validar(producto);
        producto.setId(secuencia.incrementAndGet()); // el id lo asigna el sistema
        producto.setNombre(producto.getNombre().trim());
        productos.add(producto);
        return producto;
    }

    public void eliminar(Long id) {
        Producto existente = buscarPorId(id); // lanza ProductoNoEncontradoException si no existe
        productos.remove(existente);
    }

    // ---------- Reglas de negocio ----------
    private void validar(Producto producto) {
        if (producto == null) {
            throw new ProductoInvalidoException("Debe enviar los datos del producto");
        }
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new ProductoInvalidoException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio del producto debe ser mayor a cero");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new ProductoInvalidoException("El stock del producto no puede ser negativo");
        }
    }
}
