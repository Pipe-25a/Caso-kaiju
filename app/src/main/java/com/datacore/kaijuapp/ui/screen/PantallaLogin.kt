package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.viewmodel.SesionViewModel

@Composable
fun PantallaLogin(
    viewModel: SesionViewModel,
    onLoginExitoso: () -> Unit
) {
    val estado by viewModel.formulario.collectAsState()
    var mostrarClave by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kaiju App", style = MaterialTheme.typography.headlineLarge)
            Text("Inicia sesión para continuar", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(
                value = estado.usuario,
                onValueChange = viewModel::actualizarUsuario,
                label = { Text("Usuario") },
                isError = estado.errores.errorUsuario != null,
                supportingText = { estado.errores.errorUsuario?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::actualizarClave,
                label = { Text("Contraseña") },
                isError = estado.errores.errorClave != null,
                supportingText = { estado.errores.errorClave?.let { Text(it) } },
                singleLine = true,
                visualTransformation = if (mostrarClave) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { mostrarClave = !mostrarClave }) {
                        Icon(
                            imageVector = if (mostrarClave) Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility,
                            contentDescription = if (mostrarClave) "Ocultar contraseña"
                            else "Mostrar contraseña"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            // Error general: credenciales incorrectas
            estado.errores.errorGeneral?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { if (viewModel.iniciarSesion()) onLoginExitoso() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}