package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
    onAgregar: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Kaiju App") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregar) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { innerPadding ->
        ListaProducto(
            productos = productos,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
/*
se cambio porque ya no se utiliza
 */
