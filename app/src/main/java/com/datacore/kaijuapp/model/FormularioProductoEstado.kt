package com.datacore.kaijuapp.model

data class FormularioProductoEstado (
    val codigo:String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val precio:Double = 0.0,
    val stockMin:Int = 0,
    val stockMax:Int = 0,
    val tipo: String= "",
    val detalle:String= "",
    val errores: ErroresProducto = ErroresProducto()

)
data class ErroresProducto(
    val errorCodigo:String?=null,
    val errorNombre: String?=null,
    val errorDescripcion: String?=null,
    val errorCategoria: String?=null,
    val errorPrecio:Double?=null,
    val errorStockMin:Int?=null,
    val errorStockMax:Int?=null,
    val ErrorTipo: String?=null,
    val errorDetalle:String?=null
)