# 🗓 CoffeeLogApp - Plan de Implementación

## Fase 1: Configuración Inicial (Día 1)
**Duración estimada: 4 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 1.1 | Configurar proyecto Maven en IntelliJ IDEA | pom.xml |
| 1.2 | Configurar dependencias (FlatLaf, JDBC, JUnit) | pom.xml actualizado |
| 1.3 | Configurar control de versiones (Git) | Repositorio local |
| 1.4 | Crear repositorio remoto en GitHub | Repositorio remoto |
| 1.5 | Definir estructura de paquetes | Estructura de carpetas |

**Completado**

---

## Fase 2: Capa de Modelo y Persistencia (Día 2)
**Duración estimada: 6 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 2.1 | Crear clase Cafe (Model) | Cafe.java |
| 2.2 | Implementar ConnectionManager (Singleton) | ConnectionManager.java |
| 2.3 | Implementar CafeDAO con operaciones CRUD | CafeDAO.java |
| 2.4 | Crear script SQL de base de datos | script.sql |
| 2.5 | Probar conexión a SQL Server | Prueba exitosa |

**Completado**

---

## Fase 3: Capa de Utilidades (Día 3)
**Duración estimada: 3 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 3.1 | Implementar CBOption para combobox | CBOption.java |
| 3.2 | Implementar CSVExporter | CSVExporter.java |
| 3.3 | Probar exportación de datos | Exportación funcional |

**Completado**

---

## Fase 4: Capa de Presentación - Login (Día 3)
**Duración estimada: 3 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 4.1 | Diseñar LoginForm | LoginForm.java |
| 4.2 | Implementar validación de credenciales | login() |
| 4.3 | Probar autenticación | Login funcional |

**Completado**

---

## Fase 5: Capa de Presentación - Main (Día 4)
**Duración estimada: 4 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 5.1 | Diseñar MainForm con menú | MainForm.java |
| 5.2 | Implementar navegación entre formularios | Menú funcional |
| 5.3 | Aplicar Look and Feel FlatLaf | Interfaz moderna |

**Completado**

---

## Fase 6: Capa de Presentación - Registro (Día 4-5)
**Duración estimada: 6 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 6.1 | Diseñar CoffeeWriteForm | CoffeeWriteForm.java |
| 6.2 | Implementar JSliders para tueste y puntuación | Sliders funcionales |
| 6.3 | Implementar JComboBox con CBOption | Combos funcionales |
| 6.4 | Implementar validaciones | Validaciones |
| 6.5 | Integrar con CafeDAO | Guardado funcional |

**Completado**

---

## Fase 7: Capa de Presentación - Listado (Día 5-6)
**Duración estimada: 6 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 7.1 | Diseñar CoffeeReadingForm | CoffeeReadingForm.java |
| 7.2 | Implementar JTable con datos | Tabla funcional |
| 7.3 | Implementar búsqueda en tiempo real | Búsqueda funcional |
| 7.4 | Implementar cálculo de promedio | Promedio mostrado |
| 7.5 | Implementar edición y eliminación | CRUD completo |

**Completado**

---

## Fase 8: Mejora Visual y Pruebas (Día 6-7)
**Duración estimada: 5 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 8.1 | Mejorar diseño con colores y gradientes | UI mejorada |
| 8.2 | Agregar iconos emoji | UI más amigable |
| 8.3 | Pruebas de integración | Todas las funcionalidades probadas |
| 8.4 | Captura de pantallas | Evidencias |

**Completado**

---

## Fase 9: Documentación y Entrega (Día 7)
**Duración estimada: 4 horas**

| Actividad | Descripción | Entregable |
|-----------|-------------|------------|
| 9.1 | Completar requirements.md | Documento de requerimientos |
| 9.2 | Completar plan_implementacion.md | Plan de implementación |
| 9.3 | Capturar evidencias de pantalla | Carpeta evidencias/ |
| 9.4 | Subir todo a GitHub | Repositorio actualizado |

**Completado**

---

##  Resumen de Tiempos

| Fase | Duración | Estado |
|------|----------|--------|
| Fase 1 - Configuración | 4 horas | ✅ |
| Fase 2 - Modelo y Persistencia | 6 horas | ✅ |
| Fase 3 - Utilidades | 3 horas | ✅ |
| Fase 4 - Login | 3 horas | ✅ |
| Fase 5 - Main | 4 horas | ✅ |
| Fase 6 - Registro | 6 horas | ✅ |
| Fase 7 - Listado | 6 horas | ✅ |
| Fase 8 - Mejora Visual | 5 horas | ✅ |
| Fase 9 - Documentación | 4 horas | ✅ |

**Total estimado: 41 horas**
**Total real: 41 horas**

## Entregables Finales

1. ✅ Código fuente completo
2. ✅ Script SQL (script.sql)
3. ✅ requirements.md
4. ✅ plan_implementacion.md
5. ✅ Evidencias en carpeta /evidencias
6. ✅ README.md
7. ✅ Repositorio GitHub con historial de commits