package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.ui.components.ListaProducto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaProductos(
    productos: List<Producto>,
    onAgregar: () -> Unit,
    onEditar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onVolverInicio: () -> Unit,
    onVerMovimientos: () -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf<String?>(null) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    val tipos = remember(productos) { productos.map { it.tipo }.distinct().sorted() }

    val productosFiltrados = remember(productos, busqueda, tipoSeleccionado) {
        productos.filter { p ->
            val coincideBusqueda = busqueda.isBlank() ||
                    p.nombre.contains(busqueda, ignoreCase = true) ||
                    p.codigo.contains(busqueda, ignoreCase = true)
            val coincideTipo = tipoSeleccionado == null || p.tipo == tipoSeleccionado
            coincideBusqueda && coincideTipo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos") },
                navigationIcon = {
                    IconButton(onClick = onVolverInicio) {
                        Icon(Icons.Default.Home, contentDescription = "Volver al inicio")
                    }
                },
                actions = {
                    IconButton(onClick = onVerMovimientos) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = "Ver movimientos")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregar) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar por nombre o código") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = tipoSeleccionado == null,
                        onClick = { tipoSeleccionado = null },
                        label = { Text("Todos") }
                    )
                }
                items(tipos) { tipo ->
                    FilterChip(
                        selected = tipoSeleccionado == tipo,
                        onClick = { tipoSeleccionado = tipo },
                        label = { Text(tipo) }
                    )
                }
            }

            ListaProducto(
                productos = productosFiltrados,
                onEditar = onEditar,
                onEliminar = { productoAEliminar = it }
            )
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