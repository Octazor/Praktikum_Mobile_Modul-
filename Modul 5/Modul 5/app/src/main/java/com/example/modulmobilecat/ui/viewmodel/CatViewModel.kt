package com.example.modulmobilecat.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.modulmobilecat.data.local.UserPreferences
import com.example.modulmobilecat.data.remote.ApiResult
import com.example.modulmobilecat.data.repository.CatRepository
import com.example.modulmobilecat.domain.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class CatViewModel(
    private val appTitle: String,
    private val repository: CatRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val internalState = MutableStateFlow(CatUiState(appTitle = appTitle, isLoading = true))

    val uiState: StateFlow<CatUiState> = combine(
        internalState,
        repository.observeCats(),
        userPreferences.darkMode,
        internalState.flatMapLatest { state ->
            val id = state.selectedCatId
            if (id == null) MutableStateFlow(null) else repository.observeCat(id)
        }
    ) { state, cats, darkMode, selectedCat ->
        state.copy(
            cats = cats,
            selectedCat = selectedCat,
            darkMode = darkMode
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CatUiState(appTitle = appTitle, isLoading = true)
    )

    init {
        refreshCats()
    }

    fun refreshCats() {
        viewModelScope.launch {
            internalState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.refreshCats()) {
                is ApiResult.Success -> internalState.update { it.copy(isLoading = false) }
                is ApiResult.Error -> internalState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                ApiResult.Loading -> internalState.update { it.copy(isLoading = true) }
            }
        }
    }

    fun openDetail(cat: Cat) {
        Timber.i("Tombol Detail ditekan untuk ${cat.breedName}")
        Timber.i("Data list dipilih: id=${cat.id}, url=${cat.imageUrl}, asal=${cat.origin}")
        internalState.update { it.copy(selectedCatId = cat.id, errorMessage = null) }
    }

    fun closeDetail() {
        internalState.update { it.copy(selectedCatId = null) }
    }

    fun logExplicitIntent(cat: Cat) {
        Timber.i("Tombol Explicit Intent ditekan untuk ${cat.breedName}: ${cat.imageUrl}")
    }

    fun toggleFavorite(cat: Cat) {
        viewModelScope.launch {
            repository.setFavorite(cat)
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        userPreferences.setDarkMode(enabled)
    }

    fun toggleFavoriteFilter() {
        internalState.update { it.copy(showOnlyFavorites = !it.showOnlyFavorites) }
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearCache()
        }
    }
}

class CatViewModelFactory(
    private val appTitle: String,
    private val repository: CatRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            return CatViewModel(appTitle, repository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
