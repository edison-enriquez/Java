# Ejemplos MVC + DAO + SQLite con Maven (JavaFX y Swing)

Dos versiones de la misma aplicación, **Gestión de Estudiantes**, para aprender la
arquitectura **Modelo–Vista–Controlador (MVC)** junto con el patrón **DAO**
y una base de datos **SQLite**.

| Proyecto | Interfaz | Cómo se diseña la vista |
|---|---|---|
| [`mvc-javafx-sqlite`](mvc-javafx-sqlite) | JavaFX 21 | Archivo FXML editado con **SceneBuilder** |
| [`mvc-swing-sqlite`](mvc-swing-sqlite) | Swing | Código Java (`JFrame`, `JTable`, ...) |

Las dos aplicaciones comparten el mismo **Modelo** (`Estudiante`), la misma capa de
datos (`ConexionSQLite`, `EstudianteDAO`, `EstudianteDAOSQLite`) y el mismo flujo
del **Controlador**. Lo único que cambia es la **Vista**. Esa es la gran ventaja de MVC.

## ¿Qué hace la aplicación?

- Registrar estudiantes (nombre, correo, nota de 0.0 a 5.0).
- Listarlos en una tabla y filtrarlos por nombre.
- Seleccionar una fila para editarla o eliminarla.
- Los datos quedan guardados en el archivo `estudiantes.db` (SQLite).

## Requisitos

- **JDK 21** (por ejemplo, Eclipse Temurin 21).
- **VS Code** con el *Extension Pack for Java* (incluye soporte para Maven).
- **SceneBuilder** (solo para editar el FXML del proyecto JavaFX).
- No hace falta instalar Maven: cada proyecto trae el **Maven Wrapper** (`mvnw`).

## Cómo ejecutar

```powershell
cd ejemplos-mvc/mvc-javafx-sqlite
.\mvnw.cmd clean javafx:run
```

```powershell
cd ejemplos-mvc/mvc-swing-sqlite
.\mvnw.cmd clean compile exec:java
```

En Linux/macOS use `./mvnw` en lugar de `.\mvnw.cmd`.

## Documentación

En la carpeta [`docs`](docs) están:

- `Guia_MVC_DAO_SQLite.docx`: guía paso a paso con capturas de pantalla.
- `Presentacion_MVC_DAO_SQLite.pptx`: diapositivas para la clase.
- `uml/`: diagramas UML (fuente PlantUML `.puml` + imagen `.png`).
- `capturas/`: capturas de VS Code, SceneBuilder y de las aplicaciones.

## Estructura de paquetes (igual en ambos proyectos)

```
com.ap22
├── App / Main    → punto de entrada
├── modelo        → Estudiante (los datos)
├── db            → ConexionSQLite (abre conexiones y crea la tabla)
├── dao           → EstudianteDAO (interfaz), EstudianteDAOSQLite (JDBC), DAOException
├── controlador   → EstudianteController (recibe eventos, valida y llama al DAO)
└── vista         → JavaFX: estudiantes.fxml  |  Swing: VentanaEstudiantes
```
