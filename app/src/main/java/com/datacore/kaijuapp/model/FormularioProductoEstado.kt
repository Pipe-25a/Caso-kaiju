package com.datacore.kaijuapp.model

data class FormularioProductoEstado (
    val codigo:String="",
    val nombre: String="",
    val descripcion: String="",
    val categoria: String="",
    val precio:Double=0.0,
    val cantidad: Int=0,
    val fechaVencimiento:String ="",
    val errores: ErroresProducto = ErroresProducto()

)
data class ErroresProducto(
    val errorCodigo: String? = null,  // null = "está bien"
    val errorNombre: String? = null,
    val errorDescripcion: String? = null,
    val errorCategoria:String?=null,
    val errorPrecio: Double?=null,
    val errorCantidad:String?=null,
    val errorfechaVencimiento:String?=null
)