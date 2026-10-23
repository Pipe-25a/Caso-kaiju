package com.datacore.kaijuapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datacore.kaijuapp.model.Movimiento

@Entity(tableName = "movimientos")
data class MovimientoEntity(
    @PrimaryKey val id: String,
    val codigoProducto: String,
    val tipo: String,
    val cantidad: Int,
    val motivo: String,
    val fecha: String,
    val usuario: String
)

fun MovimientoEntity.toModel(): Movimiento = Movimiento(
    id = id,
    codigoProducto = codigoProducto,
    tipo = tipo,
    cantidad = cantidad,
    motivo = motivo,
    fecha = fecha,
    usuario = usuario
)

fun Movimiento.toEntity(): MovimientoEntity = MovimientoEntity(
    id = id,
    codigoProducto = codigoProducto,
    tipo = tipo,
    cantidad = cantidad,
    motivo = motivo,
    fecha = fecha,
    usuario = usuario
)
