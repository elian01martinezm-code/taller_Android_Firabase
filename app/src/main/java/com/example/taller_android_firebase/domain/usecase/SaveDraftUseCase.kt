package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository
) {
    suspend operator fun invoke(title: String, description: String): Result<Unit> {
        if (title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título del borrador no puede estar vacío"))
        }
        return localTaskRepository.saveDraft(title, description)
    }
}
