package com.datacore.kaijuapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datacore.kaijuapp.model.Producto

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey val codigo: String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio: Double,
    val stockMin: Int,
    val stockActual: Int,
    val stockMax: Int,
    val tipo: String,
    val detalle: String,
    val fotoUri: String? = null
)

fun ProductoEntity.toModel(): Producto = Producto(
    codigo = codigo,
    nombre = nombre,
    descripcion = descripcion,
    categoria = categoria,
    precio = precio,
    stockMin = stockMin,
    stockActual = stockActual,
    stockMax = stockMax,
    tipo = tipo,
    detalle = detalle,
    fotoUri = fotoUri
)

fun Producto.toEntity(): ProductoEntity = ProductoEntity(
    codigo = codigo,
    nombre = nombre,
    descripcion = descripcion,
    categoria = categoria,
    precio = precio,
    stockMin = stockMin,
    stockActual = stockActual,
    stockMax = stockMax,
    tipo = tipo,
    detalle = detalle,
    fotoUri = fotoUri
)
