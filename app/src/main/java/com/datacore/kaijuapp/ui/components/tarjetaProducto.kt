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
fun TarjetaProducto(
    codigo: String,
    nombre: String,
    descripcion: String,
    categoria: String,
    precio: Double,
    stockMax: Int,
    stockMin: Int,
    tipo: String,
    detalle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = codigo, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = nombre)
            Text(text = descripcion)
            Text(text = categoria)
            Text(text = "Precio: $precio", color = Color.Green)
            Text(text = "Stock mínimo: $stockMin")
            Text(text = "Stock máximo: $stockMax")
            Text(text = tipo, color = Color.Blue)
            Text(text = detalle)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTarjetaProducto() {
    TarjetaProducto(
        codigo = "KJ-001",
        nombre = "Producto de ejemplo",
        descripcion = "Descripción de prueba",
        categoria = "Categoría",
        precio = 1990.0,
        stockMax = 100,
        stockMin = 10,
        tipo = "Tipo",
        detalle = "Detalle de ejemplo"
    )
}