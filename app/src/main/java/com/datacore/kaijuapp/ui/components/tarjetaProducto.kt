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

@Composable
fun TarjetaProducto(nombre: String, fechaVencimiento: String, cantidad:String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = nombre, fontWeight = FontWeight.Bold)
            Text(text = fechaVencimiento, color = Color.Gray)
            Text(text = cantidad, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTarjetaProducto() {
    TarjetaProducto(nombre = "Papas", fechaVencimiento = "Lun 22 sept", cantidad = "20")
}