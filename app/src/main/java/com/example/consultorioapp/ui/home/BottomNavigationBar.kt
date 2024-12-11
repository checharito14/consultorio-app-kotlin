package com.example.consultorioapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Pacientes", Icons.Default.Person, "pacientes"),
        BottomNavItem("Citas", Icons.Default.DateRange, "citas"),
        BottomNavItem("Salir", Icons.AutoMirrored.Filled.ExitToApp, "salir"),
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(30.dp)),
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = if (selected) MaterialTheme.colorScheme.inversePrimary else Color(0xFF646A83  )
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            fontSize = 10.sp,
                            color = if (selected) MaterialTheme.colorScheme.inversePrimary else Color(0xFF646A83  )
                        )
                    },
                    selected = selected,
                    onClick = { onItemSelected(index) },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }

}


data class BottomNavItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)

