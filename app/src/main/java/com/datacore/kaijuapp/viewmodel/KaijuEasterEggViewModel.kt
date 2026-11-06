package com.datacore.kaijuapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datacore.kaijuapp.model.KaijuEasterEggEstado
import com.datacore.kaijuapp.repository.PokemonRepository
import com.google.gson.JsonParseException
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KaijuEasterEggViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _estado = MutableStateFlow(KaijuEasterEggEstado())
    val estado = _estado.asStateFlow()
    private var clics = 0
    private var ultimoClic = 0L
    private companion object {
        const val CLICS_NECESARIOS = 7
        const val VENTANA_MS = 2000L // máximo entre clics para que sean "consecutivos"
    }
    /** Llamar en cada clic del apartado secreto. */
    fun onEasterEggClick() {
        if (_estado.value.visible) return
        val ahora = System.currentTimeMillis()
        clics = if (ahora - ultimoClic <= VENTANA_MS) clics + 1 else 1
        ultimoClic = ahora
        if (clics >= CLICS_NECESARIOS) {
            clics = 0
            invocarKaiju()
        }
    }
    fun invocarKaiju() {
        if (_estado.value.cargando) return
        _estado.update { KaijuEasterEggEstado(visible = true, cargando = true) }
        viewModelScope.launch {
            try {
                val pokemon = repository.obtenerKaijuAleatorio()
                _estado.update { it.copy(cargando = false, pokemon = pokemon) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                mostrarError("El servidor respondió con error ${e.code()}.")
            } catch (e: IOException) {
                mostrarError("No pude conectar. Revisa internet e intenta nuevamente.")
            } catch (e: JsonParseException) {
                mostrarError("La respuesta no tiene el formato esperado.")
            }
        }
    }
    fun cerrar() {
        _estado.update { KaijuEasterEggEstado() }
    }
    private fun mostrarError(mensaje: String) {
        _estado.update { it.copy(cargando = false, pokemon = null, error = mensaje) }
    }
}