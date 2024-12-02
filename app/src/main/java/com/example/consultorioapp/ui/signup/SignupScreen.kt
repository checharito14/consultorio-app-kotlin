package com.example.consultorioapp.ui.signup


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.consultorioapp.ui.components.ConsultorioHeader
import com.example.consultorioapp.ui.components.CustomTextField
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, auth: FirebaseAuth, viewModel: SignupViewModel) {

    val especialidades = listOf(
        "Cardiología",
        "Dermatología",
        "Ginecología",
        "Pediatría",
        "Neurología",
        "Ortopedia",
        "Oftalmología",
        "Psiquiatría"
    )

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val name: String by viewModel.name.observeAsState(initial = "")
    val especialidad: String by viewModel.especialidad.observeAsState(initial = "Selecciona una especialidad")

    val navigateToHome: Boolean by viewModel.navigateToHome.observeAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(26.dp),
    ) {
        if (navigateToHome) {
            // Navegamos a la pantalla principal y reseteamos el estado
            navController.navigate("home")
            viewModel.onNavigatedToHome() // Resetea el estado en el ViewModel
        }
        ConsultorioHeader()
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            thickness = 2.dp
        )
        Text(
            "Crear cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        CustomTextField(
            value = name,
            onTextFieldChanged = viewModel::onNameChange,
            label = "Nombre completo"
        )
        Spacer(modifier = Modifier.height(30.dp))
        EspecialidadDropdown(
            especialidades = especialidades,
            selectedEspecialidad = especialidad,
            onEspecialidadSelected = viewModel::onEspecialidadChange
        )
        Spacer(modifier = Modifier.height(30.dp))
        //Email
        CustomTextField(
            value = email,
            onTextFieldChanged = viewModel::onEmailChange,
            label = "Correo electronico"
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            value = password,
            onTextFieldChanged = viewModel::onPasswordChange,
            label = "Contraseña",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.register()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text("Registrarse")
        }
    }
}
@Composable
fun EspecialidadDropdown(
    especialidades: List<String>,
    selectedEspecialidad: String,
    onEspecialidadSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    // Usar un Box para manejar la clicabilidad manualmente
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedEspecialidad,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable{ expanded = !expanded },  // Hacer clic sin cambiar el color
            label = { Text(text = "Selecciona tu especialidad") },
            trailingIcon = {
                Icon(icon, "", Modifier.clickable { expanded = !expanded })
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
        ) {
            especialidades.forEach { especialidad ->
                DropdownMenuItem(
                    onClick = {
                        onEspecialidadSelected(especialidad)
                        expanded = false
                    },
                    text = {
                        Text(text = especialidad)
                    }
                )
            }
        }
    }
}

