package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.TaskDraft
import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalDraftsUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository
) {
    operator fun invoke(): Flow<List<TaskDraft>> {
        return localTaskRepository.getLocalDrafts()
    }
}
