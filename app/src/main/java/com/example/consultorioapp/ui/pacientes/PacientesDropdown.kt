package com.example.consultorioapp.ui.pacientes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.consultorioapp.data.models.Paciente
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacientesDropdown(
    viewModel: PacientesViewModel = hiltViewModel(),
    onPacienteSelected: (Paciente) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPaciente by remember { mutableStateOf<Paciente?>(null) }

    val pacientes by viewModel.pacientes.collectAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        viewModel.fetchPacientes()
    }

    //Dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedPaciente?.nombre ?: "Seleccione un paciente",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(top = 8.dp),
            label = { Text("Paciente") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            pacientes.forEach { paciente ->
                DropdownMenuItem(
                    text = { Text(paciente.nombre) },
                    onClick = {
                        selectedPaciente = paciente
                        expanded = false
                        onPacienteSelected(paciente)
                    }
                )

            }

        }
    }
}