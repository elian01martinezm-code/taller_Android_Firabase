package com.example.taller_android_firebase.data.remote.model

import com.google.firebase.firestore.PropertyName

data class TaskDocument(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    
    @get:PropertyName("isCompleted")
    @set:PropertyName("isCompleted")
    var isCompleted: Boolean = false,
    
    var createdAt: Long = 0L,
    var ownerId: String = ""
)
