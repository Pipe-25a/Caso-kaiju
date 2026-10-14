package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Movimiento

// Solo se ven los detalles de UN movimiento ya guardado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetallesMovimiento(
    movimiento: Movimiento,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles del movimiento") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            CampoDetalle("Código del producto", movimiento.codigoProducto)
            CampoDetalle("Tipo", movimiento.tipo)
            CampoDetalle("Cantidad", movimiento.cantidad.toString())
            CampoDetalle("Motivo", movimiento.motivo)
            CampoDetalle("Fecha", movimiento.fecha)
            CampoDetalle("Usuario", movimiento.usuario)

            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}

@Composable
fun CampoDetalle(etiqueta: String, valor: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            etiqueta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(valor.ifBlank { "—" }, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 6.dp))
    }
}