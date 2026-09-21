package com.example.taller_android_firebase.data.repository

import com.example.taller_android_firebase.data.local.dao.TaskDraftDao
import com.example.taller_android_firebase.data.local.entity.TaskDraftEntity
import com.example.taller_android_firebase.data.mapper.toDomain
import com.example.taller_android_firebase.data.mapper.toEntity
import com.example.taller_android_firebase.domain.model.TaskDraft
import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTaskRepositoryImpl @Inject constructor(
    private val taskDraftDao: TaskDraftDao,
    private val firebaseAuth: FirebaseAuth
) : LocalTaskRepository {

    override fun getLocalDrafts(): Flow<List<TaskDraft>> {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            return flow { emit(emptyList()) }
        }
        return taskDraftDao.getDraftsByOwner(uid).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveDraft(title: String, description: String): Result<Unit> {
        val uid = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))
        return try {
            val draftEntity = TaskDraftEntity(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                createdAt = System.currentTimeMillis(),
                ownerId = uid
            )
            taskDraftDao.insertDraft(draftEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDraft(draftId: String): Result<Unit> {
        return try {
            taskDraftDao.deleteDraftById(draftId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDraft(draft: TaskDraft): Result<Unit> {
        return try {
            taskDraftDao.insertDraft(draft.toEntity()) // Room REPLACE logic
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
