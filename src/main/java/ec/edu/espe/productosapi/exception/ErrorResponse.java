package ec.edu.espe.productosapi.exception;

import java.time.LocalDateTime;

/**
 * Estructura uniforme de las respuestas de error de la API.
 * Incluye el mensaje del error y el codigo de estado HTTP.
 */
public class ErrorResponse {

    private final LocalDateTime fecha;
    private final int status;
    private final String error;
    private final String mensaje;
    private final String ruta;

    public ErrorResponse(int status, String error, String mensaje, String ruta) {
        this.fecha = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.ruta = ruta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getRuta() {
        return ruta;
    }
}
