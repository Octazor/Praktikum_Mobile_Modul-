package com.example.modulmobilecat.data.repository

import com.example.modulmobilecat.data.local.CatDao
import com.example.modulmobilecat.data.local.CatEntity
import com.example.modulmobilecat.data.remote.ApiResult
import com.example.modulmobilecat.data.remote.CatApiService
import com.example.modulmobilecat.data.remote.CatImageDto
import com.example.modulmobilecat.domain.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class CatRepository(
    private val apiService: CatApiService,
    private val catDao: CatDao
) {
    fun observeCats(): Flow<List<Cat>> =
        catDao.observeCats().map { cats -> cats.map { it.toDomain() } }

    fun observeCat(id: String): Flow<Cat?> =
        catDao.observeCat(id).map { it?.toDomain() }

    suspend fun refreshCats(): ApiResult<Unit> {
        return when (val result = apiService.fetchCats()) {
            is ApiResult.Success -> {
                val entities = result.data.map { dto ->
                    val currentFavorite = catDao.isFavorite(dto.id) ?: false
                    dto.toEntity(currentFavorite)
                }
                catDao.upsertCats(entities)
                Timber.i("Data item masuk ke list: ${entities.size} kucing disimpan ke Room")
                ApiResult.Success(Unit)
            }
            is ApiResult.Error -> {
                Timber.e(result.throwable, "Gagal refresh data kucing: ${result.message}")
                result
            }
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    suspend fun setFavorite(cat: Cat) {
        catDao.setFavorite(cat.id, !cat.isFavorite)
        Timber.i("Status favorit berubah untuk ${cat.breedName}: ${!cat.isFavorite}")
    }

    suspend fun clearCache() {
        catDao.clearNonFavoriteCache()
        Timber.i("Cache non-favorit dibersihkan")
    }

    private fun CatImageDto.toEntity(isFavorite: Boolean): CatEntity {
        val breed = breeds.firstOrNull()
        return CatEntity(
            id = id,
            imageUrl = url,
            width = width,
            height = height,
            breedName = breed?.name ?: "Ini kucing",
            temperament = breed?.temperament ?: "Imoet, Majikan",
            origin = breed?.origin ?: "Gambar Kucing",
            description = breed?.description ?: "Data ras belum tersedia untuk gambar kucing ini.",
            isFavorite = isFavorite,
            cachedAt = System.currentTimeMillis()
        )
    }
}
