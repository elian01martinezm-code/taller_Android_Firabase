package com.example.taller_android_firebase.data.local.dao

import androidx.room.*
import com.example.taller_android_firebase.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {

    @Query("SELECT * FROM task_drafts WHERE ownerId = :ownerId ORDER BY createdAt DESC")
    fun getDraftsByOwner(ownerId: String): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity)

    @Query("DELETE FROM task_drafts WHERE id = :draftId")
    suspend fun deleteDraftById(draftId: String)
}
