package com.datacore.kaijuapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datacore.kaijuapp.ui.screen.PantallaAgregarProducto
import com.datacore.kaijuapp.ui.screen.PantallaProducto
import com.datacore.kaijuapp.viewmodel.InventarioViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    // Se crea aquí (una sola vez) para que la lista y el formulario compartan los mismos datos
    val inventarioViewModel: InventarioViewModel = viewModel()
    val productos by inventarioViewModel.productos.collectAsState()

    NavHost(navController = navController, startDestination = Rutas.Lista.ruta) {
        composable(Rutas.Lista.ruta) {
            PantallaProducto(
                productos = productos,
                onAgregar = { navController.navigate(Rutas.Agregar.ruta) }
            )
        }
        composable(Rutas.Agregar.ruta) {
            PantallaAgregarProducto(
                onGuardar = { inventarioViewModel.agregar(it) },
                onVolver = { navController.popBackStack() }
            )
        }
    }
}