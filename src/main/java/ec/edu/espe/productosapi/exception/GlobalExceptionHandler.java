package ec.edu.espe.productosapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Mecanismo centralizado de manejo de excepciones.
 * Intercepta las excepciones lanzadas por cualquier controlador y las
 * transforma en una respuesta JSON con el mensaje y el codigo HTTP adecuado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - El producto solicitado no existe
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(ProductoNoEncontradoException ex,
                                                             HttpServletRequest request) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // 400 - Los datos incumplen una regla de negocio
    @ExceptionHandler(ProductoInvalidoException.class)
    public ResponseEntity<ErrorResponse> manejarInvalido(ProductoInvalidoException ex,
                                                         HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // 400 - El cuerpo JSON esta mal formado o tiene tipos incorrectos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex,
                                                             HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud no es un JSON valido o tiene tipos de datos incorrectos",
                request);
    }

    // 400 - El id de la ruta no es numerico (ej: /api/productos/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex,
                                                               HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST,
                "El parametro '" + ex.getName() + "' debe ser un numero entero",
                request);
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje,
                                                    HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(status.value(), status.getReasonPhrase(),
                mensaje, request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
