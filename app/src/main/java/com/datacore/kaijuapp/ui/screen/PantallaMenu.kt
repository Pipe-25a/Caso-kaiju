package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    usuario: Usuario?,
    onCatalogo: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú") },
                actions = {
                    IconButton(onClick = onCerrarSesion) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (usuario != null) {
                Text("Hola, ${usuario.nombre}", style = MaterialTheme.typography.headlineSmall)
                Text("Rol: ${usuario.rol}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
            }

            OpcionMenu(
                titulo = "Catálogo de productos",
                habilitada = true,
                onClick = onCatalogo
            )
            Spacer(Modifier.height(8.dp))
            OpcionMenu(
                titulo = "Movimientos",
                habilitada = false,
                onClick = {}
            )

            if (usuario?.esAdministrador == true) {
                Spacer(Modifier.height(8.dp))
                OpcionMenu(
                    titulo = "Gestión de usuarios",
                    habilitada = false,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun OpcionMenu(
    titulo: String,
    habilitada: Boolean,
    onClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = {
                Text(
                    text = if (habilitada) titulo else "$titulo (próximamente)",
                    color = if (habilitada) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            },
            supportingContent = if (habilitada) null else {
                { Text("Próximamente") }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = habilitada, onClick = onClick)
        )
    }
}
