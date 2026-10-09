package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.repository.InventarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*Al compilar el codigo se cae en esta parte, hice una revision con claude
y siguiro crear una clase con una lista y la funcion agregar
*/
class InventarioViewModel : ViewModel() {
    private val _productos = MutableStateFlow(InventarioRepository.obtenerTodo())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun agregar(producto: Producto) {
        InventarioRepository.agregarProducto(producto)
        _productos.value = InventarioRepository.obtenerTodo()
    }
}