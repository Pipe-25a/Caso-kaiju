package com.datacore.kaijuapp.model

data class Moviminto (
    //falta definir los atributos de mejor manera
    //Código o identificador del producto, nombre, descripción, categoría, tipo de producto y precio.
    val id:String,
    val codigoProducto: String,
    val tipo: String,
    val cantidad: Int,
    val motivo:String,
    val fecha:String,
    val usuario:String

)
{
}