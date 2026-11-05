package com.datacore.kaijuapp.navigation

// Cada pantalla es un objeto con su ruta, en UN solo archivo.
sealed class Rutas(val ruta: String) {
        object Login       : Rutas("login")
        object Menu        : Rutas("menu")
        object Lista       : Rutas("lista")
        object Agregar     : Rutas("agregar")
        object Eliminar    : Rutas("eliminar")
        object Movimientos : Rutas("movimientos")
        object Modificar : Rutas("modificar/{codigo}") {
                fun crear(codigo: String) = "modificar/${android.net.Uri.encode(codigo)}"
        }
        // Ruta con argumento: detalle_movimiento/{id}
        object DetalleMovimiento : Rutas("detalle_movimiento/{id}") {
                fun crear(id: String) = "detalle_movimiento/$id"
        }
}
