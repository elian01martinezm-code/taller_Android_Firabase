package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.repository.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(title: String, description: String): Result<Unit> {
        if (title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título de la tarea no puede estar vacío"))
        }
        return taskRepository.createTask(title, description)
    }
}
