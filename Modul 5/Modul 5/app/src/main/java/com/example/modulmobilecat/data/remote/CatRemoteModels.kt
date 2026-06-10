package com.example.modulmobilecat.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatImageDto(
    val id: String,
    val url: String,
    val width: Int = 0,
    val height: Int = 0,
    val breeds: List<CatBreedDto> = emptyList()
)

@Serializable
data class CatBreedDto(
    val name: String = "Ini Kucing",
    val temperament: String = "Imoet, Majikan",
    val origin: String = "Gambar Kucing",
    val description: String = "Data ras belum tersedia untuk gambar kucing ini.",
    @SerialName("life_span") val lifeSpan: String = ""
)
