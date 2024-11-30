package com.example.consultorioapp.presentation.signup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consultorioapp.R
import com.example.consultorioapp.presentation.login.Header
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(26.dp),
    ) {
        Header()
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp), thickness = 2.dp)
        Text(
            "Crear cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it }, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Correo electrónico"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,  // Configura el teclado para email
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            )

        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
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
        Spacer(modifier = Modifier.height(30.dp))
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = {
//                Text(
//                    text = "Confirma tu contraseña"
//                )
//            }
//        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,

                )
        ) {
            Text("Registrarse")
        }
    }
}