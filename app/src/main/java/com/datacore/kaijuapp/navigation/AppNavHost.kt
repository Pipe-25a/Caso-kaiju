package com.datacore.kaijuapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datacore.kaijuapp.ui.screen.PantallaAgregarProducto
import com.datacore.kaijuapp.ui.screen.PantallaLogin
import com.datacore.kaijuapp.ui.screen.PantallaMenu
import com.datacore.kaijuapp.ui.screen.PantallaProducto
import com.datacore.kaijuapp.viewmodel.InventarioViewModel
import com.datacore.kaijuapp.viewmodel.SesionViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    // Se crean aquí (una sola vez) para que todas las pantallas compartan los mismos datos
    val sesionViewModel: SesionViewModel = viewModel()
    val inventarioViewModel: InventarioViewModel = viewModel()
    val productos by inventarioViewModel.productos.collectAsState()
    val usuario by sesionViewModel.usuarioActual.collectAsState()

    NavHost(navController = navController, startDestination = Rutas.Login.ruta) {
        composable(Rutas.Login.ruta) {
            PantallaLogin(
                viewModel = sesionViewModel,
                onLoginExitoso = {
                    navController.navigate(Rutas.Menu.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(Rutas.Menu.ruta) {
            PantallaMenu(
                usuario = usuario,
                onCatalogo = { navController.navigate(Rutas.Lista.ruta) },
                onCerrarSesion = {
                    sesionViewModel.cerrarSesion()
                    navController.navigate(Rutas.Login.ruta) {
                        popUpTo(Rutas.Menu.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(Rutas.Lista.ruta) {
            PantallaProducto(
                productos = productos,
                puedeAgregar = usuario?.esEncargadoDeInventario == true,
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
