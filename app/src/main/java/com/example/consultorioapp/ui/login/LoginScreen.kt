package com.example.consultorioapp.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.consultorioapp.R
import com.example.consultorioapp.ui.components.ConsultorioHeader
import com.example.consultorioapp.ui.components.CustomTextField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp).padding( horizontal = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConsultorioHeader()
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 24.dp ),
            thickness = 2.dp
        )
        Text(
            "Accede con tu cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(vertical = 18.dp),
            color = Color.Black
        )
        //Email
        CustomTextField(
            value = uiState.email,
            onTextFieldChanged = viewModel::onEmailChange,
            label = "Correo electrónico",
            error = uiState.emailError ?: null
        )
        Spacer(modifier = Modifier.height(32.dp))
        //Password
        CustomTextField(
            value = uiState.password,
            onTextFieldChanged = viewModel::onPasswordChange,
            label = "Contraseña",
            isPassword = true,
            error = uiState.passwordError ?: null
        )
        ForgetPassword(
            Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = {
//                viewModel.login()
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            shape = RoundedCornerShape(0.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.primary)
            } else {
                Text("Inicia sesión")
            }
        }

        if(uiState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 10.sp
            )
        }
    }
    LaunchedEffect(uiState.navigateToHome) {
        if (uiState.navigateToHome) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.onNavigatedToHome()
        }
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

