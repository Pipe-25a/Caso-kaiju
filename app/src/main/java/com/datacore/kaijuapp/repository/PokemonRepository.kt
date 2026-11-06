package com.datacore.kaijuapp.repository

import com.datacore.kaijuapp.data.remote.PokeApi
import com.datacore.kaijuapp.data.remote.PokemonDto

class PokemonRepository(private val api: PokeApi) {
    // Tyranitar, Groudon, Kyogre, Aggron, Duraludon, Hydreigon
    private val kaijuIds = listOf(248, 383, 382, 306, 884, 635)
    suspend fun obtenerKaijuAleatorio(): PokemonDto =
        api.obtenerPokemon(kaijuIds.random())
}