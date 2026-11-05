package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.datacore.kaijuapp.model.ErroresLogin
import com.datacore.kaijuapp.model.FormularioLoginEstado
import com.datacore.kaijuapp.model.Usuario
import com.datacore.kaijuapp.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SesionViewModel : ViewModel(){
    private val _formulario= MutableStateFlow(FormularioLoginEstado())
    val formulario: StateFlow<FormularioLoginEstado> =_formulario.asStateFlow()
    // null = nadie ha iniciado sesión
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()
    // Al escribir, se limpian los errores para que no queden en rojo
    fun actualizarUsuario(valor: String) = _formulario.update {
        it.copy(usuario = valor, errores = ErroresLogin())
    }
    fun actualizarClave(valor: String) = _formulario.update {
        it.copy(clave = valor, errores = ErroresLogin())
    }
    // Devuelve true si el login fue exitoso
    fun iniciarSesion(): Boolean {
        val estado = _formulario.value
        val errorUsuario = if (estado.usuario.isBlank()) "Ingresa tu usuario" else null
        val errorClave = if (estado.clave.isBlank()) "Ingresa tu contraseña" else null

        if (errorUsuario != null || errorClave != null) {
            _formulario.update {
                it.copy(errores = ErroresLogin(errorUsuario = errorUsuario, errorClave = errorClave))
            }
            return false
        }
        val usuario = UsuarioRepository.autenticar(estado.usuario, estado.clave)
        if (usuario == null) {
            _formulario.update {
                it.copy(errores = ErroresLogin(errorGeneral = "Usuario o contraseña incorrectos"))
            }
            return false
        }
        _usuarioActual.value = usuario
        _formulario.value = FormularioLoginEstado()  // limpia los campos
        return true
    }
    fun cerrarSesion() {
        _usuarioActual.value = null
        _formulario.value = FormularioLoginEstado()
    }
}