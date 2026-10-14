package com.datacore.kaijuapp.model

data class Usuario (
    val id: String,
    val nombre: String,
    val rol: String,
    val clave: String
) {
    val esEncargadoDeInventario: Boolean
        get() = rol == ROL_ENCARGADO

    val esAdministrador: Boolean
        get() = rol == ROL_ADMINISTRADOR

    companion object {
        const val ROL_ADMINISTRADOR = "Administrador"
        const val ROL_VENDEDOR = "Vendedor"
        const val ROL_ENCARGADO = "EncargadoInventario"
    }
}
