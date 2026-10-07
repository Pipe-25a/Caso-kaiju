package com.datacore.kaijuapp.model

data class FormularioMovimientoEstado (
    val id:String="",
    val codigoProducto: String="",
    val tipo: String="",
    val cantidad: Int=0,
    val motivo: String="",
    val fecha:String ="",
    val usuario: String="",
    val errores: ErroresMovimiento = ErroresMovimiento()

)
data class ErroresMovimiento(
    val errorId: String? = null,  // null = "está bien"
    val errorCodigoProducto: String? = null,
    val errorTipo: String? = null,
    val errorCantidad: Int?=null,
    val errorMotivo: String?=null,
    val errorFecha:String?=null,
    val errorUsuario:String?=null
)