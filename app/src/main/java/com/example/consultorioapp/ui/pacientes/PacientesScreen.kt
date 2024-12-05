package com.example.consultorioapp.ui.pacientes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consultorioapp.R
import com.example.consultorioapp.ui.theme.AppTheme

@Composable
fun PacientesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(30.dp)
            .paddingFromBaseline(top = 70.dp)
    ) {
        PacienteSection {
            PacienteCard()
        }
    }
}


@Composable
fun PacienteSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = "Pacientes",
            style = MaterialTheme.typography.displaySmall,
        )
        content()
    }
}

@Composable
fun PacienteCard(modifier: Modifier = Modifier) {
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
                Image(
                    painter = painterResource(id = R.drawable.ab1_inversions),
                    contentDescription = "Paciente",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = "Cesar Rice", style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Edad: ", style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                thickness = 2.dp
            )
            Text(
                text = "Ultima cita: ",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                EditButton(Modifier.weight(1f))
                DeleteButton(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun EditButton(modifier: Modifier) {
    OutlinedButton(
        onClick = {},
        modifier = modifier
            .padding(end = 8.dp)
            .height(35.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text("Editar")
    }
}

@Composable
fun DeleteButton(modifier: Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .padding(end = 8.dp)
            .height(35.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    ) {
        Text("Eliminar")
    }
}


@Preview(showBackground = true)
@Composable
private fun PacienteCardPreview() {
    AppTheme {
        PacienteCard()
    }
}