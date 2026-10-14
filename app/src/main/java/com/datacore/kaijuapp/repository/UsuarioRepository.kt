package com.datacore.kaijuapp.repository

import com.datacore.kaijuapp.model.Usuario
//esto es un autenticardor de contraseña de usuarios
object UsuarioRepository {
    fun autenticar(nombre:String,clave: String): Usuario?=
        DatosDePrueba.usuarios.find {
            it.nombre.equals(nombre.trim(),)&& it.clave==clave
        }
}