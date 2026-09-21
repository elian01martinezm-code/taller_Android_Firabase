package com.example.taller_android_firebase.data.mapper

import com.example.taller_android_firebase.data.local.entity.TaskDraftEntity
import com.example.taller_android_firebase.domain.model.TaskDraft

fun TaskDraftEntity.toDomain(): TaskDraft {
    return TaskDraft(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        ownerId = ownerId
    )
}

fun TaskDraft.toEntity(): TaskDraftEntity {
    return TaskDraftEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        ownerId = ownerId
    )
}
