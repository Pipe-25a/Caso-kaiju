package com.datacore.kaijuapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.datacore.kaijuapp.ui.screen.PantallaAgregarProducto
import com.datacore.kaijuapp.ui.screen.PantallaDetallesMovimiento
import com.datacore.kaijuapp.ui.screen.PantallaEliminarProducto
import com.datacore.kaijuapp.ui.screen.PantallaLogin
import com.datacore.kaijuapp.ui.screen.PantallaMenu
import com.datacore.kaijuapp.ui.screen.PantallaMovimiento
import com.datacore.kaijuapp.ui.screen.PantallaProducto
import com.datacore.kaijuapp.viewmodel.InventarioViewModel
import com.datacore.kaijuapp.viewmodel.MovimientoViewModel
import com.datacore.kaijuapp.viewmodel.SesionViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    // Se crean aquí para compartirlos entre pantallas
    val sesionViewModel: SesionViewModel = viewModel()
    val inventarioViewModel: InventarioViewModel = viewModel()
    val movimientoViewModel: MovimientoViewModel = viewModel()

    val productos by inventarioViewModel.productos.collectAsState()
    val movimientos by movimientoViewModel.movimientos.collectAsState()
    val usuario by sesionViewModel.usuarioActual.collectAsState()

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
            PantallaMenu(
                nombreUsuario = usuario?.nombre ?: "",
                rol = usuario?.rol ?: "",
                onCatalogo = { navController.navigate(Rutas.Lista.ruta) },
                onAgregar = { navController.navigate(Rutas.Agregar.ruta) },
                onEliminar = { navController.navigate(Rutas.Eliminar.ruta) },
                onMovimientos = { navController.navigate(Rutas.Movimientos.ruta) },
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
                onAgregar = { navController.navigate(Rutas.Agregar.ruta) },
                onVolver = { navController.popBackStack() },
                puedeAgregar = usuario?.rol == "EncargadoInventario"
            )
        }

        composable(Rutas.Agregar.ruta) {
            PantallaAgregarProducto(
                onGuardar = { inventarioViewModel.agregar(it) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.Eliminar.ruta) {
            PantallaEliminarProducto(
                onEliminar = { inventarioViewModel.eliminar(it) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.Movimientos.ruta) {
            PantallaMovimiento(
                movimientos = movimientos,
                onSeleccionar = { navController.navigate(Rutas.DetalleMovimiento.crear(it.id)) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.DetalleMovimiento.ruta,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val movimiento = movimientoViewModel.buscarPorId(id)
            if (movimiento != null) {
                PantallaDetallesMovimiento(
                    movimiento = movimiento,
                    onVolver = { navController.popBackStack() }
                )
            } else {
                // Si no existe, vuelve atrás en vez de mostrar una pantalla rota
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}