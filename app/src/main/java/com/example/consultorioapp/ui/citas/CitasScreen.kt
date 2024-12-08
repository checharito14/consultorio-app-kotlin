package com.example.consultorioapp.ui.citas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CitasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .paddingFromBaseline(top = 70.dp)
    ) {
        Column(modifier = Modifier.padding(30.dp)) {
            Text("Citas", style = MaterialTheme.typography.headlineMedium)

            CitasContador()
        }

        Spacer(Modifier.height(40.dp))
        Surface(
            shape = RoundedCornerShape(
                topStart = 36.dp,
                topEnd = 36.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 2.dp
        ) {
            Column {
                CitasFilter()
            }
        }
    }
}

@Composable
fun CitasContador(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .height(85.dp)
    ) {
        Surface(
            modifier = modifier
                .weight(2.3f)
                .fillMaxHeight(),
            shape = MaterialTheme.shapes.extraSmall,
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 2.dp
        ) {
            Row() {
                //Today
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "1",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text("Hoy", style = MaterialTheme.typography.bodyMedium)
                }
                //Total
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "1",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Total", style = MaterialTheme.typography.bodyMedium)
                }
                //Canceladas
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "1",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Canceladas", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        //Boton add
        Button(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            shape = MaterialTheme.shapes.medium,
            onClick = { showDialog = true }) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.size(30.dp),
                contentDescription = "Crear cita"
            )
        }
    }
    AddCitaDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false }
    )
}

@Composable
fun CitasFilter() {
    var selectedIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Próximas", "Pasadas", "Canceladas")

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = MaterialTheme.colorScheme.tertiary,
                height = 3.dp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                text = { Text(text = title, style = MaterialTheme.typography.bodyMedium) },
                selectedContentColor = MaterialTheme.colorScheme.secondary,
                unselectedContentColor = Color.Gray
            )
        }
    }
    when (selectedIndex) {
        0 -> ProximasCitas()
        1 -> PasadasCitas()
        2 -> CanceladasCitas()
    }
}

@Composable
fun ProximasCitas() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        CitaCard()
    }
}

@Composable
fun CanceladasCitas() {

}

@Composable
fun PasadasCitas() {
    Text("Pasadas")
}




