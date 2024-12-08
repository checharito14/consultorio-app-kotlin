package com.example.consultorioapp.ui.citas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.RadioGroup
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.consultorioapp.data.models.Paciente
import com.example.consultorioapp.ui.components.CustomTextField
import com.example.consultorioapp.ui.pacientes.PacientesDropdown
import com.example.consultorioapp.ui.pacientes.PacientesViewModel
import com.example.consultorioapp.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Composable
fun AddCitaDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text("Confirmar")
                }
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Agendar cita", style = MaterialTheme.typography.headlineMedium)
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }
            },
            text = {
                CitaForm()
            }
        )

    }
}

@Composable
fun CitaForm() {

    val pacientesViewModel: PacientesViewModel = hiltViewModel()
//    var selectedDate
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var descripcion by remember { mutableStateOf("") }
    var selectedPaciente by remember { mutableStateOf("") }

    var expandedDatePicker by remember { mutableStateOf(false) }
    var expandedTimePicker by remember { mutableStateOf(false) }

    if (expandedDatePicker) {
        DatePickerDialog(
            onDateSelected = { year, month, day ->
                selectedDate = "$day/${month + 1}/$year"
                expandedDatePicker = false
            }
        )
    }

    if (expandedTimePicker) {
        TimePickerDialog(
            onHourSelected = { hour ->
                selectedHour = "$hour:00"
                expandedTimePicker = false
            }
        )
    }

    HorizontalDivider(
        modifier = Modifier
            .padding(bottom = 18.dp)
            .fillMaxWidth(),
        thickness = 2.dp
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        PacientesDropdown(onPacienteSelected = { paciente ->
            selectedPaciente = paciente.id
        })
        Spacer(Modifier.height(8.dp))
        CustomTextField(
            value = descripcion,
            label = "Motivo de la cita",
            onTextFieldChanged = { descripcion = it },
        )
        Spacer(Modifier.height(8.dp))
        Text("Fecha:")
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = MaterialTheme.shapes.extraSmall,
            onClick = { expandedDatePicker = true }) {
            Text(
                text = selectedDate ?: "Selecciona la fecha",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.height(8.dp))
        Text("Hora:")
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp),
            shape = MaterialTheme.shapes.extraSmall,
            onClick = { expandedTimePicker = true }) {
            Text(
                text = selectedHour ?: "Selecciona la hora",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )
        }
    }


}

@Composable
fun DatePickerDialog(onDateSelected: (Int, Int, Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
        },
        year,
        month,
        day
    )
    datePicker.show()
}

@Composable
fun TimePickerDialog(onHourSelected: (Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    val availableHours = (9..18).toList()

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour, _ ->
            onHourSelected(selectedHour)
        },
        hour,
        0,
        true
    )

    timePicker.show()
}

@Preview
@Composable
private fun CitaFormPreview() {
    AppTheme {
        CitaForm()
    }
}