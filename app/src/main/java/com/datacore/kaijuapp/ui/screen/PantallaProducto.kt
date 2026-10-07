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
// ListaEventos vive en ui.components: paquete distinto a ui.screen, así que
// acá sí hace falta importarla explícitamente (a diferencia de TarjetaEvento
// dentro de ListaEventos.kt, que comparte paquete con ella).
// datos de prueba: en la actividad, cada equipo los reemplaza por los de su caso
private val listaDeEjemplo = listOf(
    Producto(
        codigo ="",
        nombre = "",
        descripcion = "",
        categoria = "",
        precio = 0.0,
        stockMax = 0,
        stockMin =  0,
        tipo = "",
        detalle = ""
    ))

// TopAppBar todavía es una API experimental de Material3: Google la recomienda
// para uso normal, pero se reserva el derecho a cambiar su firma más adelante.
// Kotlin exige reconocer ese riesgo con @OptIn antes de dejar compilar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProducto() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Kaiju App") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ir a agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto") } }
    ) { padding ->
        ListaProducto(
            productos = listaDeEjemplo,
            modifier = Modifier.padding(padding)  // evita que el contenido quede tapado por la topBar
        )
    }
}