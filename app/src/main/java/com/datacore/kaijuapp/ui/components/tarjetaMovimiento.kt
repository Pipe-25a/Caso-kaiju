package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//tarjeta para la clase de movimiento (ahora se puede tocar)
@Composable
fun TarjetaMovimiento(
    id: String,
    codigoProducto: String,
    tipo: String,
    cantidad: Int,
    motivo: String,
    fecha: String,
    usuario: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Movimiento #$id", fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = "Producto: $codigoProducto")
            Text(text = tipo)
            Text(text = "Cantidad: $cantidad")
            Text(text = fecha)
        }
    }
}