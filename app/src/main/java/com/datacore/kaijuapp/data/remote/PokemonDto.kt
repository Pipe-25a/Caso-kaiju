package com.datacore.kaijuapp.data.remote

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val id: Int,
    val name: String,
    val sprites: SpritesDto,
    val types: List<PokemonTypeDto>
)

data class SpritesDto(
    @SerializedName("front_default") val frontDefault: String?,
    val other: OtherSpritesDto?
)

data class OtherSpritesDto(
    @SerializedName("official-artwork") val officialArtwork: ArtworkDto?
)

data class ArtworkDto(
    @SerializedName("front_default") val frontDefault: String?
)

data class PokemonTypeDto(val slot: Int, val type: TypeDto)

data class TypeDto(val name: String)