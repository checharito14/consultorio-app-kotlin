package com.example.consultorioapp.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.consultorioapp.ui.citas.CitasScreen
import com.example.consultorioapp.ui.components.LogoutDialog
import com.example.consultorioapp.ui.pacientes.PacientesScreen
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController, userId: String?) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it })
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            userId = userId,
            onLogout = {
                navController.navigate("initial")
            },
            navController = navController
        )
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    userId: String?,
    onLogout: () -> Unit,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    when (selectedIndex) {
        0 -> HomePage()
        1 -> PacientesScreen(userId = userId)
        2 -> CitasScreen()
        3 -> {
            showDialog = true
        }
    }

    LogoutDialog(
        showDialog = showDialog,
        onDismiss = { navController.navigate("home") },
        title = "Cerrar Sesión",
        text = "¿Estas seguro que quieres cerrar sesión?",
        onClick = {
            showDialog = false
            onLogout()
        }
    )

}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("hola")
    }
}


