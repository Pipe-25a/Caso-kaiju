package com.datacore.kaijuapp.navigation
//Este e sun ejemplo generico falata adaptarlo al proyecto

// imports necesarios para este bloque
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MiNavHost() {
    // navController guarda "en qué pantalla estoy" y permite navegar
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "lista") {
        // composable("ruta") registra cada pantalla con un nombre de ruta
        composable("lista") {
            PantallaA(
                onIrAOtra = { navController.navigate("otra") }
            )
        }
        composable("otra") {
            PantallaB(
                onVolver = { navController.popBackStack() }  // vuelve a "lista"
            )
        }
    }
}
