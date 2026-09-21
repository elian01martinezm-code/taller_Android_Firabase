package com.example.taller_android_firebase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taller_android_firebase.data.local.dao.TaskDraftDao
import com.example.taller_android_firebase.data.local.entity.TaskDraftEntity

@Database(entities = [TaskDraftEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDraftDao(): TaskDraftDao
}
