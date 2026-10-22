package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.ui.components.SeccionFoto
import com.datacore.kaijuapp.ui.theme.*
import com.datacore.kaijuapp.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarProducto(
    onGuardar: (Producto) -> Unit,
    onVolver: () -> Unit,
    viewModel: ProductoViewModel = viewModel()
) {
    val estado by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Agregar producto", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SeccionFoto(estado.fotoUri, viewModel::actualizarFotoUri)
            CampoTexto(estado.nombre, viewModel::actualizarNombre, "Nombre", estado.errores.errorNombre)
            CampoTexto(estado.descripcion, viewModel::actualizarDescripcion, "Descripción", null)
            CampoTexto(estado.categoria, viewModel::actualizarCategoria, "Categoría", estado.errores.errorCategoria)
            CampoTexto(estado.precio, viewModel::actualizarPrecio, "Precio", estado.errores.errorPrecio, KeyboardType.Decimal)
            CampoTexto(estado.stockMin, viewModel::actualizarStockMin, "Stock mínimo", estado.errores.errorStockMin, KeyboardType.Number)
            CampoTexto(estado.stockActual, viewModel::actualizarStockActual, "Stock actual", estado.errores.errorStockActual, KeyboardType.Number)
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KaijuYellow,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) { Text("Guardar", fontWeight = FontWeight.Bold) }

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, KaijuPurpleLight),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = KaijuPurpleLight)
            ) {
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
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = KaijuYellow,
            unfocusedBorderColor = KaijuPurple,
            focusedLabelColor = KaijuYellow,
            cursorColor = KaijuYellow,
            focusedTextColor = KaijuOnDark,
            unfocusedTextColor = KaijuOnDark
        )
    )
}