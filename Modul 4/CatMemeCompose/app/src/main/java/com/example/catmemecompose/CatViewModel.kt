package com.example.catmemecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

sealed class CatUiEvent {
    data object Idle : CatUiEvent()
    data class NavigateToDetail(val cat: Cat) : CatUiEvent()
    data class OpenUrl(val url: String) : CatUiEvent()
}

class CatViewModel(private val category: String) : ViewModel() {

    private val _catList = MutableStateFlow<List<Cat>>(emptyList())
    val catList: StateFlow<List<Cat>> = _catList.asStateFlow()

    private val _uiEvent = MutableStateFlow<CatUiEvent>(CatUiEvent.Idle)
    val uiEvent: StateFlow<CatUiEvent> = _uiEvent.asStateFlow()

    init {
        // Logging category yang diterima dari Factory
        Timber.d("ViewModel initialized with category: $category")
        loadCats()
    }

    private fun loadCats() {
        val data = listOf(
            Cat(1, "Evil Larry", "Shorthair Hitam", "Evil Larry merujuk pada beberapa entitas, terutama meme kucing Oriental Shorthair hitam bernama asli Dexter dengan tatapan datar yang populer di media sosial.", R.drawable.cat1, "https://www.instagram.com/hobbikats/"),
            Cat(2, "Mr. PXXXX Cat", "Tabby Cat", "Kucing ini merujuk pada humor brainrot yang sering di instagram sebagai candaan yang mengarah ke dewasa tapi sebenarnya hanya murni absurd dan brainrot jokes.", R.drawable.cat2, "https://www.instagram.com/kiji1674/"),
            Cat(3, "OIIA Cat", "Munchkin Tabby", "Ethel, yang dikenal sebagai \"OIIA Cat\" di media sosial, adalah kucing penyelamat berusia 14 tahun yang viral karena meme berputar.", R.drawable.cat3, "https://www.instagram.com/oiiacat_ethel/"),
            Cat(4, "Freaky Cat", "Orange Cat", "Rigby Cat (iamrigbycat) adalah kucing selebriti internet populer di TikTok dan Instagram yang dikenal memiliki fainting goat syndrome.", R.drawable.cat4, "https://www.instagram.com/iamrigbycat/"),
            Cat(5, "Smudge Cat", "Domestic Shorthair", "Smudge Cat adalah kucing putih asal Ottawa, Kanada, yang viral sebagai meme \"Wanita Berteriak pada Kucing\".", R.drawable.cat5, "https://www.instagram.com/smudge_lord/")
        )
        
        // Log saat data item masuk ke dalam list
        data.forEach {
            Timber.d("Item masuk ke dalam list: ${it.name}")
        }
        
        _catList.value = data
    }

    fun onDetailClick(cat: Cat) {
        // Log saat tombol Detail ditekan
        Timber.d("Tombol Detail ditekan")
        // Log data dari list yang dipilih ketika berpindah ke halaman Detail
        Timber.d("Berpindah ke halaman Detail dengan data: $cat")
        _uiEvent.value = CatUiEvent.NavigateToDetail(cat)
    }

    fun onExplicitIntentClick(url: String) {
        // Log saat tombol Explicit Intent ditekan
        Timber.d("Tombol Explicit Intent ditekan untuk URL: $url")
        _uiEvent.value = CatUiEvent.OpenUrl(url)
    }

    fun resetEvent() {
        _uiEvent.value = CatUiEvent.Idle
    }

    class Factory(private val category: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CatViewModel(category) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}