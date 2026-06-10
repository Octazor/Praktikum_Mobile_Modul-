package com.example.modulmobilecat.ui.viewmodel

import com.example.modulmobilecat.domain.Cat

data class CatUiState(
    val appTitle: String = "Cat Gallery",
    val cats: List<Cat> = emptyList(),
    val selectedCat: Cat? = null,
    val selectedCatId: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val darkMode: Boolean = false,
    val showOnlyFavorites: Boolean = false
)

val CatUiState.visibleCats: List<Cat>
    get() = if (showOnlyFavorites) cats.filter { it.isFavorite } else cats
