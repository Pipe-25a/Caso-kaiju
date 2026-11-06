package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.repository.InventarioRepository
import kotlinx.coroutines.flow.StateFlow

class InventarioViewModel : ViewModel() {

    // El repositorio ya es reactivo: no hace falta copiar la lista a mano
    val productos: StateFlow<List<Producto>> = InventarioRepository.productos

    // Devuelve el producto ya guardado, con el código asignado
    fun agregar(producto: Producto): Producto = InventarioRepository.agregar(producto)

    fun actualizar(producto: Producto) = InventarioRepository.actualizar(producto)

    // Si ya fue borrado, no pasa nada
    fun eliminar(producto: Producto): Boolean =
        InventarioRepository.eliminarPorCodigo(producto.codigo)
}