package com.example.consultorioapp.ui.home

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.consultorioapp.R
import com.example.consultorioapp.data.models.Cita
import com.example.consultorioapp.ui.citas.CitaCard
import com.example.consultorioapp.ui.citas.CitasScreen
import com.example.consultorioapp.ui.components.LogoutDialog
import com.example.consultorioapp.ui.pacientes.PacientesScreen

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
        0 -> HomePage(navController = navController)
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
fun HomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(.8f)
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.background,
                            Color(0xFFFFFFFF)
                        )
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.doctor),
                contentDescription = "Doctora",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        PaddingValues(
                            end = 30.dp
                        )
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bienvenido!",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.headlineLarge
                )
                IconButton(
                    onClick = { navController.navigate("notifications") },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones"
                    )
                }
            }
            Text(
                text = "¿Como estas?",
                modifier = Modifier
                    .padding(
                        PaddingValues(
                            start = 20.dp,
                            bottom = 120.dp
                        )
                    )
                    .align(Alignment.CenterStart),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Light
            )

            Button(
                onClick = {}, modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_urgent),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    "Cita urgente",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Surface(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = 36.dp,
                topEnd = 36.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Mis servicios", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularIcon(icon = Icons.Default.Add, text = "Consultas")
                    CircularIcon(icon = Icons.Default.DateRange, text = "Pacientes")
                    CircularIcon(icon = Icons.Default.ExitToApp, text = "Salir")
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mis citas", fontWeight = FontWeight.Bold)
                    TextButton(onClick = {}) {
                        Text("Ver más", color = Color.Blue)
                    }
                }
                CitaCard(cita = Cita(id = "1", descripcion = "Cancer", pacienteId = "332"))
            }

        }
    }
}

@Composable
fun CircularIcon(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape
                )
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp).clickable(onClick = { })
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(text, fontSize = 10.sp)
    }
}

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No hay notificaciones", style = MaterialTheme.typography.bodyLarge)
    }
}