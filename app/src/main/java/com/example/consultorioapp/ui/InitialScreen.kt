package com.example.consultorioapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consultorioapp.R
import com.example.consultorioapp.ui.components.ConsultorioHeader
import com.example.consultorioapp.ui.theme.AppTheme


@Composable
fun InitialScreen(navigateToLogin: () -> Unit = {}, navigateToSignUp: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        ConsultorioHeader()
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Gestion medica",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            "al instante",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateToSignUp() }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Registrate", color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        CustomButton(Modifier.clickable { }, "google", painterResource(id = R.drawable.google))
        Spacer(modifier = Modifier.height(6.dp))
        CustomButton(Modifier.clickable { }, "facebook", painterResource(id = R.drawable.facebook))
        Text("Inicia sesion", modifier = Modifier
            .padding(26.dp)
            .clickable { navigateToLogin() },
            color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomButton(modifier: Modifier = Modifier, text: String, painter: Painter) {
    Surface(
        modifier = modifier
            .padding(horizontal = 32.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        shadowElevation = 5.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.shapes.medium,
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(16.dp)
            )
            Text(
                "Inicia con $text",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    AppTheme {
        InitialScreen()
    }
}