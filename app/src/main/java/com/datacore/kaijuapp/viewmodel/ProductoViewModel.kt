package com.datacore.kaijuapp.viewmodel

import com.datacore.kaijuapp.model.FormularioProductoEstado
import com.datacore.kaijuapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.datacore.kaijuapp.*
private val _estadoFormulario = MutableStateFlow(FormularioProductoEstado())
val estadoFormulario: StateFlow<FormularioProductoEstado> = _estadoFormulario.asStateFlow()

fun actualizarCodigo(Valor: String) = _estadoFormulario.update { it.copy(codigo = Valor) }
fun actualizarNombre(Valor: String) = _estadoFormulario.update { it.copy(nombre = Valor) }
fun actualizarDescripcion(Valor: String) = _estadoFormulario.update { it.copy(descripcion = Valor) }
fun actualiarCategoria(Valor: String)= _estadoFormulario.update { it.copy(categoria = Valor) }
fun actualizarPrecio(Valor: Double)= _estadoFormulario.update { it.copy(precio = Valor) }
fun actualizarcantida(Valor:Int)=_estadoFormulario.update { it.copy(cantidad = Valor) }
fun actualizarFechaVencimiento(Valor: String)=_estadoFormulario.update {it.copy(fechaVencimiento = Valor ) }

//revisar aqui
fun validarFormulario(): Boolean {
    val estado = _estadoFormulario.value
    val precioNumerico = estado.precio.toDoubleOrNull()
    val cantidadNumerica = estado.cantidad.toIntOrNull()
    val errorCodigo = if (estado.codigo.isBlank()) "El código es obligatorio" else null
    val errorNombre = if (estado.nombre.isBlank()) "El nombre es obligatorio" else null
    val errorCategoria = if (estado.categoria.isBlank()) "La categoría es obligatoria" else null

    val errorPrecio = when {
        estado.precio.isBlank() -> "El precio es obligatorio"
        precioNumerico == null -> "El precio debe ser un número"
        precioNumerico <= 0 -> "El precio debe ser mayor a 0"
        else -> null
    }

    val errorCantidad = when {
        estado.cantidad.isBlank() -> "La cantidad es obligatoria"
        cantidadNumerica == null -> "La cantidad debe ser un número entero"
        cantidadNumerica < 0 -> "La cantidad no puede ser negativa"
        else -> null
    }

    // descripción y fecha de vencimiento son opcionales
    _estadoFormulario.update {
        it.copy(
            errores = ErroresProducto(
                errorCodigo = errorCodigo,
                errorNombre = errorNombre,
                errorCategoria = errorCategoria,
                errorPrecio = errorPrecio,
                errorCantidad = errorCantidad
            )
        )
    }
    return listOf(errorCodigo, errorNombre, errorCategoria, errorPrecio, errorCantidad)
        .all { it == null }
}
fun guardarProducto(): Boolean {
    if (!validarFormulario()) return false
    val estado = _estadoFormulario.value
    val producto = Producto(
        codigo = estado.codigo.trim(),
        nombre = estado.nombre.trim(),
        descripcion = estado.descripcion.ifBlank { "Sin descripción" },
        categoria = estado.categoria.trim(),
        precio = estado.precio.toDouble(),
        cantidad = estado.cantidad.toInt(),
        fechaVencimiento = estado.fechaVencimiento.ifBlank { "Sin vencimiento" }
    )
    _productos.update { it + producto }
    _estadoFormulario.value = FormularioProductoEstado()  // limpia el formulario
    return true
}
