package com.datacore.kaijuapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {

    @Query("SELECT * FROM movimientos")
    fun listarTodos(): Flow<List<MovimientoEntity>>

    @Query("SELECT * FROM movimientos WHERE id = :id")
    suspend fun buscarPorId(id: String): MovimientoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(movimiento: MovimientoEntity)
}
