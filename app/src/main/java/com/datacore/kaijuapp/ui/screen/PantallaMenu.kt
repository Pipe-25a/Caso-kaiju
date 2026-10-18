package com.datacore.kaijuapp.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.notificaciones.NotificacionHelper
import com.datacore.kaijuapp.repository.DatosDePrueba

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    nombreUsuario: String,
    rol: String,
    productos: List<Producto>,       // <-- NUEVO, en este mismo orden
    onCatalogo: () -> Unit,
    onAgregar: () -> Unit,
    onEliminar: () -> Unit,
    onMovimientos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val context = LocalContext.current
    val launcherPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (!NotificacionHelper.tienePermiso(context)) {
            launcherPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Menú principal") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Hola, $nombreUsuario", style = MaterialTheme.typography.headlineSmall)
            Text("Rol: $rol", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))


            /*
            Botones según el rol falta corregir los roles y eliminar el rol
            de EncargadoIventario ya que es lo mismo que el rol de administrador
            */
            when (rol) {
                "Administrador" -> {
                    BotonMenu("Gestión de usuarios (próximamente)", onClick = {}, habilitado = false)
                    BotonMenu("Revisar catálogo", onCatalogo)
                    BotonMenu("Revisar movimientos", onMovimientos)
                    BotonMenu("Ver catálogo", onCatalogo)
                    BotonMenu("Agregar producto", onAgregar)
                    BotonMenu("Eliminar producto", onEliminar)
                    BotonMenu("Modificar producto (próximamente)", onClick = {}, habilitado = false)
                }
                "Vendedor" -> {
                    BotonMenu("Ver catálogo", onCatalogo)
                    BotonMenu("Ver movimientos", onMovimientos)
                }
                else -> BotonMenu("Ver catálogo", onCatalogo)
            }
            // Botón de prueba de notificación
            OutlinedButton(
                onClick = {
                    NotificacionHelper.mostrarStockBajo(context, DatosDePrueba.productos.first())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Probar notificación")
            }

            Spacer(Modifier.height(24.dp))

            OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth()) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
private fun BotonMenu(texto: String, onClick: () -> Unit, habilitado: Boolean = true) {
    Button(onClick = onClick, enabled = habilitado, modifier = Modifier.fillMaxWidth()) {
        Text(texto)
    }
}
