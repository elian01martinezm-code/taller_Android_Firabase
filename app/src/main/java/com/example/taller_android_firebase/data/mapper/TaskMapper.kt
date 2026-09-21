package com.example.taller_android_firebase.data.mapper

import com.example.taller_android_firebase.data.remote.model.TaskDocument
import com.example.taller_android_firebase.domain.model.Task

fun TaskDocument.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        ownerId = ownerId
    )
}

fun Task.toDocument(): TaskDocument {
    return TaskDocument(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = createdAt,
        ownerId = ownerId
    )
}
