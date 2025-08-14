# Greater Events Microservices

## 📄 Descripción

Proyecto de microservicios para gestión de eventos, artistas y usuarios/admins.  
Incluye:

- **User Service**: Gestión de usuarios, login, registro, favoritos, seguimiento de artistas y notificaciones.
- **Event Service**: Gestión de eventos (crear, actualizar, cancelar, confirmar, agregar artistas).
- **Artist Service**: Gestión de artistas (crear, actualizar, eliminar) y consulta pública.

**Autenticación**:

- **Admins**: JWT vía `/admin/auth`.
- **Usuarios**: JWT vía `/user/login`.

---

## ⚙️ Requisitos

- Java 22
- Spring Boot 3.4.x
- PostgreSQL
- Maven
- Postman (opcional para probar endpoints)

---

.

🔑 Autenticación
Admin

POST /admin/auth

`{
"username": "admin",
"password": "admin123"
}
`

Respuesta:

`{
"token": "<JWT_TOKEN>"
}`


Header para endpoints protegidos:

`Authorization: Bearer <JWT_TOKEN>
`
Usuario

POST /user/login

`{
"username": "facu",
"password": "user123"
}`


Respuesta:

`{
"token": "<JWT_TOKEN>"
}`


Header para endpoints protegidos:

`Authorization: Bearer <JWT_TOKEN>
`

📌 Endpoints
User Service
Admin

POST /admin/auth → Autenticación de admin

### Usuario

POST /user → Crear usuario

POST /user/login → Login

POST /user/register → Registro

POST /user/artists/{id}/follow → Seguir artista

DELETE /user/artists/{id}/unfollow → Dejar de seguir artista

GET /user/artists/following → Obtener artistas seguidos

POST /user/events/{id}/favorite → Marcar evento favorito

DELETE /user/events/{id}/unfavorite → Quitar evento favorito

GET /user/events/favorites → Eventos favoritos (EventDTO)

### Event Service

**Admin**

GET /admin/events → Listar eventos (opcional: ?state=CONFIRMED)

GET /admin/events/{id} → Obtener evento por ID

POST /admin/events → Crear evento

PUT /admin/events/{id} → Actualizar evento

DELETE /admin/events/{id} → Eliminar evento

POST /admin/events/{id}/artists → Agregar artista

DELETE /admin/events/{id}/artists/{artistId} → Remover artista

PUT /admin/events/{id}/confirmed → Confirmar evento

PUT /admin/events/{id}/rescheduled → Reprogramar evento ({"start_date":"YYYY-MM-DD"})

PUT /admin/events/{id}/canceled → Cancelar evento

**Público**

GET /public/events → Listar eventos futuros

GET /public/events/{eventId} → Detalle de evento futuro

GET /public/artists/{artistId}/events → Eventos futuros de un artista

### Artist Service

**Admin**

GET /admin/artists → Listar artistas (opcional: ?genre=ROCK)

GET /admin/artists/{id} → Obtener artista

POST /admin/artists → Crear artista

PUT /admin/artists/{id} → Actualizar artista

DELETE /admin/artists/{id} → Eliminar artista

**Público**

GET /public/artists → Listar artistas

GET /public/artists/{id} → Obtener artista