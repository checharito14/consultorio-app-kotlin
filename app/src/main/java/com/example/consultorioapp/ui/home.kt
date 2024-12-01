package com.example.consultorioapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home(modifier: Modifier = Modifier) {
    Text("HOme", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)
}