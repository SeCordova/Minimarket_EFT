# MiniMarket Plus - Backend EFT

API REST desarrollada con Spring Boot 3.4, Java 17, Spring Data JPA, Spring Security, JWT, H2, OpenAPI/Swagger y HATEOAS.

## Ejecución
1. Instalar Java 17 y Maven 3.9+.
2. Ejecutar `mvn clean test`.
3. Ejecutar `mvn spring-boot:run`.
4. Abrir Swagger UI en `http://localhost:8080/swagger-ui.html`.

## Usuarios de prueba
- `admin / Admin123!` - ROLE_ADMIN
- `cajero / Cajero123!` - ROLE_CAJERO
- `cliente / Cliente123!` - ROLE_CLIENTE

## Autenticación
Enviar `POST /api/auth/login` con JSON:
```json
{"username":"admin","password":"Admin123!"}
```
Copiar el token y usar `Authorization: Bearer <token>`.

## Control de acceso
- Productos/categorías: CLIENTE, CAJERO y ADMIN.
- Ventas: CAJERO y ADMIN.
- Inventario/usuarios: solo ADMIN.

## Documentación
- OpenAPI JSON: `/v3/api-docs`
- Swagger UI: `/swagger-ui.html`
- Las respuestas de productos contienen enlaces `_links` HATEOAS.
