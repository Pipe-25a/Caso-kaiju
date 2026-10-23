package com.datacore.kaijuapp.model

data class FormularioProductoEstado(
    val codigo: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val precio: String = "",
    val stockMin: String = "",
    val stockActual: String = "",
    val stockMax: String = "",
    val tipo: String = "",
    val detalle: String = "",
    val fotoUri: String? = null,
    val errores: ErroresProducto = ErroresProducto()
)
data class ErroresProducto(
    val errorCodigo: String? = null,
    val errorNombre: String? = null,
    val errorCategoria: String? = null,
    val errorPrecio: String? = null,
    val errorStockMin: String? = null,
    val errorStockActual: String? = null,
    val errorStockMax: String? = null,
    val errorTipo: String? = null
)