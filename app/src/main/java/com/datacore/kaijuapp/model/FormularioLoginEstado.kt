package com.datacore.kaijuapp.model

data class FormularioLoginEstado(
    val usuario: String = "",
    val clave: String = "",
    val errores: ErroresLogin = ErroresLogin()
)
data class ErroresLogin(
    val errorUsuario: String? = null,
    val errorClave: String? = null,
    val errorGeneral: String? = null
)
