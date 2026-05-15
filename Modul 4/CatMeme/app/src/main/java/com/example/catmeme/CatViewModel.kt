package com.example.catmeme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class CatViewModel(private val category: String = "") : ViewModel() {

    private val _catList = MutableStateFlow<List<Cat>>(emptyList())
    val catList: StateFlow<List<Cat>> = _catList.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<Cat?>(null)
    val navigateToDetail: StateFlow<Cat?> = _navigateToDetail.asStateFlow()

    init {
        loadCats()
    }

    private fun loadCats() {
        val data = listOf(
            Cat(
                id = 1,
                name = "Evil Larry ($category)",
                ras = "Shorthair Hitam",
                description = "Evil Larry merujuk pada beberapa entitas, terutama meme kucing Oriental Shorthair hitam bernama asli Dexter dengan tatapan datar yang populer di media sosial. Ia sering digambarkan sebagai karakter jahat, \"menyeramkan\", atau \"villain\" dalam konten viral.",
                imageResId = R.drawable.cat1,
                instagram = "https://www.instagram.com/hobbikats/"
            ),
            Cat(
                id = 2,
                name = "Mr. Pxxxxx Cat ($category)",
                ras = "Tabby Cat",
                description = "Kucing ini merujuk pada humor brainrot yang sering di instagram sebagai candaan yang mengarah ke dewasa tapi sebenarnya hanya murni absurd dan brainrot jokes",
                imageResId = R.drawable.cat2,
                instagram = "https://www.instagram.com/kiji1674/"
            ),
            Cat(
                id = 3,
                name = "OIIA Cat ($category)",
                ras = "Munchkin Tabby",
                description = "Ethel, yang dikenal sebagai \"OIIA Cat\" di media sosial, adalah kucing penyelamat berusia 14 tahun yang viral karena meme berputar. Ethel buta, ompong, dan bertubuh mungil/kerdil, sering berputar-putar saat waktu makan. Diadopsi pada 2018 dari The Cat Welfare Association, Ethel kini hidup bahagia dan sehat dengan pemiliknya, Donna, setelah sebelumnya obesitas dan berjuang dengan kondisi kesehatan.",
                imageResId = R.drawable.cat3,
                instagram = "https://www.instagram.com/oiiacat_ethel/"
            ),
            Cat(
                id = 4,
                name = "Freaky Cat ($category)",
                ras = "Orange Cat",
                description = "Rigby Cat (iamrigbycat) adalah kucing selebriti internet populer di TikTok (2.8M+ pengikut) dan Instagram yang dikenal memiliki fainting goat syndrome (sindrom kambing pingsan), menyebabkan ia sering bertingkah lucu dan kaku. Kadang juga dikenal sebagai Freaky Cat",
                imageResId = R.drawable.cat4,
                instagram = "https://www.instagram.com/iamrigbycat/"
            ),
            Cat(
                id = 5,
                name = "Smudge Cat ($category)",
                ras = "Domestic Shorthair",
                description = "Smudge Cat adalah kucing putih asal Ottawa, Kanada, yang viral sebagai meme \"Wanita Berteriak pada Kucing\" (Woman Yelling at a Cat). Terkenal karena ekspresi bingung/tidak suka saat duduk di depan sepiring salad, Smudge menjadi ikon internet. Meme ini menggabungkan fotonya dengan Taylor Armstrong dari Real Housewives of Beverly Hills.",
                imageResId = R.drawable.cat5,
                instagram = "https://www.instagram.com/smudge_lord/"
            )
        )
        _catList.value = data
        Timber.d("Data item masuk ke dalam list: %s", data.size)
    }

    fun onDetailClicked(cat: Cat) {
        Timber.d("Tombol Detail ditekan")
        Timber.d("Data dari list yang dipilih: %s", cat.name)
        _navigateToDetail.value = cat
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}

class CatViewModelFactory(private val category: String = "") : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatViewModel(category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
