package com.datacore.kaijuapp.model

import com.datacore.kaijuapp.data.remote.PokemonDto

data class KaijuEasterEggEstado(
    val visible: Boolean = false,   // diálogo abierto o cerrado
    val cargando: Boolean = false,
    val pokemon: PokemonDto? = null,
    val error: String? = null
)