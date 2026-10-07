package com.datacore.kaijuapp.ui.screen
import androidx.compose.*;
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


OutlinedTextField(
    value = estado.titulo,
    onValueChange = viewModel::actualizarTitulo,
    label = { Text("Título") },
    // isError = true pone el borde rojo; supportingText dibuja el mensaje debajo
    isError = estado.errores.errorTitulo != null,
    supportingText = { estado.errores.errorTitulo?.let { Text(it) } },
    singleLine = true,
    modifier = Modifier.fillMaxWidth()
)