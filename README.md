# API REST de Productos — Spring Boot

Taller individual · Programación Avanzada · NRC 36759 · ESPE
Autor: Francisco Xavier Guerrón Llivichuzca

API REST para la gestión básica de productos de una tienda. Aplica **Inversión de Control**,
**inyección de dependencias por constructor** y **manejo centralizado de excepciones**.
No usa base de datos: los productos se guardan en una colección en memoria.

## Tecnologías
- Java 17
- Spring Boot 4.1.2 (proyecto generado con Spring Initializr)
- Spring Web (única dependencia)
- Maven Wrapper

## Estructura
```
src/main/java/ec/edu/espe/productosapi
├── ProductosApiApplication.java
├── controller/ProductoController.java        -> recibe y responde solicitudes HTTP
├── service/ProductoService.java              -> reglas de negocio + colección en memoria
├── model/Producto.java                       -> id, nombre, precio, stock
└── exception/
    ├── ProductoNoEncontradoException.java
    ├── ProductoInvalidoException.java
    ├── ErrorResponse.java                    -> formato JSON de los errores
    └── GlobalExceptionHandler.java           -> @RestControllerAdvice
```

## Ejecución
```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```
La API queda disponible en `http://localhost:8080/api/productos`.

## Endpoints
| Método | Ruta | Éxito | Error |
|---|---|---|---|
| GET | `/api/productos` | 200 | — |
| GET | `/api/productos/{id}` | 200 | 404 si no existe |
| POST | `/api/productos` | 201 | 400 si precio ≤ 0 o stock < 0 |
| DELETE | `/api/productos/{id}` | 204 | 404 si no existe |

Ejemplo de cuerpo para POST:
```json
{ "nombre": "Monitor Samsung 24", "precio": 159.99, "stock": 8 }
```

Ejemplo de respuesta de error:
```json
{
  "status": 404,
  "error": "Not Found",
  "mensaje": "No existe un producto con id 99",
  "ruta": "/api/productos/99",
  "fecha": "2026-11-08T00:11:20.68"
}
```

## Pruebas con Postman
Importar `postman/ProductosAPI.postman_collection.json`, que contiene las 8 pruebas del taller.
