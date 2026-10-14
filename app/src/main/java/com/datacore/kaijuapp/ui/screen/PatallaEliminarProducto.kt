package com.datacore.kaijuapp.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.viewmodel.ProductoViewModel

// Pantalla para eliminar un producto (se busca por código)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEliminarProducto(
    onEliminar: (Producto) -> Unit,
    onVolver: () -> Unit,
    viewModel: ProductoViewModel = viewModel()
) {
    val estado by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Eliminar Producto") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CampoTexto(
                estado.codigo,
                viewModel::actualizarCodigo,
                "Código",
                estado.errores.errorCodigo
            )

            Button(
                onClick = {
                    val producto = viewModel.eliminarProductoSiEsValido()
                    if (producto != null) {
                        onEliminar(producto)
                        onVolver()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Eliminar") }

            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}

@Composable
private fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        modifier = Modifier.fillMaxWidth()
    )
}