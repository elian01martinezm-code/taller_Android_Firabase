package com.example.taller_android_firebase.domain.repository

import com.example.taller_android_firebase.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface LocalTaskRepository {
    fun getLocalDrafts(): Flow<List<TaskDraft>>
    suspend fun saveDraft(title: String, description: String): Result<Unit>
    suspend fun deleteDraft(draftId: String): Result<Unit>
    suspend fun updateDraft(draft: TaskDraft): Result<Unit>
}
