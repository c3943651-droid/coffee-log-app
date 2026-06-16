#  CoffeeLogApp - Documento de Requerimientos

## 1. Descripción General

CoffeeLogApp es una aplicación de escritorio desarrollada en Java 21 con Swing, diseñada para que entusiastas del café registren y gestionen sus experiencias de cata.

## 2. Objetivos del Sistema

- Permitir el registro completo de experiencias de cata de café
- Almacenar información sensorial (método, tueste, notas, puntuación)
- Gestionar cafés favoritos
- Exportar datos a CSV
- Proveer búsqueda en tiempo real

## 3. Requerimientos Funcionales (RF)

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| RF01 | El sistema debe permitir registrar un nuevo café con nombre, origen, método, nivel de tueste (1-5), puntuación (1-10), notas de sabor y marcador de favorito | Alta |
| RF02 | El sistema debe permitir editar un café existente | Alta |
| RF03 | El sistema debe permitir eliminar un café existente | Alta |
| RF04 | El sistema debe listar todos los cafés en una tabla con capacidad de búsqueda en tiempo real | Alta |
| RF05 | El sistema debe calcular y mostrar automáticamente el promedio de puntuaciones de todos los cafés | Media |
| RF06 | El sistema debe exportar a CSV exclusivamente los registros marcados como favoritos | Media |
| RF07 | El sistema debe requerir autenticación (usuario/contraseña) para acceder | Media |
| RF08 | El sistema debe validar campos obligatorios (nombre del café no puede estar vacío) | Alta |

## 4. Requerimientos No Funcionales (RNF)

| ID | Requerimiento | Descripción |
|----|--------------|-------------|
| RNF01 | Usabilidad | Interfaz intuitiva con iconos y colores agradables |
| RNF02 | Rendimiento | La búsqueda debe ser instantánea (menos de 1 segundo) |
| RNF03 | Portabilidad | La aplicación debe ejecutarse en Windows, Linux y macOS |
| RNF04 | Mantenibilidad | Código organizado en capas (UI, DAO, Model) |
| RNF05 | Escalabilidad | Arquitectura preparada para agregar nuevas funcionalidades |

## 5. Requerimientos Técnicos

| Componente | Tecnología |
|-----------|-----------|
| Lenguaje | Java 21 |
| Interfaz Gráfica | Swing + FlatLaf |
| Base de Datos | SQL Server |
| Gestor de Dependencias | Maven |
| Pruebas | JUnit 5 |
| Control de Versiones | Git / GitHub |

## 6. Casos de Uso Principales

### CU01 - Iniciar Sesión
1. Usuario ingresa credenciales (admin/1234)
2. Sistema valida credenciales
3. Si son correctas, muestra ventana principal

### CU02 - Registrar Café
1. Usuario abre formulario de registro
2. Completa los campos requeridos
3. Sistema guarda en base de datos

### CU03 - Listar Cafés
1. Usuario abre listado
2. Sistema muestra todos los cafés en tabla
3. Usuario puede buscar en tiempo real

### CU04 - Exportar Favoritos
1. Usuario selecciona exportar
2. Sistema filtra cafés favoritos
3. Sistema genera archivo CSV

## 7. Matriz de Trazabilidad

| Requerimiento | Clase asociada | Método | Estado |
|--------------|---------------|--------|--------|
| RF01 | CoffeeWriteForm | guardarCafe() |  Implementado |
| RF02 | CoffeeWriteForm | guardarCafe() |  Implementado |
| RF03 | CoffeeReadingForm | eliminarCafe() | Implementado |
| RF04 | CoffeeReadingForm | filtrar() | Implementado |
| RF05 | CoffeeReadingForm | cargarDatos() |  Implementado |
| RF06 | CSVExporter | exportarFavoritos() |  Implementado |
| RF07 | LoginForm | login() |  Implementado |
| RF08 | CoffeeWriteForm | guardarCafe() |  Implementado |