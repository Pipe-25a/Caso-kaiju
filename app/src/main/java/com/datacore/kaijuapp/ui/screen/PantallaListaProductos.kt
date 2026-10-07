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

private val listaDeEjemplo = listOf(
    Producto("", nombre = "", descripcion = "", categoria = "", precio = 0.0, cantidad = 0, fechaVencimiento = "")

)

// TopAppBar todavía es una API experimental de Material3: Google la recomienda
// para uso normal, pero se reserva el derecho a cambiar su firma más adelante.
// Kotlin exige reconocer ese riesgo con @OptIn antes de dejar compilar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaEventos() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ir a agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        ListaProducto(
            productos = listaDeEjemplo,
            modifier = Modifier.padding(padding)  // evita que el contenido quede tapado por la topBar
        )
    }
}