package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.Task
import com.example.taller_android_firebase.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.getTasksRealTime()
    }
}
