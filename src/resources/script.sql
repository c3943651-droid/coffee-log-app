-- =============================================
-- CoffeeLogApp - Script de Base de Datos
-- =============================================

-- Crear la base de datos
CREATE DATABASE CoffeeLogDB;
GO

-- Usar la base de datos
USE CoffeeLogDB;
GO

-- Crear la tabla de cafés
CREATE TABLE Cafes (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       pais_origen VARCHAR(50) NOT NULL,
                       metodo_preparacion VARCHAR(50) NOT NULL,
                       nivel_tueste INT NOT NULL CHECK (nivel_tueste BETWEEN 1 AND 5),
                       puntuacion INT NOT NULL CHECK (puntuacion BETWEEN 1 AND 10),
                       notas_sabor VARCHAR(500),
                       es_favorito BIT DEFAULT 0,
                       fecha_registro DATETIME DEFAULT GETDATE()
);
GO



