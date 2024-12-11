package com.example.consultorioapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.consultorioapp.NavGraph
import com.example.consultorioapp.ui.theme.AppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun App(modifier: Modifier = Modifier) {
    val auth = Firebase.auth
    val navController = rememberNavController()


    NavGraph(navController = navController, auth = auth)
}

