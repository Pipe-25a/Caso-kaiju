package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.datacore.kaijuapp.model.KaijuEasterEggEstado

@Composable
fun KaijuEasterEggDialog(
    estado: KaijuEasterEggEstado,
    onReintentar: () -> Unit,
    onCerrar: () -> Unit
) {
    if (!estado.visible) return
    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text("¡Un Kaiju apareció! 🦖") },
        confirmButton = {
            if (estado.error != null) {
                TextButton(onClick = onReintentar) { Text("Reintentar") }
            } else {
                TextButton(onClick = onCerrar) { Text("Cerrar") }
            }
        },
        dismissButton = {
            if (estado.error != null) {
                TextButton(onClick = onCerrar) { Text("Cerrar") }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when {
                    estado.cargando -> {
                        CircularProgressIndicator()
                        Text("Invocando...")
                    }
                    estado.error != null -> {
                        Text(estado.error, color = MaterialTheme.colorScheme.error)
                    }
                    estado.pokemon != null -> {
                        val p = estado.pokemon
                        val imagen = p.sprites.other?.officialArtwork?.frontDefault
                            ?: p.sprites.frontDefault
                        if (imagen == null) {
                            Text("Imagen no disponible")
                        } else {
                            AsyncImage(
                                model = imagen,
                                contentDescription = "Imagen de ${p.name}",
                                error = painterResource(android.R.drawable.ic_menu_report_image),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(200.dp)
                            )
                        }
                        Text(
                            "#${p.id} · ${p.name.replaceFirstChar { it.uppercase() }}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Tipos: " + p.types.sortedBy { it.slot }
                                .joinToString { it.type.name }
                        )
                    }
                }
            }
        }
    )
}