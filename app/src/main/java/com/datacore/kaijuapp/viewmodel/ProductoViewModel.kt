package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.ErroresProducto
import com.datacore.kaijuapp.model.FormularioProductoEstado
import com.datacore.kaijuapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
/*
Al compilar el codigo esta parte deja de funcionar y tira el codigo
*/
class ProductoViewModel : ViewModel() {

    private val _estadoFormulario = MutableStateFlow(FormularioProductoEstado())
    val estadoFormulario: StateFlow<FormularioProductoEstado> = _estadoFormulario.asStateFlow()

    fun actualizarNombre(valor: String) = _estadoFormulario.update { it.copy(nombre = valor) }
    fun actualizarDescripcion(valor: String) = _estadoFormulario.update { it.copy(descripcion = valor) }
    fun actualizarCategoria(valor: String) = _estadoFormulario.update { it.copy(categoria = valor) }
    fun actualizarPrecio(valor: String) = _estadoFormulario.update { it.copy(precio = valor) }
    fun actualizarStockMin(valor: String) = _estadoFormulario.update { it.copy(stockMin = valor) }
    fun actualizarStockMax(valor: String) = _estadoFormulario.update { it.copy(stockMax = valor) }
    fun actualizarTipo(valor: String) = _estadoFormulario.update { it.copy(tipo = valor) }
    fun actualizarDetalle(valor: String) = _estadoFormulario.update { it.copy(detalle = valor) }
    private fun validarFormulario(): Boolean {
        val estado = _estadoFormulario.value

        val precio = estado.precio.replace(',', '.').toDoubleOrNull()
        val stockMin = estado.stockMin.toIntOrNull()
        val stockMax = estado.stockMax.toIntOrNull()
        val errorNombre = if (estado.nombre.isBlank()) "El nombre es obligatorio" else null
        val errorCategoria = if (estado.categoria.isBlank()) "La categoría es obligatoria" else null
        val errorTipo = if (estado.tipo.isBlank()) "El tipo es obligatorio" else null
        val errorPrecio = when {
            estado.precio.isBlank() -> "El precio es obligatorio"
            precio == null -> "El precio debe ser un número"
            precio <= 0 -> "El precio debe ser mayor a 0"
            else -> null }
        val errorStockMin = when {
            estado.stockMin.isBlank() -> "El stock mínimo es obligatorio"
            stockMin == null -> "Debe ser un número entero"
            stockMin < 0 -> "No puede ser negativo"
            else -> null }
        val errorStockMax = when {
            estado.stockMax.isBlank() -> "El stock máximo es obligatorio"
            stockMax == null -> "Debe ser un número entero"
            stockMax < 0 -> "No puede ser negativo"
            stockMin != null && stockMax < stockMin -> "No puede ser menor al stock mínimo"
            else -> null }
        _estadoFormulario.update {
            it.copy(
                errores = ErroresProducto(
                    errorNombre = errorNombre,
                    errorCategoria = errorCategoria,
                    errorPrecio = errorPrecio,
                    errorStockMin = errorStockMin,
                    errorStockMax = errorStockMax,
                    errorTipo = errorTipo
                ))}
        return listOf(errorNombre, errorCategoria, errorTipo, errorPrecio, errorStockMin, errorStockMax)
            .all { it == null }
    }
    fun crearProductoSiEsValido(): Producto? {
        if (!validarFormulario()) return null
        val estado = _estadoFormulario.value
        val producto = Producto(
            codigo = "",
            nombre = estado.nombre.trim(),
            descripcion = estado.descripcion.ifBlank { "Sin descripción" },
            categoria = estado.categoria.trim(),
            precio = estado.precio.replace(',', '.').toDouble(),
            stockMin = estado.stockMin.toInt(),
            stockMax = estado.stockMax.toInt(),
            tipo = estado.tipo.trim(),
            detalle = estado.detalle.trim())
        _estadoFormulario.value = FormularioProductoEstado() // limpia el formulario
        return producto
    }
}