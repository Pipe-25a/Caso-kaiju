package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
/*
Tuve que rehacer este parte del codigo porque se al momento de compilar da error

*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarProducto(
    onGuardar: (Producto) -> Unit,
    onVolver: () -> Unit,
    viewModel: ProductoViewModel = viewModel()
) {
    val estado by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Agregar producto") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CampoTexto(estado.nombre, viewModel::actualizarNombre, "Nombre", estado.errores.errorNombre)
            CampoTexto(estado.descripcion, viewModel::actualizarDescripcion, "Descripción", null)
            CampoTexto(estado.categoria, viewModel::actualizarCategoria, "Categoría", estado.errores.errorCategoria)
            CampoTexto(estado.precio, viewModel::actualizarPrecio, "Precio", estado.errores.errorPrecio, KeyboardType.Decimal)
            CampoTexto(estado.stockMin, viewModel::actualizarStockMin, "Stock mínimo", estado.errores.errorStockMin, KeyboardType.Number)
            CampoTexto(estado.stockMax, viewModel::actualizarStockMax, "Stock máximo", estado.errores.errorStockMax, KeyboardType.Number)
            CampoTexto(estado.tipo, viewModel::actualizarTipo, "Tipo", estado.errores.errorTipo)
            CampoTexto(estado.detalle, viewModel::actualizarDetalle, "Detalle (talla, color, lote...)", null)

            Button(
                onClick = {
                    val producto = viewModel.crearProductoSiEsValido()
                    if (producto != null) {
                        onGuardar(producto)
                        onVolver()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar") }

            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
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
    teclado: KeyboardType = KeyboardType.Text) {
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