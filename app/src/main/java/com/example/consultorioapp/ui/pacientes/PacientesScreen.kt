package com.example.consultorioapp.ui.pacientes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.R
import com.example.consultorioapp.data.models.Paciente
import com.example.consultorioapp.ui.components.LogoutDialog

@Composable
fun PacientesScreen(
    userId: String?,
    modifier: Modifier = Modifier
) {
    val pacientesViewModel: PacientesViewModel = hiltViewModel()
    val pacientes by pacientesViewModel.pacientes.collectAsState()

    // Fetch pacientes
    LaunchedEffect(Unit) {
        pacientesViewModel.fetchPacientes()
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        // Encabezado fijo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(
                text = "Pacientes",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            AgregarPacienteButton(onPacienteAgregado = { paciente ->
                pacientesViewModel.addPaciente(paciente)
            })
        }

        // Lista desplazable de pacientes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 60.dp)
        ) {
            items(pacientes) { paciente ->
                PacienteCard(
                    paciente = paciente,
                    onDeleteClick = { pacienteId ->
                        pacientesViewModel.deletePaciente(pacienteId)
                    }
                )
            }
        }
    }
}


@Composable
fun PacienteCard(
    paciente: Paciente,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(top = 30.dp),
        shape = MaterialTheme.shapes.extraSmall,    
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 1.dp
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            VerticalDivider(
                color = MaterialTheme.colorScheme.tertiary,
                thickness = 4.dp,
                modifier = Modifier
                    .height(100.dp)
                    .width(18.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                        )
                    }
                    Column {
                        Text(
                            text = paciente.nombre, fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Edad: ${paciente.edad}", fontSize = 13.sp,
                            fontWeight = FontWeight.W200,
                            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.CenterHorizontally)
                        )
                    }
                    DeleteButton(onClick = {
                        onDeleteClick(paciente.id)
                    })
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .fillMaxWidth(),
                    thickness = 2.dp
                )
                Text(
                    text = "Fecha de registro: ${paciente.fechaRegistro}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
//                Row(
//                    modifier = Modifier.padding(horizontal = 10.dp)
//                ) {
////                    EditButton()
//
//                }
            }
        }
    }
}

@Composable
fun AgregarPacienteButton(modifier: Modifier = Modifier, onPacienteAgregado: (Paciente) -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    OutlinedButton(
        onClick = { showDialog = true },
        modifier = modifier
            .padding(top = 10.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text("Agregar", style = MaterialTheme.typography.bodyMedium)
    }
    AddPacienteDialog(showDialog, { showDialog = false }, onPacienteAgregado = onPacienteAgregado)
}

@Composable
fun EditButton() {
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .padding(end = 8.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text("Editar", fontSize = 10.sp, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteButton(onClick: () -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    IconButton(
        onClick = { showDialog = true },
    ) {
        Icon( imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
    }

    LogoutDialog(
        showDialog,
        onDismiss = { showDialog = false },
        title = "Confirmar eliminar",
        text = "¿Estás seguro de que quieres eliminar al paciente?",
        onClick = onClick
    )

}



