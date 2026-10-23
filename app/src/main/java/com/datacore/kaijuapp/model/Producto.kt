package com.datacore.kaijuapp.model

data class Producto (
    val codigo:String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio:Double,
    val stockMin:Int,
    val stockActual: Int,
    val stockMax:Int,
    val tipo: String,
    val detalle:String,
    val fotoUri: String? = null   // foto elegida en la galería (opcional)
){}