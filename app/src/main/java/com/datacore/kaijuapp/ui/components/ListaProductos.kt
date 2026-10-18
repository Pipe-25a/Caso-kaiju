package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.datacore.kaijuapp.model.Producto

@Composable
fun ListaProducto(
    productos: List<Producto>,
    modifier: Modifier = Modifier,
    onEditar: ((Producto) -> Unit)? = null,     // si es null, no se muestra el botón
    onEliminar: ((Producto) -> Unit)? = null    // si es null, no se muestra el botón
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(productos, key = { it.codigo }) { producto ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TarjetaProducto(
                        codigo = producto.codigo,
                        nombre = producto.nombre,
                        descripcion = producto.descripcion,
                        categoria = producto.categoria,
                        precio = producto.precio,
                        stockMax = producto.stockMax,
                        stockMin = producto.stockMin,
                        tipo = producto.tipo,
                        detalle = producto.detalle
                    )
                }
                if (onEditar != null) {
                    IconButton(onClick = { onEditar(producto) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
                if (onEliminar != null) {
                    IconButton(onClick = { onEliminar(producto) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}