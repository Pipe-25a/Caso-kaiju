package com.datacore.kaijuapp.repository
import com.datacore.kaijuapp.model.Producto
private val catalogo: MutableList<Producto> = DatosDePrueba.productos.toMutableList()
fun obtenerTodo(): List<Producto> = catalogo.toList()
fun buscarPorCodigo(codigo: String): Producto? =
    catalogo.find { it.codigo == codigo }

fun agregarProducto(producto: Producto): Producto {
    val nuevoCodigo = ((catalogo.mapNotNull { it.codigo.toIntOrNull() }.maxOrNull() ?: 0) + 1).toString()
    val nuevo = producto.copy(codigo = nuevoCodigo)
    catalogo.add(nuevo)
    return nuevo
}

fun actualizarProducto(producto: Producto): Boolean {
    val indice = catalogo.indexOfFirst { it.codigo == producto.codigo }
    if (indice == -1) return false
    catalogo[indice] = producto
    return true
}

fun eliminarPorCodigo(codigo: String): Boolean =
    catalogo.removeIf { it.codigo == codigo }