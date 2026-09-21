package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.Task
import com.example.taller_android_firebase.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Unit> {
        return taskRepository.updateTask(task)
    }
}
