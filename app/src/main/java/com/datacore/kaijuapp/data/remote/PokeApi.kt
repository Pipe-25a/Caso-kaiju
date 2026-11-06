package com.datacore.kaijuapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {
    @GET("pokemon/{id}")
    suspend fun obtenerPokemon(@Path("id") id: Int): PokemonDto
}