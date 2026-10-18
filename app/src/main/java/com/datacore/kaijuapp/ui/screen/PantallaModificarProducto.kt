package com.datacore.kaijuapp.ui.screen
/*Pantalla para modificar producto
  el ID del producto no se puede modificar ya que se genera automaticamente y servira como
  llave primaria en la base de datos
  tambien tiene que tener un mensaje de confimacion al final de la modificacion del producto
*/
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.viewmodel.ProductoViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaModificarProducto(
    producto: Producto,
    onGuardar: (Producto) -> Unit,
    onVolver: () -> Unit,
    viewModel: ProductoViewModel = viewModel()
) {
    val estado by viewModel.estadoFormulario.collectAsState()
    val errores = estado.errores
    // Producto recién guardado: si no es null, se muestra el diálogo de confirmación
    var productoGuardado by remember { mutableStateOf<Producto?>(null) }
    // Carga el formulario al entrar. Se usa `producto` como clave (y no solo el código)
    // para que, tras guardar, el formulario se recargue con los datos nuevos
    // y no quede vacío detrás del diálogo.
    LaunchedEffect(producto) { viewModel.cargarProducto(producto) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modificar producto") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ID: generado automáticamente, no editable
            Campo(
                "ID", estado.codigo, {}, errores.errorCodigo,
                habilitado = false,
                ayuda = "Se genera automáticamente y no se puede modificar"
            )
            Campo("Nombre", estado.nombre, viewModel::actualizarNombre, errores.errorNombre)
            Campo("Descripción", estado.descripcion, viewModel::actualizarDescripcion, null)
            Campo("Categoría", estado.categoria, viewModel::actualizarCategoria, errores.errorCategoria)
            Campo("Tipo", estado.tipo, viewModel::actualizarTipo, errores.errorTipo)
            Campo(
                "Precio", estado.precio, viewModel::actualizarPrecio, errores.errorPrecio,
                teclado = KeyboardType.Decimal
            )
            Campo(
                "Stock mínimo", estado.stockMin, viewModel::actualizarStockMin, errores.errorStockMin,
                teclado = KeyboardType.Number
            )
            Campo(
                "Stock máximo", estado.stockMax, viewModel::actualizarStockMax, errores.errorStockMax,
                teclado = KeyboardType.Number
            )
            // Stock actual: solo cambia mediante movimientos
            Campo(
                "Stock actual", estado.stockActual, {}, errores.errorStockActual,
                habilitado = false,
                ayuda = "Solo cambia mediante movimientos"
            )
            Campo("Detalle", estado.detalle, viewModel::actualizarDetalle, null)

            Button(
                onClick = {
                    val modificado = viewModel.modificarProductoSiEsValido()
                    if (modificado != null) {
                        onGuardar(modificado)
                        productoGuardado = modificado   // abre el diálogo de confirmación
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) { Text("Guardar cambios") }
        }
    }
    // Mensaje de confirmación al terminar la modificación
    productoGuardado?.let { guardado ->
        AlertDialog(
            onDismissRequest = {
                productoGuardado = null
                onVolver()
            },
            title = { Text("Producto modificado") },
            text = {
                Text("Los cambios en \"${guardado.nombre}\" (ID ${guardado.codigo}) se guardaron correctamente.")
            },
            confirmButton = {
                TextButton(onClick = {
                    productoGuardado = null
                    onVolver()
                }) { Text("Aceptar") }
            }
        )
    }
}
@Composable
private fun Campo(
    label: String,
    valor: String,
    onCambio: (String) -> Unit,
    error: String?,
    teclado: KeyboardType = KeyboardType.Text,
    habilitado: Boolean = true,
    ayuda: String? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(label) },
        enabled = habilitado,
        isError = error != null,
        supportingText = (error ?: ayuda)?.let { { Text(it) } },
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}