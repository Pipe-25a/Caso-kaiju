package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Movimiento

@Composable
fun ListaMovimiento(
    movimientos: List<Movimiento>,
    modifier: Modifier = Modifier,
    onSeleccionar: (Movimiento) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(movimientos, key = { it.id }) { movimiento ->
            TarjetaMovimiento(
                id = movimiento.id,
                codigoProducto = movimiento.codigoProducto,
                tipo = movimiento.tipo,
                cantidad = movimiento.cantidad,
                motivo = movimiento.motivo,
                fecha = movimiento.fecha,
                usuario = movimiento.usuario,
                onClick = { onSeleccionar(movimiento) }
            )
        }
    }
}