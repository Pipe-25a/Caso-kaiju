package com.datacore.kaijuapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductoEntity::class, UsuarioEntity::class, MovimientoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KaijuDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    abstract fun usuarioDao(): UsuarioDao

    abstract fun movimientoDao(): MovimientoDao

    companion object {
        @Volatile
        private var INSTANCIA: KaijuDatabase? = null

        fun getInstance(context: Context): KaijuDatabase =
            INSTANCIA ?: synchronized(this) {
                INSTANCIA ?: Room.databaseBuilder(
                    context.applicationContext,
                    KaijuDatabase::class.java,
                    "kaiju.db"
                ).build().also { INSTANCIA = it }
            }
    }
}
