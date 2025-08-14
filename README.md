
# Greater Events - Microservices Monorepo

Servicios incluidos:
- discovery (Eureka, 8761)
- gateway (Spring Cloud Gateway, 8080)
- auth-service (admins + JWT, 8084)
- artist-service (CRUD artista, 8081)
- event-service (CRUD evento + reglas de estado, 8082)

## Ejecutar con Docker
```bash
docker compose up --build
```
Luego:
- POST http://localhost:8080/admin/auth  -> { "username":"admin", "password":"admin" }
- Usar el token en `Authorization: Bearer <token>` para endpoints `/admin/**`.
- Endpoints públicos disponibles en `/public/**`.

> Nota: Los servicios usan Postgres individuales.
```
