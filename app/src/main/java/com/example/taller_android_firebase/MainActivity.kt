package com.example.taller_android_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taller_android_firebase.ui.navigation.AppNavigation
import com.example.taller_android_firebase.ui.theme.Taller_Android_FirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller_Android_FirebaseTheme {
                AppNavigation()
            }
        }
    }
}
