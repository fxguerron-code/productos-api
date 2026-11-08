package ec.edu.espe.productosapi.controller;

import ec.edu.espe.productosapi.model.Producto;
import ec.edu.espe.productosapi.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Capa de controlador: recibe las solicitudes HTTP y devuelve las respuestas.
 * No contiene reglas de negocio; delega todo el trabajo al ProductoService.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    // Inyeccion de dependencias por constructor: Spring entrega el bean del servicio.
    // El Controller NUNCA usa "new ProductoService()".
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos -> 200 OK
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // GET /api/productos/{id} -> 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    // POST /api/productos -> 201 Created o 400 Bad Request
    @PostMapping
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto) {
        Producto creado = productoService.registrar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // DELETE /api/productos/{id} -> 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
