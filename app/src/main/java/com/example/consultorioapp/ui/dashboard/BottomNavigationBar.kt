package com.example.consultorioapp.ui.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.consultorioapp.ui.theme.AppTheme


@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Pacientes", Icons.Default.Person, "pacientes"),
        BottomNavItem("Citas", Icons.Default.DateRange, "citas"),
        BottomNavItem("Configuracion", Icons.Default.Settings, "configuracion"),
    )

    NavigationBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = if (selected) Color(0xFF9DF2E6) else MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        color = if (selected) Color(0xFF9DF2E6) else MaterialTheme.colorScheme.onSurface
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


data class BottomNavItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)

