-- =====================================================
-- CoffeeLogApp - Script de Base de Datos
-- Base de datos: SQL Server
-- =====================================================

-- =====================================================
-- 1. Crear la base de datos
-- =====================================================

USE master;
GO

-- Eliminar la base de datos si existe (opcional - comentar si no se quiere eliminar)
-- IF EXISTS (SELECT name FROM sys.databases WHERE name = 'CoffeeLogDB')
-- BEGIN
--     DROP DATABASE CoffeeLogDB;
--     PRINT 'Base de datos CoffeeLogDB eliminada';
-- END
-- GO

-- Crear la base de datos
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'CoffeeLogDB')
BEGIN
    CREATE DATABASE CoffeeLogDB;
    PRINT 'Base de datos CoffeeLogDB creada exitosamente';
END
ELSE
BEGIN
    PRINT 'La base de datos CoffeeLogDB ya existe';
END
GO

-- =====================================================
-- 2. Usar la base de datos
-- =====================================================

USE CoffeeLogDB;
GO

-- =====================================================
-- 3. Crear la tabla Cafes
-- =====================================================

-- Eliminar la tabla si existe
IF OBJECT_ID('dbo.Cafes', 'U') IS NOT NULL
BEGIN
DROP TABLE dbo.Cafes;
PRINT 'Tabla Cafes eliminada (existía previamente)';
END
GO

-- Crear la tabla
CREATE TABLE dbo.Cafes (
                           id INT IDENTITY(1,1) PRIMARY KEY,
                           nombre NVARCHAR(100) NOT NULL,
                           pais_origen NVARCHAR(50) NULL,
                           metodo_preparacion NVARCHAR(50) NULL,
                           nivel_tueste INT NULL,
                           puntuacion INT NULL,
                           notas_sabor NVARCHAR(500) NULL,
                           es_favorito BIT DEFAULT 0,
                           fecha_creacion DATETIME DEFAULT GETDATE(),
                           fecha_actualizacion DATETIME DEFAULT GETDATE()
);
GO

PRINT 'Tabla Cafes creada exitosamente';
GO

-- =====================================================
-- 4. Agregar restricciones (constraints)
-- =====================================================

-- Restricción para nivel de tueste (1-5)
ALTER TABLE dbo.Cafes
    ADD CONSTRAINT CHK_nivel_tueste CHECK (nivel_tueste BETWEEN 1 AND 5);
PRINT 'Restricción CHK_nivel_tueste agregada';
GO

-- Restricción para puntuación (1-10)
ALTER TABLE dbo.Cafes
    ADD CONSTRAINT CHK_puntuacion CHECK (puntuacion BETWEEN 1 AND 10);
PRINT 'Restricción CHK_puntuacion agregada';
GO

-- =====================================================
-- 5. Crear índices para mejorar el rendimiento
-- =====================================================

-- Índice para búsqueda por favoritos
CREATE INDEX IX_Cafes_EsFavorito ON dbo.Cafes(es_favorito);
PRINT 'Índice IX_Cafes_EsFavorito creado';
GO

-- Índice para búsqueda por puntuación
CREATE INDEX IX_Cafes_Puntuacion ON dbo.Cafes(puntuacion);
PRINT 'Índice IX_Cafes_Puntuacion creado';
GO

-- Índice para búsqueda por nombre
CREATE INDEX IX_Cafes_Nombre ON dbo.Cafes(nombre);
PRINT 'Índice IX_Cafes_Nombre creado';
GO

-- =====================================================
-- 6. Crear trigger para actualizar fecha_actualizacion
-- =====================================================

CREATE TRIGGER trg_Cafes_Update
    ON dbo.Cafes
    AFTER UPDATE
              AS
BEGIN
    SET NOCOUNT ON;

UPDATE dbo.Cafes
SET fecha_actualizacion = GETDATE()
    FROM dbo.Cafes c
    INNER JOIN inserted i ON c.id = i.id;

PRINT 'Trigger trg_Cafes_Update ejecutado';
END
GO

PRINT 'Trigger trg_Cafes_Update creado';
GO

-- =====================================================
-- 7. Insertar datos de ejemplo
-- =====================================================

INSERT INTO dbo.Cafes (nombre, pais_origen, metodo_preparacion, nivel_tueste, puntuacion, notas_sabor, es_favorito)
VALUES
    ('Colombia Supremo', 'COL', 'V60', 3, 8, 'Notas cítricas, caramelo y chocolate', 1),
    ('Etiopía Yirgacheffe', 'ETH', 'Aeropress', 2, 9, 'Jazmín, bergamota y sabor afrutado', 1),
    ('Brasil Santos', 'BRA', 'Espresso', 4, 7, 'Nueces, chocolate amargo y cuerpo pesado', 0),
    ('Guatemala Antigua', 'GTM', 'Chemex', 3, 8, 'Cacao, especias y acidez brillante', 1),
    ('México Chiapas', 'MEX', 'Prensa Francesa', 3, 7, 'Chocolate, nuez y cuerpo sedoso', 0),
    ('Costa Rica Tarrazú', 'CRI', 'V60', 2, 9, 'Manzana verde, miel y cuerpo balanceado', 1),
    ('Perú Chanchamayo', 'PER', 'V60', 3, 7, 'Cítricos, caramelo y acidez media', 0),
    ('Nicaragua Jinotega', 'NIC', 'Chemex', 3, 8, 'Frutos secos, chocolate y dulce', 0),
    ('El Salvador Santa Ana', 'SLV', 'Espresso', 4, 7, 'Caramelo, naranja y cuerpo cremoso', 0),
    ('Honduras Marcala', 'HND', 'Aeropress', 3, 8, 'Frutas tropicales, chocolate y dulce', 1),
    ('Colombia Geisha', 'COL', 'V60', 2, 10, 'Floral, té de jazmín y sabor complejo', 1),
    ('Etiopía Sidama', 'ETH', 'Aeropress', 3, 8, 'Frutos rojos, vinoso y dulce', 1),
    ('Brasil Cerrado', 'BRA', 'Espresso', 4, 6, 'Frutos secos, chocolate y bajo brillo', 0),
    ('Kenia AA', 'KEN', 'V60', 2, 9, 'Frutos rojos, vino y acidez brillante', 1),
    ('Sumatra Mandheling', 'IDN', 'Prensa Francesa', 4, 8, 'Hierbas, especias y cuerpo pesado', 0);
GO

PRINT 'Datos de ejemplo insertados exitosamente';
GO

-- =====================================================
-- 8. Verificar los datos insertados
-- =====================================================

SELECT * FROM dbo.Cafes ORDER BY id;
GO

-- =====================================================
-- 9. Estadísticas de la tabla
-- =====================================================

SELECT
    COUNT(*) AS TotalCafes,
    SUM(CASE WHEN es_favorito = 1 THEN 1 ELSE 0 END) AS TotalFavoritos,
    CAST(AVG(puntuacion) AS DECIMAL(4,2)) AS PromedioPuntuacion,
    MIN(puntuacion) AS MinPuntuacion,
    MAX(puntuacion) AS MaxPuntuacion
FROM dbo.Cafes;
GO

-- =====================================================
-- 10. Consultas útiles de ejemplo
-- =====================================================

-- Obtener todos los cafés favoritos
-- SELECT * FROM dbo.Cafes WHERE es_favorito = 1 ORDER BY puntuacion DESC;
GO

-- Obtener el promedio de puntuación
-- SELECT CAST(AVG(puntuacion) AS DECIMAL(4,2)) as Promedio FROM dbo.Cafes;
GO

-- Obtener cafés por país de origen
-- SELECT
--     pais_origen,
--     COUNT(*) as Cantidad,
--     CAST(AVG(puntuacion) AS DECIMAL(4,2)) as Promedio
-- FROM dbo.Cafes
-- GROUP BY pais_origen
-- ORDER BY Promedio DESC;
GO

-- Obtener cafés por método de preparación
-- SELECT
--     metodo_preparacion,
--     COUNT(*) as Cantidad,
--     CAST(AVG(puntuacion) AS DECIMAL(4,2)) as Promedio
-- FROM dbo.Cafes
-- GROUP BY metodo_preparacion
-- ORDER BY Promedio DESC;
GO

-- Obtener cafés con puntuación mayor a 8
-- SELECT nombre, pais_origen, puntuacion FROM dbo.Cafes WHERE puntuacion >= 8 ORDER BY puntuacion DESC;
GO

-- =====================================================
-- 11. Resumen final
-- =====================================================

PRINT '=====================================================';
PRINT 'Script ejecutado exitosamente';
PRINT '=====================================================';
PRINT 'Base de datos: CoffeeLogDB';
PRINT 'Tabla: Cafes';
PRINT 'Registros insertados: 15';
PRINT '=====================================================';
GO