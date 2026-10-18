package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    nombreUsuario: String,
    rol: String,
    onCatalogo: () -> Unit,
    onMovimientos: () -> Unit,
    onUsuarios: (() -> Unit)? = null,
    onCerrarSesion: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú principal") },
                actions = {
                    TextButton(onClick = onCerrarSesion) {
                        Text("Cerrar sesión")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Hola, $nombreUsuario", style = MaterialTheme.typography.headlineMedium)
            Text("Rol: $rol", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))

            Button(onClick = onCatalogo, modifier = Modifier.fillMaxWidth()) {
                Text("Catálogo")
            }
            Button(onClick = onMovimientos, modifier = Modifier.fillMaxWidth()) {
                Text("Movimientos")
            }
            Button(
                onClick = { onUsuarios?.invoke() },
                enabled = onUsuarios != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (onUsuarios != null) "Usuarios" else "Usuarios (próximamente)")
            }
        }
    }
}