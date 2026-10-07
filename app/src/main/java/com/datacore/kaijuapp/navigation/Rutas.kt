package com.datacore.kaijuapp.navigation

// Cada pantalla es un objeto con su ruta, en UN solo archivo.
sealed class Rutas(val ruta: String) {
    object Lista   : Rutas("lista")
    object Agregar : Rutas("agregar")
}