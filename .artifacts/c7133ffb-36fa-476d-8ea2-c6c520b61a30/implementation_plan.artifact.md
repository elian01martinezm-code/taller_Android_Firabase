# Plan de Implementación - Refactorización de Capa de Presentación a UI

Basado en tu preferencia, vamos a eliminar la carpeta `presentation` y mover todo su contenido dentro de la carpeta `ui`. Esto simplificará la estructura del proyecto y alineará el código con el nombre original de la capa visual que mencionaste al inicio.

## Objetivo
Mover los paquetes `auth`, `tasks` y `navigation` desde `com.example.taller_android_firebase.presentation` hacia `com.example.taller_android_firebase.ui`, actualizando todas las referencias de código necesarias.

## Cambios Propuestos

### 📂 Movimiento de Archivos
#### [DELETE] y [NEW] (Mover directorios)
- Mover `app/src/main/java/com/example/taller_android_firebase/presentation/auth/` a `app/src/main/java/com/example/taller_android_firebase/ui/auth/`.
- Mover `app/src/main/java/com/example/taller_android_firebase/presentation/tasks/` a `app/src/main/java/com/example/taller_android_firebase/ui/tasks/`.
- Mover `app/src/main/java/com/example/taller_android_firebase/presentation/navigation/` a `app/src/main/java/com/example/taller_android_firebase/ui/navigation/`.
- Eliminar la carpeta `presentation` vacía.

### 🛠️ Actualización de Código
#### [MODIFY] Archivos en los nuevos paquetes `ui.*`
- Cambiar la declaración `package ...presentation...` por `package ...ui...`.
- Actualizar los `import` internos entre ellos.
#### [MODIFY] [MainActivity.kt](file:///C:/Users/elian/AndroidStudioProjects/taller_Android_Firebase/app/src/main/java/com/example/taller_android_firebase/MainActivity.kt)
- Actualizar los imports de ViewModels y Navigation para apuntar a la nueva ubicación en `ui`.

## Plan de Verificación

### Pruebas Automatizadas
- Ejecutar `./gradlew app:assembleDebug` para asegurar que todas las referencias se actualizaron correctamente y Hilt puede seguir inyectando los ViewModels.

### Verificación Manual
- Abrir la aplicación y verificar que la navegación y los estados de UI sigan funcionando exactamente igual que antes.

---
> [!NOTE]
> **Consejo de Mentor:** Este proceso se llama "Refactorización". Es una práctica muy sana y común en el desarrollo profesional para mantener el código ordenado y fiel a la arquitectura deseada. ¡No tengas miedo de mover las piezas para que el mapa sea más claro!
