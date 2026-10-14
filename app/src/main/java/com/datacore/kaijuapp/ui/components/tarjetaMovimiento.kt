package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//tarjeta para la clase de movimiento
@Composable
fun TarjetaMovimiento(
    id:String,
    codigoProducto: String,
    tipo: String,
    cantidad: Int,
    motivo:String,
    fecha:String,
    usuario:String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = id, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = codigoProducto)
            Text(text = tipo)
            Text(text = "Cantidad: $cantidad")
            Text(text = motivo)
            Text(text = fecha)
            Text(text = usuario)

        }
    }
}

