-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS swing_java;

-- Usar la base de datos
USE swing_java;

-- Eliminar tabla si existe
DROP TABLE IF EXISTS users;

-- Crear tabla de usuarios
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos de prueba
INSERT INTO users (username, password, email) VALUES
('admin', '123456', 'admin@example.com'),
('usuario1', 'clave123', 'usuario1@example.com'),
('usuario2', 'clave456', 'usuario2@example.com');

-- Crear índices para búsquedas frecuentes
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

-- Mostrar estructura de la tabla
DESCRIBE users;

-- Mostrar datos insertados
SELECT * FROM users;
