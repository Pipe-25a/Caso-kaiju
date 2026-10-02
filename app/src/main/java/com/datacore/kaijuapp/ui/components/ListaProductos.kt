package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.datacore.kaijuapp.model.Producto


// TarjetaProducto no se importa: vive en este mismo paquete (ui.components),
// y Kotlin no exige import entre archivos del mismo paquete.
@Composable
fun ListaProducto(productos: List<Producto>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(productos) { producto ->
            TarjetaProducto(nombre = producto.nombre, fechaVencimiento = producto.fechaVencimiento, cantidad = producto.cantidad)
        }
    }
}