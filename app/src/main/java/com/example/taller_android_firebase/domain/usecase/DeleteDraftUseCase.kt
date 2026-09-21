package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import javax.inject.Inject

class DeleteDraftUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository
) {
    suspend operator fun invoke(draftId: String): Result<Unit> {
        return localTaskRepository.deleteDraft(draftId)
    }
}
