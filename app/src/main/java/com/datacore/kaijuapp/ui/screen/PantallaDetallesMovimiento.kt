package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Movimiento
import com.datacore.kaijuapp.ui.theme.KaijuDarkPurple
import com.datacore.kaijuapp.ui.theme.KaijuOnDark
import com.datacore.kaijuapp.ui.theme.KaijuPurple
import com.datacore.kaijuapp.ui.theme.KaijuPurpleLight
import com.datacore.kaijuapp.ui.theme.KaijuYellow

// Solo se ven los detalles de UN movimiento ya guardado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetallesMovimiento(
    movimiento: Movimiento,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del movimiento", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KaijuDarkPurple,
                    titleContentColor = KaijuYellow
                )
            )
        }
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

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, KaijuPurpleLight),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = KaijuPurpleLight)
            ) {
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
            color = KaijuYellow
        )
        Text(valor.ifBlank { "—" }, style = MaterialTheme.typography.bodyLarge, color = KaijuOnDark)
        HorizontalDivider(modifier = Modifier.padding(top = 6.dp), color = KaijuPurple.copy(alpha = 0.5f))
    }
}