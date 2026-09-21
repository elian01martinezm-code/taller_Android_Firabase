package com.example.taller_android_firebase.data.mapper

import com.example.taller_android_firebase.data.remote.model.TaskDocument
import com.example.taller_android_firebase.domain.model.Task

fun TaskDocument.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        // Usamos los nuevos nombres internos
        isCompleted = taskCompleted || legacyCompleted, 
        createdAt = createdAt,
        ownerId = ownerId
    )
}

fun Task.toDocument(): TaskDocument {
    return TaskDocument(
        id = id,
        title = title,
        description = description,
        taskCompleted = isCompleted,
        legacyCompleted = isCompleted, 
        createdAt = createdAt,
        ownerId = ownerId
    )
}
