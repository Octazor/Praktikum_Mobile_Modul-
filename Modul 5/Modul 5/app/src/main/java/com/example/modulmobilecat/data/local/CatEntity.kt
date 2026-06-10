package com.example.modulmobilecat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modulmobilecat.domain.Cat

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val breedName: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val isFavorite: Boolean,
    val cachedAt: Long
) {
    fun toDomain(): Cat = Cat(
        id = id,
        imageUrl = imageUrl,
        width = width,
        height = height,
        breedName = breedName,
        temperament = temperament,
        origin = origin,
        description = description,
        isFavorite = isFavorite
    )
}
