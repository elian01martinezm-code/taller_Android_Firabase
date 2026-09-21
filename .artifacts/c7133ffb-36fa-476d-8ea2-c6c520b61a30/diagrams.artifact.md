# Diagramas de Flujo del Proyecto

Estos diagramas representan la lógica técnica de la aplicación "Gestor Personal de Tareas".

## 1. Arquitectura General y Flujo de Datos
Este diagrama muestra cómo se comunican las capas desde que el usuario pulsa un botón hasta que el dato llega a la nube o al disco.

```mermaid
graph TD
    subgraph "Capa de UI (Compose)"
        UI[Pantalla / Screen] -->|Acción del usuario| VM[ViewModel]
        VM -->|Expone Estado| UI
    end

    subgraph "Capa de Dominio"
        VM -->|Llama| UC[Caso de Uso / UseCase]
        UC -->|Define Contrato| RepoInt[Interfaz Repositorio]
    end

    subgraph "Capa de Datos"
        RepoInt -.->|Implementación| RepoImpl[RepositoryImpl]
        RepoImpl -->|Sincronización| FS[(Cloud Firestore)]
        RepoImpl -->|Persistencia| RM[(Room SQLite)]
    end
```

---

## 2. Flujo Crítico: Publicación de Borradores
Este diagrama detalla la lógica de "Seguridad de Datos" que implementamos para mover un borrador local a la nube.

```mermaid
flowchart TD
    A[Inicio: Click en Publicar] --> B{¿Hay Internet?}
    B -- Sí --> C[Caso de Uso: PublishDraft]
    B -- No --> D[Error: Permanecer en Room]

    C --> E[Subir a Firestore]
    E --> F{¿Éxito?}

    F -- Sí --> G[Borrar de Room local]
    F -- No --> H[Mantener en Room y mostrar Error]

    G --> I[Fin: Tarea en la Nube]
    H --> J[Fin: Intento Fallido]
```

---

## 3. Flujo de Autenticación y Seguridad
Muestra cómo el `ownerId` protege la información.

```mermaid
sequenceDiagram
    participant U as Usuario
    participant A as Auth (Firebase)
    participant DB as Firestore

    U->>A: Login (Email/Pass)
    A-->>U: Retorna UID (ID Único)
    U->>DB: Consulta Tareas (Filtro ownerId == UID)
    DB-->>U: Retorna solo mis tareas
```
