package com.example.modulmobilecat.domain

data class Cat(
    val id: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val breedName: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val isFavorite: Boolean
)
