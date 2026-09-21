# Informe de Evidencia: Gestor Personal de Tareas

**Estudiante:** [Tu Nombre Completo]
**Materia:** [Nombre de la Materia]
**Fecha:** 21 de septiembre de 2026

---

## 1. Introducción
Este documento presenta las pruebas de funcionamiento de la aplicación "Gestor Personal de Tareas". La app integra autenticación con Firebase, base de datos en tiempo real con Firestore y persistencia local offline con Room, bajo una arquitectura Clean Architecture.

---

## 2. Evidencia de Pruebas (Paso a Paso)

### Paso 1: Autenticación y Registro Manual
**Descripción:** Se realiza el registro de un nuevo usuario. La app debe cerrar la sesión automáticamente para obligar a un login manual.
*   **Instrucciones para la captura:** Toma el pantallazo en la pantalla de "Crear Cuenta" con los datos ya escritos o justo cuando vuelvas al Login tras registrarte.

> **[ PEGAR AQUÍ CAPTURA 1: PANTALLA DE REGISTRO / LOGIN ]**

---

### Paso 2: Panel Principal y Sincronización Cloud (CRUD - Create)
**Descripción:** El usuario inicia sesión y crea una nueva tarea que se sincroniza inmediatamente con la nube.
*   **Instrucciones para la captura:** Toma el pantallazo de la pantalla principal donde se vea la lista con al menos una tarea recién creada.

> **[ PEGAR AQUÍ CAPTURA 2: LISTA DE TAREAS CLOUD ]**

---

### Paso 3: Edición de Datos en Tiempo Real (CRUD - Update)
**Descripción:** Se pone a prueba la capacidad de editar una tarea existente.
*   **Instrucciones para la captura:** Abre el diálogo de edición (icono del lápiz) y toma la captura donde se vea el título que estás a punto de cambiar.

> **[ PEGAR AQUÍ CAPTURA 3: DIÁLOGO DE EDICIÓN ]**

---

### Paso 4: Persistencia Local Offline (Room)
**Descripción:** El usuario accede a la sección de borradores para guardar información que no desea subir aún a la nube.
*   **Instrucciones para la captura:** Navega a "Borradores Locales" (icono de la nota) y crea un borrador. Toma la captura donde se vea la tarjeta con la etiqueta "Local (Room)".

> **[ PEGAR AQUÍ CAPTURA 4: PANTALLA DE BORRADORES ]**

---

### Paso 5: Integración y Publicación (Local -> Cloud)
**Descripción:** Se verifica el flujo de publicación: el dato viaja de SQLite a Firestore y se borra de la memoria local.
*   **Instrucciones para la captura:** Toma el pantallazo justo después de dar clic en el icono de la nube en un borrador. Debes mostrar el "Snackbar" (mensaje de éxito abajo) que dice que se publicó.

> **[ PEGAR AQUÍ CAPTURA 5: ÉXITO DE PUBLICACIÓN ]**

---

### Paso 6: Verificación en el Backend (Firebase Console)
**Descripción:** Evidencia de que los datos realmente residen en los servidores de Google.
*   **Instrucciones para la captura:** Toma una captura de pantalla de tu navegador web donde se vea la colección `tasks` en Firestore con los mismos datos que creaste en el celular.

> **[ PEGAR AQUÍ CAPTURA 6: CONSOLA DE FIREBASE WEB ]**

---

## 3. Conclusión
La aplicación cumple con todos los requisitos técnicos. La separación de capas permite que el sistema sea fluido y que la gestión de errores (como fallos de red durante la publicación) se maneje de forma transparente para el usuario.
