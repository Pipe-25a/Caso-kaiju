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
    // Se crean aquí para compartirlos entre pantallas
    val sesionViewModel: SesionViewModel = viewModel()
    val inventarioViewModel: InventarioViewModel = viewModel()
    val productos by inventarioViewModel.productos.collectAsState()
    NavHost(navController = navController, startDestination = Rutas.Login.ruta) {
        composable(Rutas.Login.ruta) {
            PantallaLogin(
                viewModel = sesionViewModel,
                onLoginExitoso = {
                    navController.navigate(Rutas.Menu.ruta) {
                        // saca el login del historial: "atrás" no vuelve al login
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(Rutas.Menu.ruta) {
            val usuario by sesionViewModel.usuarioActual.collectAsState()
            PantallaMenu(
                nombreUsuario = usuario?.nombre ?: "",
                rol = usuario?.rol ?: "",
                onProductos = { navController.navigate(Rutas.Lista.ruta) },
                onAgregar = { navController.navigate(Rutas.Agregar.ruta) },
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