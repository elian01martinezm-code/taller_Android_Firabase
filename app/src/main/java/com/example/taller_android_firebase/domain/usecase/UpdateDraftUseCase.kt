package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.TaskDraft
import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import javax.inject.Inject

class UpdateDraftUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        if (draft.title.isBlank()) {
            return Result.failure(IllegalArgumentException("El título no puede estar vacío"))
        }
        return localTaskRepository.updateDraft(draft)
    }
}
