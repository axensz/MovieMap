# MovieMap
**MovieMap** es una aplicación móvil desarrollada con Jetpack Compose que permite a los usuarios explorar información sobre películas y series disponibles en diversas plataformas de streaming. Incluye funcionalidades de autenticación, exploración de contenido, creación de listas personalizadas y filtros avanzados.

---

## Características
- Inicio de sesión y registro de usuarios
- Selección de servicios de streaming preferidos
- Pantalla de inicio con recomendaciones personalizadas
- Visualización de contenido reciente y popular
- Creación y gestión de listas: "Ver más tarde", "Visto", "Me gusta"
- Filtros por año de estreno, calificación, género y clasificación por edad
- Perfil de usuario con vista de datos básicos

---

## Diseño UX/UI
El diseño fue prototipado en Figma, utilizando principios de diseño centrado en el usuario.  
Enlace al diseño:  
[Figma](https://www.figma.com/design/6cEdOZnBvjNYNDuTj5Ft8r/MovieMap)

---

## Tecnologías
- Lenguaje: Kotlin
- UI Toolkit: Jetpack Compose
- Sistema de construcción: Kotlin DSL (`build.gradle.kts`)

---

## Arquitectura y Estructura del Proyecto

### Arquitectura
El proyecto sigue una arquitectura limpia (Clean Architecture) con una clara separación de capas:
- `data/`: Capa de datos y acceso a fuentes externas
- `presentation/`: Capa de presentación y lógica de negocio
- `ui/`: Capa de interfaz de usuario y componentes visuales

### Patrones de Diseño
- MVVM (Model-View-ViewModel)
- Clean Architecture

### Dependencias Principales
- **Frontend**:
  - Jetpack Compose
  - Material3
  - Coil (carga de imágenes)
  - Navigation Compose

- **Backend**:
  - Firebase:
    - Authentication
    - Firestore

### Configuración Técnica
- SDK mínimo: 24 (Android 7.0)
- SDK objetivo: 35
- Java 11
- Kotlin

### Estructura de Carpetas
```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/upbapps/moviemap/
│   │   │       ├── data/
│   │   │       ├── presentation/
│   │   │       └── ui/
│   │   ├── res/
│   │   └── AndroidManifest.xml
├── build.gradle.kts
└── proguard-rules.pro
```

### Seguridad
- Implementación de reglas de Firestore
- Firebase Authentication para manejo seguro de usuarios

### Testing
- JUnit para pruebas unitarias
- Espresso para pruebas de UI
- AndroidX Test para pruebas de integración

---

## Package Name

```kotlin
com.upbapps.moviemaps
