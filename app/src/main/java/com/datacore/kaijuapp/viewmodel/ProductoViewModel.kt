package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.ErroresProducto
import com.datacore.kaijuapp.model.FormularioProductoEstado
import com.datacore.kaijuapp.model.Producto
import com.datacore.kaijuapp.repository.InventarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductoViewModel : ViewModel() {

    private val _estadoFormulario = MutableStateFlow(FormularioProductoEstado())
    val estadoFormulario: StateFlow<FormularioProductoEstado> = _estadoFormulario.asStateFlow()

    fun actualizarCodigo(valor: String) = _estadoFormulario.update { it.copy(codigo = valor) }
    fun actualizarNombre(valor: String) = _estadoFormulario.update { it.copy(nombre = valor) }
    fun actualizarDescripcion(valor: String) = _estadoFormulario.update { it.copy(descripcion = valor) }
    fun actualizarCategoria(valor: String) = _estadoFormulario.update { it.copy(categoria = valor) }
    fun actualizarPrecio(valor: String) = _estadoFormulario.update { it.copy(precio = valor) }
    fun actualizarStockMin(valor: String) = _estadoFormulario.update { it.copy(stockMin = valor) }
    fun actualizarStockMax(valor: String) = _estadoFormulario.update { it.copy(stockMax = valor) }
    fun actualizarTipo(valor: String) = _estadoFormulario.update { it.copy(tipo = valor) }
    fun actualizarDetalle(valor: String) = _estadoFormulario.update { it.copy(detalle = valor) }

    // Rellena el formulario con un producto existente (para la pantalla de modificar)
    fun cargarProducto(p: Producto) {
        _estadoFormulario.value = FormularioProductoEstado(
            codigo = p.codigo,
            nombre = p.nombre,
            descripcion = p.descripcion,
            categoria = p.categoria,
            precio = p.precio.toString(),
            stockMin = p.stockMin.toString(),
            stockMax = p.stockMax.toString(),
            tipo = p.tipo,
            detalle = p.detalle
        )
    }

    // Valida que el código no esté vacío y que el producto exista
    private fun validarCodigo(): Boolean {
        val codigo = _estadoFormulario.value.codigo.trim()
        val error = when {
            codigo.isBlank() -> "El código es obligatorio"
            InventarioRepository.buscarPorCodigo(codigo) == null -> "No existe un producto con ese código"
            else -> null
        }
        _estadoFormulario.update { it.copy(errores = it.errores.copy(errorCodigo = error)) }
        return error == null
    }

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
        // copy() para NO borrar el errorCodigo que pudo setear validarCodigo()
        _estadoFormulario.update {
            it.copy(
                errores = it.errores.copy(
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

    private fun construirProducto(codigo: String): Producto {
        val estado = _estadoFormulario.value
        return Producto(
            codigo = codigo,
            nombre = estado.nombre.trim(),
            descripcion = estado.descripcion.ifBlank { "Sin descripción" },
            categoria = estado.categoria.trim(),
            precio = estado.precio.replace(',', '.').toDouble(),
            stockMin = estado.stockMin.toInt(),
            stockMax = estado.stockMax.toInt(),
            tipo = estado.tipo.trim(),
            detalle = estado.detalle.trim())
    }

    fun crearProductoSiEsValido(): Producto? {
        if (!validarFormulario()) return null
        val producto = construirProducto(codigo = "") // el repositorio asigna el código
        _estadoFormulario.value = FormularioProductoEstado()
        return producto
    }

    // Devuelve el producto a eliminar (o null si el código no es válido)
    fun eliminarProductoSiEsValido(): Producto? {
        if (!validarCodigo()) return null
        val producto = InventarioRepository.buscarPorCodigo(_estadoFormulario.value.codigo.trim())
        _estadoFormulario.value = FormularioProductoEstado()
        return producto
    }

    // Devuelve el producto con los datos nuevos y el MISMO código
    fun modificarProductoSiEsValido(): Producto? {
        val codigoOk = validarCodigo()
        val formularioOk = validarFormulario()
        if (!codigoOk || !formularioOk) return null
        val producto = construirProducto(codigo = _estadoFormulario.value.codigo.trim())
        _estadoFormulario.value = FormularioProductoEstado()
        return producto
    }
}