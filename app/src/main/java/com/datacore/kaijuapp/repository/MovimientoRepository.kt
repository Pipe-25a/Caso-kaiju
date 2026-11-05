package com.datacore.kaijuapp.repository

import com.datacore.kaijuapp.model.Movimiento

object MovimientoRepository {
    private val movimientos: MutableList<Movimiento> = DatosDePrueba.movimientos.toMutableList()

    fun obtenerTodo(): List<Movimiento> = movimientos.toList()

    fun buscarPorId(id: String): Movimiento? = movimientos.find { it.id == id }

    fun agregar(movimiento: Movimiento) {
        movimientos.add(movimiento)
    }
}