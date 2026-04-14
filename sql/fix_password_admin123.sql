-- Si ya tenias la base creada con el hash viejo (login500), ejecuta esto en Neon:
-- password en texto claro: admin123 (hash BCrypt generado con Spring)

UPDATE usuarios
SET password_hash = '$2a$10$GQI0a9bHkBtIZ5BmFVnx7..cQ5No.Wfxt.tF55nRu0gpNXUbEohSa'
WHERE email = 'admin@biblioteca.com';
