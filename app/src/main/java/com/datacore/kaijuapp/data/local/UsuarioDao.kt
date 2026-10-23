package com.datacore.kaijuapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND clave = :clave LIMIT 1")
    suspend fun buscarPorNombreYClave(nombre: String, clave: String): UsuarioEntity?
}
