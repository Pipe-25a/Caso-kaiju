package com.datacore.kaijuapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.datacore.kaijuapp.naturalresources.notificaciones.NotificacionHelper
import com.datacore.kaijuapp.ui.screen.PantallaAgregarProducto
import com.datacore.kaijuapp.ui.screen.PantallaCatalogo
import com.datacore.kaijuapp.ui.screen.PantallaDetallesMovimiento
import com.datacore.kaijuapp.ui.screen.PantallaLogin
import com.datacore.kaijuapp.ui.screen.PantallaMenu
import com.datacore.kaijuapp.ui.screen.PantallaModificarProducto
import com.datacore.kaijuapp.ui.screen.PantallaMovimiento
import com.datacore.kaijuapp.viewmodel.InventarioViewModel
import com.datacore.kaijuapp.viewmodel.MovimientoViewModel
import com.datacore.kaijuapp.viewmodel.SesionViewModel

@Composable
fun AppNavHost() {
    // Se crean aquí para compartirlos entre pantallas
    val navController = rememberNavController()
    val sesionViewModel: SesionViewModel = viewModel()
    val inventarioViewModel: InventarioViewModel = viewModel()
    val movimientoViewModel: MovimientoViewModel = viewModel()
    val productos by inventarioViewModel.productos.collectAsState()
    val movimientos by movimientoViewModel.movimientos.collectAsState()
    val usuario by sesionViewModel.usuarioActual.collectAsState()
    val context = LocalContext.current
    // Recalcula la alerta del producto con menor stock con la lista YA actualizada
    // (se usa .value y no `productos` porque este último aún tiene la lista vieja dentro del lambda)
    val refrescarAlerta = {
        NotificacionHelper.actualizarAlertaStock(context, inventarioViewModel.productos.value)
    }

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
                onMovimientos = { navController.navigate(Rutas.Movimientos.ruta) },
                onUsuarios = null, // deshabilitado "(próximamente)"; pásale una lambda cuando exista
                onCerrarSesion = {
                    sesionViewModel.cerrarSesion()
                    navController.navigate(Rutas.Login.ruta) {
                        popUpTo(Rutas.Menu.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(Rutas.Lista.ruta) {
            PantallaCatalogo(
                productos = productos,
                // Administrador y Encargado pueden agregar/modificar/eliminar
                puedeEditar = usuario?.rol == "Administrador" || usuario?.rol == "EncargadoInventario",
                onVolver = { navController.popBackStack() },
                onAgregar = { navController.navigate(Rutas.Agregar.ruta) },
                onEditar = { navController.navigate(Rutas.Modificar.crear(it.codigo)) },
                onEliminar = {
                    inventarioViewModel.eliminar(it)
                    NotificacionHelper.notificarAccion(
                        context, "Producto eliminado",
                        "Se eliminó el producto con ID: ${it.codigo}"
                    )
                    refrescarAlerta()
                }
            )
        }
        composable(Rutas.Agregar.ruta) {
            PantallaAgregarProducto(
                onGuardar = {
                    val nuevo = inventarioViewModel.agregar(it)
                    NotificacionHelper.notificarAccion(
                        context, "Producto agregado",
                        "Se agregó el producto con ID: ${nuevo.codigo}"
                    )
                    refrescarAlerta()
                },
                onVolver = { navController.popBackStack() }
            )
        }
        composable(
            route = Rutas.Modificar.ruta,
            arguments = listOf(navArgument("codigo") { type = NavType.StringType })
        ) { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo")
            val producto = productos.firstOrNull { it.codigo == codigo }
            if (producto != null) {
                PantallaModificarProducto(
                    producto = producto,
                    onGuardar = {
                        inventarioViewModel.actualizar(it)
                        NotificacionHelper.notificarAccion(
                            context, "Producto modificado",
                            "Se modificó el producto con ID: ${it.codigo}"
                        )
                        refrescarAlerta()
                    },
                    onVolver = { navController.popBackStack() }
                )
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
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
                    onVolver = { navController.popBackStack() })
            } else {
                // Si no existe, vuelve atrás en vez de mostrar una pantalla rota
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}