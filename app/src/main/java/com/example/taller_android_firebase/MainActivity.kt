package com.example.taller_android_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.taller_android_firebase.presentation.auth.AuthViewModel
import com.example.taller_android_firebase.presentation.navigation.AppNavigation
import com.example.taller_android_firebase.presentation.tasks.TaskViewModel
import com.example.taller_android_firebase.ui.theme.Taller_Android_FirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val authViewModel: AuthViewModel by viewModels()
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller_Android_FirebaseTheme {
                AppNavigation(
                    authViewModel = authViewModel,
                    taskViewModel = taskViewModel
                )
            }
        }
    }
}
