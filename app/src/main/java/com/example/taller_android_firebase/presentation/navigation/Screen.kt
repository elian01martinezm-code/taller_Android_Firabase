package com.example.taller_android_firebase.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list")
    object Drafts : Screen("drafts")
}
