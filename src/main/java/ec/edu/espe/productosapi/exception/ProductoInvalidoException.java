package ec.edu.espe.productosapi.exception;

/**
 * Se lanza cuando los datos de un producto incumplen una regla de negocio
 * (por ejemplo: precio menor o igual a cero o stock negativo).
 */
public class ProductoInvalidoException extends RuntimeException {

    public ProductoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
