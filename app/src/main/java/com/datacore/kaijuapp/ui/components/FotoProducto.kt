package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.datacore.kaijuapp.naturalresources.galeria.rememberSelectorFotos

/** Miniatura del producto (catálogo). Si no hay foto, muestra un recuadro "Sin foto". */
@Composable
fun FotoProducto(fotoUri: String?, modifier: Modifier = Modifier) {
    val forma = RoundedCornerShape(8.dp)
    if (fotoUri != null) {
        AsyncImage(
            model = fotoUri,
            contentDescription = "Foto del producto",
            modifier = modifier.clip(forma),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier
                .clip(forma)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("Sin foto", style = MaterialTheme.typography.labelSmall)
        }
    }
}

/** Sección de foto para los formularios (Agregar y Modificar): previsualización + botón. */
@Composable
fun SeccionFoto(
    fotoUri: String?,
    onFotoSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selector = rememberSelectorFotos(onFotoSeleccionada)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FotoProducto(
            fotoUri = fotoUri,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        OutlinedButton(
            onClick = { selector.abrirGaleria() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (fotoUri == null) "Subir foto" else "Cambiar foto")
        }
    }
}