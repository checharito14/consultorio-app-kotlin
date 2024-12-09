package com.example.consultorioapp.ui.citas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.consultorioapp.R
import com.example.consultorioapp.data.models.Cita
import com.example.consultorioapp.ui.theme.AppTheme

@Composable
fun CitaCard(modifier: Modifier = Modifier, cita: Cita) {
    Card(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        cita.pacienteId,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(cita.descripcion, style = MaterialTheme.typography.bodySmall)
                }


            }
            MoreDetailsButton(cita = cita)
        }

        Spacer(Modifier.height(5.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        bottom = 15.dp,
                        end = 18.dp,
                        start = 18.dp
                    )
                ),
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.small,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(vertical = 1.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp)
                            .clickable { },
                        painter = painterResource(id = R.drawable.ic_schedule),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,

                        )
                    Text(
                        cita.fecha ?: "",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Light
                    )
                }
                Text(
                    "Hora: ${cita.hora}:00" ?: "",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun MoreDetailsButton(cita: Cita) {
    var showMenu by remember { mutableStateOf(false) }
    val citasViewModel: CitasViewModel = hiltViewModel()


    IconButton(onClick = { showMenu = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert, // Ícono de tres puntos
            contentDescription = "More details"
        )
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(
            onClick = {
                citasViewModel.changeCitaState(cita)
                citasViewModel.fetchCitas()
                citasViewModel.contadorCitas()
                showMenu = false
            },
            text = { Text("Mover a canceladas") },
        )
    }
}



