package com.example.taller_android_firebase.domain.model

data class TaskDraft(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val ownerId: String
)
