package com.datacore.kaijuapp.viewmodel

import com.datacore.kaijuapp.model.FormularioProductoEstado
import com.datacore.kaijuapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.datacore.kaijuapp.*
private val _estadoFormularioMovimiento = MutableStateFlow(FormularioMovimientoEstado())
val estadoFormularioMovimiento: StateFlow<FormularioMovimientoEstado> = _estadoFormularioMovimiento.asStateFlow()

fun actualizarId(Valor: String) = _estadoFormularioMovimiento.update { it.copy(id = Valor) }
fun actualizarCodigoProducto(Valor: String) = _estadoFormularioMovimiento.update { it.copy(codigoProducto = Valor) }
fun actualizarTipo(Valor: String) = _estadoFormularioMovimiento.update { it.copy(tipo = Valor) }
fun actualizarCantida(Valor:Int)=_estadoFormularioMovimiento.update { it.copy(cantidad = Valor) }
fun actualiarMotivo(Valor: String)= _estadoFormularioMovimiento.update { it.copy(Motivo = Valor) }
fun actualizarFecha(Valor: String)=_estadoFormularioMovimiento.update {it.copy(fecha = Valor ) }
fun actualizarUsuario(Valor: String)=_estadoFormularioMovimiento.update {it.copy(usuario = Valor ) }

//revisar aqui
fun validarFormulario(): Boolean {
    val estado = _estadoFormularioMovimiento.value
    val errorCodigoProducto = if (estado.codigoProducto.isBlank()) "El código del producto es obligatorio" else null
    val cantidadNumerica = estado.cantidad.toIntOrNull()
    val errorTipo = if (estado.tipo.isBlank()) "El tipo es obligatorio" else null
    val errorFecha = if (estado.fecha.isBlank()) "La fecha es obligatorio" else null
    val errorMotivo = if (estado.motivo.isBlank()) "El motivo es obligatorio" else null
    val errorUsuario = if (estado.usuario.isBlank()) "El usuario es obligatoria" else null


    val errorCantidad = when {
        estado.cantidad.isBlank() -> "La cantidad es obligatoria"
        cantidadNumerica == null -> "La cantidad debe ser un número entero"
        cantidadNumerica < 0 -> "La cantidad no puede ser negativa"
        else -> null
    }

    // descripción y fecha de vencimiento son opcionales
    _estadoFormulario.update {
        it.copy(
            errores = ErroresMovimiento(
                errorCodigoProducto = errorCodigoProducto,
                errorTipo = errorTipo,
                errorFecha = errorFecha,
                errorMotivo = errorMotivo,
                errorCantidad = errorCantidad,
                errorUsuario = errorUsuario
            )
        )
    }
    return listOf(errorCodigo, errorNombre, errorCategoria, errorPrecio, errorCantidad)
        .all { it == null }
}
fun guardarMovimiento(): Boolean {
    if (!validarFormulario()) return false
    val estado = _estadoFormulario.value
    val movimiento = Movimiento(
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
}