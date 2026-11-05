package com.datacore.kaijuapp.naturalresources.galeria

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Recurso nativo: selector de fotos de la galería (Photo Picker de Android).
 * No necesita permisos en el manifest.
 */
class SelectorFotos(private val abrir: () -> Unit) {
    fun abrirGaleria() = abrir()
}

/**
 * Registra el launcher de la galería dentro del propio composable.
 * Cuando el usuario elige una foto, la copia al almacenamiento interno de la app
 * (la Uri de la galería es temporal) y entrega la Uri persistente como String.
 */
@Composable
fun rememberSelectorFotos(onFotoSeleccionada: (String) -> Unit): SelectorFotos {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val callback by rememberUpdatedState(onFotoSeleccionada)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                val copia = withContext(Dispatchers.IO) { copiarAAlmacenamientoInterno(context, uri) }
                callback((copia ?: uri).toString())
            }
        }
    }

    return remember(launcher) {
        SelectorFotos {
            launcher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}

private fun copiarAAlmacenamientoInterno(context: Context, uri: Uri): Uri? = try {
    val carpeta = File(context.filesDir, "fotos_productos").apply { mkdirs() }
    val archivo = File(carpeta, "producto_${System.currentTimeMillis()}.jpg")
    context.contentResolver.openInputStream(uri)?.use { entrada ->
        archivo.outputStream().use { salida -> entrada.copyTo(salida) }
    }
    Uri.fromFile(archivo)
} catch (e: Exception) {
    null
}