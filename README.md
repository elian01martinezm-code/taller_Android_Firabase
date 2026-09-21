# Gestor Personal de Tareas (Android + Firebase + Room) 📱🚀

Este proyecto es una aplicación Android de alto rendimiento diseñada bajo los estándares más modernos de la industria. Implementa un sistema híbrido de gestión de tareas con sincronización en tiempo real mediante **Firebase** y persistencia local offline mediante **Room**.

## 🏗️ Arquitectura: Clean Architecture + MVVM

La aplicación está estructurada siguiendo el paradigma de **Clean Architecture**, dividida en capas desacopladas para garantizar la mantenibilidad y escalabilidad:

- **Domain Layer:** Contiene el "corazón" de la aplicación. Modelos de negocio puros, interfaces de repositorios y Casos de Uso (Use Cases) independientes de cualquier framework.
- **Data Layer:** Implementa la lógica de persistencia. Orquesta la comunicación con Cloud Firestore para datos remotos y Room SQLite para almacenamiento local.
- **UI Layer (Presentation):** Desarrollada con **Jetpack Compose**. Los ViewModels gestionan el estado de la interfaz de forma reactiva mediante **StateFlow**.

## 🛠️ Stack Tecnológico

- **Lenguaje:** [Kotlin 2.2.10](https://kotlinlang.org/)
- **Interfaz de Usuario:** [Jetpack Compose](https://developer.android.com/jetpack/compose) con Material Design 3.
- **Inyección de Dependencias:** [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
- **Persistencia Local:** [Room Database](https://developer.android.com/training/data-storage/room) con KSP.
- **Backend as a Service:** [Firebase](https://firebase.google.com/) (Authentication & Cloud Firestore).
- **Asincronía:** Coroutines y Flow.

## ✨ Funcionalidades Principales

1.  **Autenticación Segura:** Registro e Inicio de Sesión mediante Firebase Auth con un flujo de validación manual.
2.  **Tareas en la Nube:** Gestión CRUD completa (Crear, Leer, Actualizar, Borrar) en tiempo real con Firestore.
3.  **Borradores Offline:** Permite crear y guardar tareas localmente en el dispositivo sin conexión a internet.
4.  **Publicación Atómica:** Sincronización inteligente que mueve borradores locales a la nube asegurando la integridad del dato (no se borra del local hasta que la nube confirma el éxito).
5.  **Seguridad Multicapa:** Filtrado de datos por `ownerId` tanto en el cliente como en las reglas de servidor de Firebase.

## 🚀 Configuración del Proyecto

Para ejecutar este proyecto localmente, asegúrate de:

1.  Tener instalado **Android Studio Ladybug** o superior.
2.  Añadir tu archivo `google-services.json` en la carpeta `app/`.
3.  Habilitar **Email/Password Auth** en la consola de Firebase.
4.  Configurar las **Reglas de Firestore** y el **Índice Compuesto** para los campos `ownerId` y `createdAt`.

## 📜 Licencia

Este proyecto fue desarrollado como parte de un taller técnico de Android avanzado.

---
**Desarrollado por:** [Tu Nombre]  
**Mentor:** AI Senior Android Architect
