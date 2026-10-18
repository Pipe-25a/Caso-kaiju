package com.datacore.kaijuapp.repository
import com.datacore.kaijuapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object InventarioRepository {
    private val _productos = MutableStateFlow(DatosDePrueba.productos)
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun buscarPorCodigo(codigo: String): Producto? =
        _productos.value.firstOrNull { it.codigo == codigo }

    // Asigna el código y devuelve el producto ya guardado
    fun agregar(p: Producto): Producto {
        val siguiente = (_productos.value.mapNotNull { it.codigo.toIntOrNull() }.maxOrNull() ?: 0) + 1
        val nuevo = p.copy(codigo = siguiente.toString())
        _productos.update { it + nuevo }
        return nuevo
    }

    fun actualizar(p: Producto) {
        _productos.update { lista -> lista.map { if (it.codigo == p.codigo) p else it } }
    }

    fun eliminarPorCodigo(codigo: String): Boolean {
        val existe = buscarPorCodigo(codigo) != null
        _productos.update { lista -> lista.filterNot { it.codigo == codigo } }
        return existe
    }
}