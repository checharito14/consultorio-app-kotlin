package com.example.consultorioapp.ui.pacientes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.data.models.Paciente

@Composable
fun PacientesScreen(
    userId: String?,  // Asegúrate de pasar el userId aquí
    modifier: Modifier = Modifier
) {
    val pacientesViewModel: PacientesViewModel = hiltViewModel()
    val pacientes by pacientesViewModel.pacientes.collectAsState()

    // Fetch pacientes
    pacientesViewModel.fetchPacientes()

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(30.dp)
            .paddingFromBaseline(top = 70.dp)
    ) {
        // Llamada a PacienteSection
        PacienteSection() {
            // Este es el bloque content que se pasa a PacienteSection
            pacientes.forEach { paciente ->
                PacienteCard(paciente, onDeleteClick = { pacienteId ->
                    pacientesViewModel.deletePaciente(pacienteId)
                })
            }
        }
    }
}

@Composable
fun PacienteSection(
    viewModel: PacientesViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    Column {
        Row {
            Text(
                text = "Pacientes",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.weight(1f)
            )
            // Botón de agregar paciente
            AgregarPacienteButton(
                onPacienteAgregado = { paciente ->
                    viewModel.addPaciente(paciente)
                }
            )
        }
        // Ejecutamos el contenido que pasamos a este Composable
        content()
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
        shadowElevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = paciente.nombre, fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Edad: ${paciente.edad}", fontSize = 11.sp,
                        fontWeight = FontWeight.W200,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 3.dp)
                    .fillMaxWidth(),
                thickness = 2.dp
            )
            Text(
                text = "Fecha de registro: ${paciente.fechaRegistro}",
                fontSize = 9.sp,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                EditButton(Modifier.weight(1f))
                DeleteButton(Modifier.weight(1f), onClick = {
                    onDeleteClick(paciente.id)
                })
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
fun EditButton(modifier: Modifier) {
    OutlinedButton(
        onClick = {},
        modifier = modifier
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
fun DeleteButton(modifier: Modifier, onClick: () -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Button(
        onClick = { showDialog = true },
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    ) {
        Text("Eliminar", fontSize = 10.sp, modifier = Modifier.align(Alignment.CenterVertically))
    }
    ConfirmDialog()
}



