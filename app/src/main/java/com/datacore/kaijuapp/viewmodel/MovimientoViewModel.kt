package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.ErroresMovimiento
import com.datacore.kaijuapp.model.FormularioMovimientoEstado
import com.datacore.kaijuapp.model.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovimientoViewModel : ViewModel() {
    private val _estadoFormulario = MutableStateFlow(FormularioMovimientoEstado())
    val estadoFormulario: StateFlow<FormularioMovimientoEstado> = _estadoFormulario.asStateFlow()
    fun actualizarCodigoProducto(valor: String) = _estadoFormulario.update { it.copy(codigoProducto = valor) }
    fun actualizarTipo(valor: String) = _estadoFormulario.update { it.copy(tipo = valor) }
    fun actualizarCantidad(valor: String) = _estadoFormulario.update { it.copy(cantidad = valor) }
    fun actualizarMotivo(valor: String) = _estadoFormulario.update { it.copy(motivo = valor) }
    fun actualizarFecha(valor: String) = _estadoFormulario.update { it.copy(fecha = valor) }
    fun actualizarUsuario(valor: String) = _estadoFormulario.update { it.copy(usuario = valor) }
    private fun validarFormulario(): Boolean {
        val estado = _estadoFormulario.value
        val cantidad = estado.cantidad.toIntOrNull()
        val errorCodigoProducto = if (estado.codigoProducto.isBlank()) "El código del producto es obligatorio" else null
        val errorTipo = if (estado.tipo.isBlank()) "El tipo es obligatorio" else null
        val errorMotivo = if (estado.motivo.isBlank()) "El motivo es obligatorio" else null
        val errorFecha = if (estado.fecha.isBlank()) "La fecha es obligatoria" else null
        val errorUsuario = if (estado.usuario.isBlank()) "El usuario es obligatorio" else null
        val errorCantidad = when {
            estado.cantidad.isBlank() -> "La cantidad es obligatoria"
            cantidad == null -> "La cantidad debe ser un número entero"
            cantidad <= 0 -> "La cantidad debe ser mayor a 0"
            else -> null
        }

        _estadoFormulario.update {
            it.copy(
                errores = ErroresMovimiento(
                    errorCodigoProducto = errorCodigoProducto,
                    errorTipo = errorTipo,
                    errorCantidad = errorCantidad,
                    errorMotivo = errorMotivo,
                    errorFecha = errorFecha,
                    errorUsuario = errorUsuario
                )
            )
        }
        return listOf(errorCodigoProducto, errorTipo, errorCantidad, errorMotivo, errorFecha, errorUsuario)
            .all { it == null }
    }

    fun crearMovimientoSiEsValido(): Movimiento? {
        if (!validarFormulario()) return null
        val estado = _estadoFormulario.value
        val movimiento = Movimiento(
            id = System.currentTimeMillis().toString(),
            codigoProducto = estado.codigoProducto.trim(),
            tipo = estado.tipo.trim(),
            cantidad = estado.cantidad.toInt(),
            motivo = estado.motivo.trim(),
            fecha = estado.fecha.trim(),
            usuario = estado.usuario.trim()
        )
        _estadoFormulario.value = FormularioMovimientoEstado()
        return movimiento
    }
}