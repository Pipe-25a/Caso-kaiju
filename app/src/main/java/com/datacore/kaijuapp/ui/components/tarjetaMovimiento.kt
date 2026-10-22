package com.datacore.kaijuapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.ui.theme.KaijuDarkPurple
import com.datacore.kaijuapp.ui.theme.KaijuGrayText
import com.datacore.kaijuapp.ui.theme.KaijuOnDark
import com.datacore.kaijuapp.ui.theme.KaijuPurple
import com.datacore.kaijuapp.ui.theme.KaijuPurpleLight
import com.datacore.kaijuapp.ui.theme.KaijuYellow

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
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = KaijuDarkPurple,
            contentColor = KaijuOnDark
        ),
        border = BorderStroke(1.dp, KaijuPurple)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Movimiento #$id",
                fontWeight = FontWeight.Bold,
                color = KaijuYellow
            )
            Text(text = "Producto: $codigoProducto", color = KaijuOnDark)
            Text(text = tipo, color = KaijuPurpleLight)
            Text(text = "Cantidad: $cantidad", color = KaijuOnDark)
            Text(text = fecha, color = KaijuGrayText)
        }
    }
}