package com.example.taller_android_firebase.domain.repository

import com.example.taller_android_firebase.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksRealTime(): Flow<List<Task>>
    suspend fun createTask(title: String, description: String): Result<Unit>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
}
