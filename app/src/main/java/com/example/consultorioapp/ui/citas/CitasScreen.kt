package com.example.consultorioapp.ui.citas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.consultorioapp.data.models.Cita

@Composable
fun CitasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .weight(1f)
        ) {
            Text("Citas", style = MaterialTheme.typography.headlineMedium)
            CitasContador()
        }
        Surface(
            modifier = Modifier.weight(3f),
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
    val citasViewModel: CitasViewModel = hiltViewModel()
    val citasCounter by citasViewModel.citasCounter.collectAsState()

    LaunchedEffect(Unit) {
        citasViewModel.contadorCitas()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Surface(
            modifier = modifier
                .weight(2f),
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
                        text = citasCounter.second.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text("Hoy", style = MaterialTheme.typography.bodySmall)
                }
                //Total
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = citasCounter.third.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Total", style = MaterialTheme.typography.bodySmall)
                }
                //Canceladas
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        citasCounter.first.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Canceladas", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        //Boton add
        Button(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight(),
            shape = MaterialTheme.shapes.medium,
            onClick = { showDialog = true }) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.size(50.dp),
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

    val citasViewModel: CitasViewModel = hiltViewModel()
    val citasCanceladas by citasViewModel.citasCanceladas.collectAsState()
    val citasNoCanceladas by citasViewModel.citasNoCanceladas.collectAsState()

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selectedIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                },
                unselectedContentColor = Color.Gray
            )
        }
    }
    when (selectedIndex) {
        0 -> ProximasCitas(citasNoCanceladas, title = "Proximas")
        2 -> ProximasCitas(citasCanceladas, title = "Canceladas")
    }
}

@Composable
fun ProximasCitas(cita: List<Cita>, title: String) {

    val citasViewModel: CitasViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        citasViewModel.fetchCitas()
    }
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Text(
            "$title citas",
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 5.dp,
                    start = 10.dp
                )
            ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 140.dp)
        ) {
            items(cita) { cita ->
                CitaCard(
                    cita = cita
                )
            }
        }
    }
}




