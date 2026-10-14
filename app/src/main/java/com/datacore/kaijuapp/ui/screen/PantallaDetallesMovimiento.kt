package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.viewmodel.MovimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Solo se veran los detalle del movimiento

fun PantallaDetallesMovimiento(

    onVolver: () -> Unit,
    viewModel: MovimientoViewModel
) {
    val estado by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles del movimiento") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            //movimiento

            CampoDetalle("Código del producto", estado.codigoProducto)
            CampoDetalle("Tipo", estado.tipo)
            CampoDetalle("Cantidad", estado.cantidad.toString())
            CampoDetalle("Motivo", estado.motivo)
            CampoDetalle("Fecha", estado.fecha)
            CampoDetalle("Usuario", estado.usuario)
            //botones
            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }

        }
    }

}

@Composable
fun CampoDetalle(x0: String, x1: String) {
    TODO("Not yet implemented")
}