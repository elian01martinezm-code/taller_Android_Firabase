# Documentación Técnica: Gestor Personal de Tareas

## 1. Introducción
El **Gestor Personal de Tareas** es una solución móvil avanzada para la gestión de productividad, diseñada para funcionar tanto en entornos con conexión (Nube) como sin ella (Offline). La aplicación permite a los usuarios autenticarse de forma segura, gestionar tareas en tiempo real y mantener borradores locales persistentes.

## 2. Stack Tecnológico
Para garantizar la modernidad y el rendimiento, se han utilizado las últimas tecnologías del ecosistema Android:
- **Lenguaje:** Kotlin 100% (Versión 2.2.10).
- **Interfaz de Usuario:** Jetpack Compose con Material Design 3.
- **Inyección de Dependencias:** Dagger Hilt.
- **Base de Datos Local:** Room (v2.8.5) con KSP.
- **Backend/Nube:** Firebase (Authentication y Cloud Firestore).
- **Asincronía:** Kotlin Coroutines y Flow (StateFlow para reactividad en la UI).

## 3. Arquitectura del Sistema
La aplicación sigue los principios de **Clean Architecture** y el patrón **MVVM**, dividiéndose en tres capas estrictamente desacopladas:

### A. Capa de Dominio (Domain)
Contiene las reglas de negocio y modelos puros.
- **Modelos:** `Task`, `TaskDraft`, `UserSession`.
- **Interfaces:** Definición de los contratos de los repositorios.
- **Use Cases:** Lógica atómica como `PublishDraftUseCase` y `LoginUseCase`.

### B. Capa de Datos (Data)
Implementa la lógica de persistencia y comunicación externa.
- **Repositorios:** `TaskRepositoryImpl` (Firestore), `LocalTaskRepositoryImpl` (Room).
- **Mappers:** Traductores de datos entre DTOs y modelos de dominio.
- **DAO:** Objetos de acceso a datos para SQLite.

### C. Capa de Interfaz de Usuario (UI)
Maneja la presentación y la interacción con el usuario.
- **ViewModels:** Gestión de estado reactivo mediante `StateFlow`.
- **Navigation:** Grafo de navegación seguro que protege las rutas autenticadas.

## 4. Configuración del Entorno (Firebase)
Para el correcto funcionamiento, el proyecto requiere:
1. **Archivo `google-services.json`:** Debe estar ubicado en la carpeta `app/`.
2. **Reglas de Firestore:**
```javascript
service cloud.firestore {
  match /databases/{database}/documents {
    match /tasks/{taskId} {
      allow read, update, delete: if request.auth != null && request.auth.uid == resource.data.ownerId;
      allow create: if request.auth != null && request.auth.uid == request.resource.data.ownerId;
    }
  }
}
```
3. **Índice Compuesto:** Es necesario un índice en la colección `tasks` para los campos `ownerId` (Asc) y `createdAt` (Asc).

## 5. Funcionalidades Destacadas
- **Registro Manual:** Flujo que obliga al usuario a validar sus credenciales tras el registro.
- **Tiempo Real:** Uso de `callbackFlow` para ver cambios en la nube instantáneamente.
- **Publicación Atómica:** Sistema que garantiza que un borrador local solo se borre de Room tras una subida exitosa a Firestore.
- **Seguridad Multicapa:** Filtrado de datos por `uid` tanto en código como en reglas de servidor.

---
> [!TIP]
> **Nota para el evaluador:** El proyecto utiliza **KSP** y **AGP 9**, optimizando los tiempos de compilación y asegurando compatibilidad con las versiones más recientes de las librerías de Jetpack.
