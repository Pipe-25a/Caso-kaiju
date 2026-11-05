package com.datacore.kaijuapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datacore.kaijuapp.model.Usuario

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val rol: String,
    val clave: String
)

fun UsuarioEntity.toModel(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    rol = rol,
    clave = clave
)

fun Usuario.toEntity(): UsuarioEntity = UsuarioEntity(
    id = id,
    nombre = nombre,
    rol = rol,
    clave = clave
)
