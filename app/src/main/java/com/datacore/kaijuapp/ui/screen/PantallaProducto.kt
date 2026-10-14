package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.ui.components.ListaProducto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProducto(
    productos: List<Producto>,
    onAgregar: () -> Unit,
    onVolver: () -> Unit,
    puedeAgregar: Boolean = true   // solo el Encargado de Inventario ve el botón +
) {
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
            if (puedeAgregar) {
                FloatingActionButton(onClick = onAgregar) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar producto")
                }
            }
        }
    ) { innerPadding ->
        ListaProducto(
            productos = productos,
            modifier = Modifier.padding(innerPadding)
        )
    }
}