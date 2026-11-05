package com.datacore.kaijuapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.datacore.kaijuapp.naturalresources.notificaciones.NotificacionHelper
import com.datacore.kaijuapp.ui.screen.MainScreen
import com.datacore.kaijuapp.ui.theme.KaijuAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // El canal se crea una sola vez, fuera de la composición
        NotificacionHelper.crearCanal(this)

        setContent {
            SolicitarPermisoNotificaciones()
            KaijuAppTheme {
                MainScreen()
            }
        }
    }
}

// En Android 12 o menos el permiso no existe, así que no hace nada
@Composable
private fun SolicitarPermisoNotificaciones() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* si lo niega, mostrar() simplemente no muestra nada */ }

    LaunchedEffect(Unit) {
        if (!NotificacionHelper.tienePermiso(context)) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}