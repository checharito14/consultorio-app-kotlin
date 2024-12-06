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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.ui.App
import com.example.consultorioapp.ui.components.CustomTextField
import com.example.consultorioapp.ui.theme.AppTheme

@Composable
fun AddPacienteDialog(showDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
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
                DialogForm()
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            shape = MaterialTheme.shapes.small// Fondo del diálogo
        )
    }

}

@Composable
fun DialogForm(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = Modifier
            .padding(bottom = 18.dp)
            .fillMaxWidth(),
        thickness = 2.dp
    )
    Column(modifier = Modifier.padding(16.dp)) {

        CustomTextField(
            value = "",
            onTextFieldChanged = { },
            label = "Nombre"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de texto para la edad
        CustomTextField(
            value = "",
            onTextFieldChanged = { },
            label = "Edad",
            isInt = true
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

