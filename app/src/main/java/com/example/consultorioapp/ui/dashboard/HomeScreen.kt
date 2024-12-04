package com.example.consultorioapp.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomePortrait() {
    Scaffold(
        bottomBar = { ConsultorioBottomNavigation() }
    ) { padding ->
        HomeScreen(Modifier.padding(padding))
    }

}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
//    Column(modifier.Arrangment(Alignment.Center)) {
//        Text("hola")
//    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConsultorioBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = {
                Text("Pacientes")
            },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            label = {
                Text("Citas")
            },
            selected = false,
            onClick = { }
        )
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {

}