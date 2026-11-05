package com.datacore.kaijuapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.datacore.kaijuapp.navigation.AppNavHost
import com.datacore.kaijuapp.navigation.Rutas
import com.datacore.kaijuapp.ui.theme.KaijuDarkPurple
import com.datacore.kaijuapp.ui.theme.KaijuOnDark
import com.datacore.kaijuapp.ui.theme.KaijuYellow
import com.datacore.kaijuapp.viewmodel.InventarioViewModel
import com.datacore.kaijuapp.viewmodel.MovimientoViewModel
import com.datacore.kaijuapp.viewmodel.SesionViewModel

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun MainScreen() {
    // Un solo NavController y ViewModels compartidos para la barra y el NavHost
    val navController = rememberNavController()
    val sesionViewModel: SesionViewModel = viewModel()
    val inventarioViewModel: InventarioViewModel = viewModel()
    val movimientoViewModel: MovimientoViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Pantallas donde la barra se oculta (sesión y formularios/detalles)
    val rutasSinBarra = listOf(
        Rutas.Login.ruta,
        Rutas.Agregar.ruta,
        Rutas.Modificar.ruta.substringBefore("{"),
        Rutas.DetalleMovimiento.ruta.substringBefore("{")
    )

    val mostrarBarra = currentRoute != null &&
            rutasSinBarra.none { ruta -> currentRoute.startsWith(ruta) }

    val items = listOf(
        BottomNavItem(Rutas.Lista.ruta, "Catálogo", Icons.Default.Inventory),
        BottomNavItem(Rutas.Movimientos.ruta, "Movimientos", Icons.Default.SwapHoriz),
        BottomNavItem(Rutas.Menu.ruta, "Menú", Icons.Default.Person)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            AnimatedVisibility(visible = mostrarBarra) {
                NavigationBar(containerColor = KaijuDarkPurple) {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    // Ojo: se usa Menú como base y no findStartDestination(),
                                    // porque el Login ya se sacó del historial al iniciar sesión
                                    popUpTo(Rutas.Menu.ruta) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = KaijuYellow,
                                selectedTextColor = KaijuYellow,
                                indicatorColor = KaijuDarkPurple,
                                unselectedIconColor = KaijuOnDark.copy(alpha = 0.6f),
                                unselectedTextColor = KaijuOnDark.copy(alpha = 0.6f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            sesionViewModel = sesionViewModel,
            inventarioViewModel = inventarioViewModel,
            movimientoViewModel = movimientoViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}