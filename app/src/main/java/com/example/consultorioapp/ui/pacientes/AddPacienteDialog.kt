package com.example.consultorioapp.ui.pacientes

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.data.models.Paciente
import com.example.consultorioapp.ui.App
import com.example.consultorioapp.ui.components.CustomTextField
import com.example.consultorioapp.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Composable
fun AddPacienteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onPacienteAgregado: (Paciente) -> Unit
) {

    var nombre by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    if (nombre.isNotBlank() && edad.isNotBlank()) {
                        val paciente = Paciente(
                            id = UUID.randomUUID().toString(),
                            nombre = nombre,
                            edad = edad.toInt(),
                            fechaRegistro = SimpleDateFormat("dd/MM/yyyy").format(Date())
                        )
                        onPacienteAgregado(paciente)
                        onDismiss()
                    }
                }) {
                    Text(text = "Guardar", color = MaterialTheme.colorScheme.primary)
                }
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Agregar Paciente", style = MaterialTheme.typography.headlineMedium)
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }
            },
            text = {
                DialogForm(
                    nombre = nombre,
                    onNombreChange = { nombre = it },
                    edad = edad,
                    onEdadChange = { edad = it }
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            shape = MaterialTheme.shapes.small// Fondo del diálogo
        )
    }

}

@Composable
fun DialogForm(
    nombre: String,
    onNombreChange: (String) -> Unit,
    edad: String,
    onEdadChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = Modifier
            .padding(bottom = 18.dp)
            .fillMaxWidth(),
        thickness = 2.dp
    )
    Column(modifier = Modifier.padding(16.dp)) {

        CustomTextField(
            value = nombre,
            onTextFieldChanged = onNombreChange,
            label = "Nombre"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de texto para la edad
        CustomTextField(
            value = edad,
            onTextFieldChanged = onEdadChange,
            label = "Edad",
            isInt = true
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

