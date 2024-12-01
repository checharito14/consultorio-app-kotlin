package com.example.consultorioapp.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.consultorioapp.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loginState: LoginState by viewModel.loginState.observeAsState(initial = LoginState())
        val navigateToHome: Boolean by viewModel.navigateToHome.observeAsState(initial = false)

        if (navigateToHome) {
            // Navegamos a la pantalla principal y reseteamos el estado
            navController.navigate("home")
            viewModel.onNavigatedToHome() // Resetea el estado en el ViewModel
        }
        Header()
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            thickness = 2.dp
        )
        Text(
            "Accede con tu cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        EmailField(email, viewModel::onEmailChange)
        Spacer(modifier = Modifier.height(32.dp))
        PasswordField(password, viewModel::onPasswordChange)
        ForgetPassword(
            Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = {
                viewModel.login(auth)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text("Inicia sesion")
        }
    }
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Correo electrónico"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,  // Configura el teclado para email
        ),
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Contraseña"
            )
        },
        visualTransformation = PasswordVisualTransformation(), // Esto oculta el texto
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,  // Usa el teclado de contraseña
        ),
    )
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(34.dp),
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null
        )
        Text(
            "DocHub",
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ForgetPassword(modifier: Modifier) {
    Text(
        "Olvide mi contraseña",
        modifier = modifier.clickable { },
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}

