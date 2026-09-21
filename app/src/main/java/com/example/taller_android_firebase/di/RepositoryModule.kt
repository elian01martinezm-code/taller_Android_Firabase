package com.example.taller_android_firebase.di

import com.example.taller_android_firebase.data.repository.AuthRepositoryImpl
import com.example.taller_android_firebase.data.repository.LocalTaskRepositoryImpl
import com.example.taller_android_firebase.data.repository.TaskRepositoryImpl
import com.example.taller_android_firebase.domain.repository.AuthRepository
import com.example.taller_android_firebase.domain.repository.LocalTaskRepository
import com.example.taller_android_firebase.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindLocalTaskRepository(
        localTaskRepositoryImpl: LocalTaskRepositoryImpl
    ): LocalTaskRepository
}
