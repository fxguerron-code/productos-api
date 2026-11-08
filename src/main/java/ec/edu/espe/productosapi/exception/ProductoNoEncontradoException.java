package ec.edu.espe.productosapi.exception;

/**
 * Se lanza cuando se consulta o se intenta eliminar un producto
 * cuyo identificador no existe en la coleccion en memoria.
 */
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("No existe un producto con id " + id);
    }
}
