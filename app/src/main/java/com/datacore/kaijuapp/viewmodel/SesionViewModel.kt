package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.Usuario
import com.datacore.kaijuapp.repository.DatosDePrueba
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EstadoLogin(
    val usuario: String = "",
    val clave: String = "",
    val errorUsuario: String? = null,
    val errorClave: String? = null
)

class SesionViewModel : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _estadoLogin = MutableStateFlow(EstadoLogin())
    val estadoLogin: StateFlow<EstadoLogin> = _estadoLogin.asStateFlow()

    fun actualizarUsuario(valor: String) = _estadoLogin.update {
        it.copy(usuario = valor, errorUsuario = null)
    }

    fun actualizarClave(valor: String) = _estadoLogin.update {
        it.copy(clave = valor, errorClave = null)
    }

    fun iniciarSesion(): Boolean {
        val estado = _estadoLogin.value
        val nombre = estado.usuario.trim()

        val errorUsuario = when {
            nombre.isBlank() -> "Ingrese su usuario"
            else -> DatosDePrueba.usuarios
                .none { it.nombre.equals(nombre, ignoreCase = true) }
                .let { noExiste -> if (noExiste) "Usuario no encontrado" else null }
        }

        val usuarioEncontrado = if (errorUsuario == null) {
            DatosDePrueba.usuarios.first { it.nombre.equals(nombre, ignoreCase = true) }
        } else null

        val errorClave = when {
            usuarioEncontrado == null -> null
            estado.clave.isBlank() -> "Ingrese su clave"
            estado.clave != usuarioEncontrado.clave -> "Clave incorrecta"
            else -> null
        }

        if (errorUsuario != null || errorClave != null) {
            _estadoLogin.update {
                it.copy(errorUsuario = errorUsuario, errorClave = errorClave)
            }
            return false
        }

        _usuarioActual.value = usuarioEncontrado
        _estadoLogin.value = EstadoLogin()
        return true
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
        _estadoLogin.value = EstadoLogin()
    }
}
