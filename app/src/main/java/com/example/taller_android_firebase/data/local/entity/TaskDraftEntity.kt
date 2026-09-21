package com.example.taller_android_firebase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_drafts")
data class TaskDraftEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val ownerId: String
)
