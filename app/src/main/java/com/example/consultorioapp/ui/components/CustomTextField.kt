package com.example.consultorioapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Error

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onTextFieldChanged: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    error: String? = null,
    isInt: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth(),
//            .height(50.dp),
        label = {
            Text(
                label,
                style = TextStyle(fontSize = 14.sp),
                color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isPassword) KeyboardType.Email else if (isInt) KeyboardType.Number else KeyboardType.Password
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = if (error != null) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.onBackground,
            focusedBorderColor = if (error != null) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
        ),
        textStyle = TextStyle(fontSize = 12.sp)
    )
    if (error != null) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = error ?: "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 10.sp
        )
    }

}