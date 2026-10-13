package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.datacore.kaijuapp.notificaciones.NotificacionHelper
import com.datacore.kaijuapp.repository.DatosDePrueba
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    nombreUsuario: String,
    rol: String,
    onProductos: () -> Unit,
    onAgregar: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val context = LocalContext.current
    val launcherPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* aquí podrías avisar si el usuario lo rechazó */ }

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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Hola, $nombreUsuario", style = MaterialTheme.typography.headlineSmall)
            Text("Rol: $rol", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))

            Button(onClick = onProductos, modifier = Modifier.fillMaxWidth()) {
                Text("Ver productos")
            }
            Button(onClick = onAgregar, modifier = Modifier.fillMaxWidth()) {
                Text("Agregar producto")
            }
            // Placeholder hasta que exista la pantalla de movimientos
            Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                Text("Movimientos (próximamente)")
            }
            //notificacion prueba
            OutlinedButton(
                onClick = {
                    NotificacionHelper.mostrarStockBajo(context, DatosDePrueba.productos.first())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Probar notificación")
            }


            Spacer(Modifier.weight(1f))  // empuja "Cerrar sesión" hacia abajo

            OutlinedButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth()) {
                Text("Cerrar sesión")
            }
        }
    }
}