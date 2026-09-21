package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.TaskDraft
import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import com.example.taller_android_firebase.domain.repository.TaskRepository
import javax.inject.Inject

class PublishDraftUseCase @Inject constructor(
    private val remoteRepository: TaskRepository,
    private val localRepository: LocalTaskRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        // Primero intentamos guardar en la nube
        val remoteResult = remoteRepository.createTask(draft.title, draft.description)
        
        return if (remoteResult.isSuccess) {
            // Solo si tiene éxito en la nube, lo borramos de Room
            localRepository.deleteDraft(draft.id)
        } else {
            // Si falla, retornamos el error original de la red
            remoteResult
        }
    }
}
