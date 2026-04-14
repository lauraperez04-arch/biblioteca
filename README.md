# Biblioteca API (Java / Spring Boot) — proyecto de estudio

API **sin front**: tres capas, **Swagger**, **login JWT**, CRUD de `usuarios` y `libros`. Base **Neon** (PostgreSQL). SQL de tablas en `sql/init_neon.sql`.

## Inicio rapido (clonar y correr)

1. Clona el repositorio.
2. Abre una terminal en la carpeta del proyecto.
3. Ejecuta:

```text
.\mvnw.cmd spring-boot:run
```

4. Abre Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**Comprueba que corres esta version:** en el navegador abre [http://localhost:8080/](http://localhost:8080/) y debe aparecer `"build":"sin-spring-security-web-v2"`. Si no sale asi, la app vieja sigue en el puerto 8080 (cierra otros `spring-boot:run` o cambia el puerto).

**Requisito:** JDK **17** o superior (`java -version`). No hace falta instalar Maven (viene `mvnw.cmd`).

### Seguridad (estudio)

No se usa `spring-boot-starter-security`: no hay filtros que devuelvan 403. El login con JWT y BCrypt sigue en el codigo.

### Login devuelve 500 o 401 con admin de prueba

El hash BCrypt del SQL antiguo no era valido para Java. **En Neon** ejecuta `sql/fix_password_admin123.sql` (actualiza la contrasena del admin) o vuelve a correr `sql/init_neon.sql` en una base vacia.

### Configuracion

Todo esta en **`src/main/resources/application.properties`**: URL de Neon, usuario, contraseña y JWT. Es **a proposito** un solo archivo para que el equipo descargue y arranque sin pasos extra. Si Neon te da una clave nueva, edita solo ese archivo.

## Swagger y login

1. En Neon, ejecuta `sql/init_neon.sql` si aun no tienes tablas y datos de prueba.
2. **CRUD de `/usuarios` y `/libros`:** en este proyecto de estudio **no hace falta token**; puedes probarlos directo en Swagger.
3. **`POST /auth/login`:** sirve para practicar JWT (recibes `access_token`). Si mas adelante proteges rutas, usarias ese token en **Authorize** (`Bearer ...`).

## Estructura del codigo

- `controller/`: HTTP.
- `service/`: reglas de negocio.
- `repository/`: JPA.
- `entity/`, `dto/`, `config/`, `security/`, `exception/`.

## Endpoints

- `GET /` — salud.
- `POST /auth/login`
- CRUD `/usuarios` y `/libros`
