package com.datacore.kaijuapp.model

data class Producto (
    //falta definir los atributos de mejor manera
    //Código o identificador del producto, nombre, descripción, categoría, tipo de producto y precio.
    val codigo:String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio:Double,
    val cantidad: Int,
    val fechaVencimiento:String

)
{
}