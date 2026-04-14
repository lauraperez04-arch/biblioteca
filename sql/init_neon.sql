-- =========================================
-- MODELO ENTIDAD-RELACION (texto)
-- Entidad 1: usuarios (sin dependencia)
-- Entidad 2: libros (sin dependencia)
-- Relacion: en este avance no hay llaves foraneas
-- =========================================

DROP TABLE IF EXISTS libros;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    anio_publicacion INTEGER NOT NULL CHECK (anio_publicacion > 0),
    isbn VARCHAR(20) NOT NULL UNIQUE
);

-- Usuario de prueba para login (password: admin123)
-- Hash generado con BCryptPasswordEncoder de Spring (compatible con Java).
INSERT INTO usuarios (nombre, email, password_hash, activo)
VALUES (
    'Administrador',
    'admin@biblioteca.com',
    '$2a$10$GQI0a9bHkBtIZ5BmFVnx7..cQ5No.Wfxt.tF55nRu0gpNXUbEohSa',
    TRUE
);

INSERT INTO libros (titulo, autor, anio_publicacion, isbn)
VALUES
('Cien anos de soledad', 'Gabriel Garcia Marquez', 1967, 'ISBN-9780307474728'),
('Don Quijote de la Mancha', 'Miguel de Cervantes', 1605, 'ISBN-9788420412146');
