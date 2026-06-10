package com.example.modulmobilecat.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CatApiService(
    private val client: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
) {
    suspend fun fetchCats(limit: Int = 20): ApiResult<List<CatImageDto>> = try {
        val cats = client.get("https://api.thecatapi.com/v1/images/search") {
            parameter("limit", limit)
            parameter("has_breeds", 1)
        }.body<List<CatImageDto>>()
        ApiResult.Success(cats)
    } catch (throwable: Throwable) {
        ApiResult.Error(
            message = throwable.message ?: "Gagal mengambil data kucing dari internet.",
            throwable = throwable
        )
    }
}
