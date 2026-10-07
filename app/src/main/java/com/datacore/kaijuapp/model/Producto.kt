package com.datacore.kaijuapp.model

data class Producto (
    val codigo:String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio:Double,
    val stockMin:Int,
    val stockMax:Int,
    val tipo: String,
    val detalle:String
    //detalle es un texto opcional para lo que depende del tipo: talla y color, lote o fecha de vencimiento.
){}