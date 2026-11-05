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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.ui.components.HomeHeader
import com.datacore.kaijuapp.ui.theme.KaijuOnDark
import com.datacore.kaijuapp.ui.theme.KaijuPurple
import com.datacore.kaijuapp.ui.theme.KaijuYellow
import com.datacore.kaijuapp.viewmodel.SesionViewModel

@Composable
fun PantallaLogin(
    viewModel: SesionViewModel,
    onLoginExitoso: () -> Unit
) {
    val estado by viewModel.formulario.collectAsState()
    var mostrarClave by remember { mutableStateOf(false) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeHeader()
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = estado.usuario,
                onValueChange = viewModel::actualizarUsuario,
                label = { Text("Usuario") },
                isError = estado.errores.errorUsuario != null,
                supportingText = { estado.errores.errorUsuario?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KaijuYellow,
                    unfocusedBorderColor = KaijuPurple,
                    focusedLabelColor = KaijuYellow,
                    cursorColor = KaijuYellow,
                    focusedTextColor = KaijuOnDark,
                    unfocusedTextColor = KaijuOnDark
                )
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
                            else "Mostrar contraseña",
                            tint = KaijuYellow
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KaijuYellow,
                    unfocusedBorderColor = KaijuPurple,
                    focusedLabelColor = KaijuYellow,
                    cursorColor = KaijuYellow,
                    focusedTextColor = KaijuOnDark,
                    unfocusedTextColor = KaijuOnDark
                )
            )

            estado.errores.errorGeneral?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { if (viewModel.iniciarSesion()) onLoginExitoso() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KaijuYellow,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold)
            }
        }
    }
}