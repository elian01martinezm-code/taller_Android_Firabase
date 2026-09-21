# Plan de Implementación - CRUD Completo y Fix de Firestore

En esta fase final de funcionalidad, corregiremos el mapeo de campos de Firestore para asegurar la compatibilidad total y completaremos el ciclo CRUD (Crear, Leer, Actualizar, Borrar) permitiendo la edición de títulos y descripciones tanto en la nube como localmente.

## User Review Required

> [!IMPORTANT]
> **Cambio de Esquema**: Al corregir el nombre del campo a `isCompleted` en Firestore, las tareas antiguas que tengan el nombre `completed` dejarán de marcarse como completadas en la app. Se recomienda borrar los documentos antiguos en la consola de Firebase tras aplicar este cambio.

## Proposed Changes

### 🛠️ Naming Fix (Firestore)
#### [MODIFY] [TaskDocument.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/data/remote/model/TaskDocument.kt)
- Añadir anotaciones `@get:PropertyName("isCompleted")` y `@set:PropertyName("isCompleted")`.
#### [MODIFY] [TaskRepositoryImpl.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/data/repository/TaskRepositoryImpl.kt)
- Asegurar que la función `updateTask` use la llave `"isCompleted"` de forma explícita.

### 📝 CRUD Completo (Edición)
#### [MODIFY] [LocalTaskRepository.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/domain/repository/LocalTaskRepository.kt)
- Añadir contrato `updateDraft(draft: TaskDraft)`.
#### [NEW] [UpdateDraftUseCase.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/domain/usecase/UpdateDraftUseCase.kt)
- Nuevo caso de uso para la edición local.
#### [MODIFY] [LocalTaskRepositoryImpl.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/data/repository/LocalTaskRepositoryImpl.kt)
- Implementar la actualización en Room.
#### [MODIFY] [TaskViewModel.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/presentation/tasks/TaskViewModel.kt)
- Añadir lógica para editar tareas remotas y borradores locales.
#### [MODIFY] [TaskListScreen.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/presentation/tasks/TaskListScreen.kt)
- Añadir botón de editar (Icono `Edit`) y diálogo de edición para tareas en la nube.
#### [MODIFY] [DraftsScreen.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/presentation/tasks/DraftsScreen.kt)
- Añadir botón de editar y diálogo de edición para borradores locales.

---

## Plan de Verificación

### Pruebas Automatizadas
- Ejecutar `./gradlew app:assembleDebug` para validar que las nuevas dependencias y el código generado por KSP/Room sean correctos.

### Verificación Manual
1.  **Edición Remota**: Cambiar el título de una tarea de Firestore y verificar que se actualice en tiempo real.
2.  **Edición Local**: Modificar un borrador en Room y comprobar que el cambio persiste tras navegar.
3.  **Checkbox Fix**: Marcar una tarea y verificar en Firebase Console que el campo se llame exactamente `isCompleted`.
