package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Producto
import java.text.NumberFormat
import java.util.Locale

/**
 * Catálogo completo: búsqueda, filtro por tipo, agregar (FAB), modificar y eliminar.
 * Supuestos sobre Producto: nombre, codigo (String), tipo, precio (Double), stockActual.
 * Si tu campo se llama distinto, cámbialo en los 4 lugares donde aparece.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(
    productos: List<Producto>,
    puedeEditar: Boolean,
    onVolver: () -> Unit,
    onAgregar: () -> Unit,
    onEditar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
) {
    var busqueda by rememberSaveable { mutableStateOf("") }
    var tipoSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    val tipos = productos.map { it.tipo.toString() }.distinct().sorted()
    // Si se elimina el último producto de un tipo, el filtro vuelve a "Todos"
    val tipoActivo = tipoSeleccionado?.takeIf { it in tipos }

    val filtrados = productos.filter { p ->
        (tipoActivo == null || p.tipo.toString() == tipoActivo) &&
                (busqueda.isBlank() ||
                        p.nombre.contains(busqueda, ignoreCase = true) ||
                        p.codigo.contains(busqueda, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            if (puedeEditar) {
                FloatingActionButton(onClick = onAgregar) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar producto")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar por nombre o código") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (tipos.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = tipoActivo == null,
                            onClick = { tipoSeleccionado = null },
                            label = { Text("Todos") }
                        )
                    }
                    items(tipos) { tipo ->
                        FilterChip(
                            selected = tipoActivo == tipo,
                            onClick = { tipoSeleccionado = tipo },
                            label = { Text(tipo) }
                        )
                    }
                }
            }

            when {
                productos.isEmpty() -> MensajeVacio("Aún no hay productos en el catálogo.")
                filtrados.isEmpty() -> MensajeVacio("Sin resultados para tu búsqueda.")
                else -> LazyColumn(
                    // espacio abajo para que el FAB no tape la última tarjeta
                    contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 96.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filtrados, key = { it.codigo }) { producto ->
                        TarjetaCatalogo(
                            producto = producto,
                            puedeEditar = puedeEditar,
                            onEditar = { onEditar(producto) },
                            onEliminar = { productoAEliminar = producto }
                        )
                    }
                }
            }
        }
    }

    productoAEliminar?.let { producto ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Seguro que quieres eliminar \"${producto.nombre}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    onEliminar(producto)
                    productoAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { productoAEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun TarjetaCatalogo(
    producto: Producto,
    puedeEditar: Boolean,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
) {
    val precio = NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(producto.precio)

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Código: ${producto.codigo} · ${producto.tipo}", style = MaterialTheme.typography.bodySmall)
                Text("$precio · Stock: ${producto.stockActual}", style = MaterialTheme.typography.bodyMedium)
            }
            if (puedeEditar) {
                IconButton(onClick = onEditar) {
                    Icon(Icons.Default.Edit, contentDescription = "Modificar")
                }
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

@Composable
private fun MensajeVacio(texto: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(texto, style = MaterialTheme.typography.bodyLarge)
    }
}