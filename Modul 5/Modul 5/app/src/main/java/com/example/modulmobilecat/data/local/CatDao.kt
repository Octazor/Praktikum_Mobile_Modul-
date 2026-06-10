package com.example.modulmobilecat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM cats ORDER BY cachedAt DESC")
    fun observeCats(): Flow<List<CatEntity>>

    @Query("SELECT * FROM cats WHERE id = :id")
    fun observeCat(id: String): Flow<CatEntity?>

    @Query("SELECT isFavorite FROM cats WHERE id = :id")
    suspend fun isFavorite(id: String): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCats(cats: List<CatEntity>)

    @Query("UPDATE cats SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: String, favorite: Boolean)

    @Query("DELETE FROM cats WHERE isFavorite = 0")
    suspend fun clearNonFavoriteCache()
}
