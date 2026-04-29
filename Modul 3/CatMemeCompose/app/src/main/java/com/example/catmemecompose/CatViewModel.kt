package com.example.catmemecompose

import androidx.lifecycle.ViewModel

class CatViewModel : ViewModel() {
    val catList = listOf(
        Cat(1, "Evil Larry", "Shorthair Hitam", "Evil Larry merujuk pada beberapa entitas, terutama meme kucing Oriental Shorthair hitam bernama asli Dexter dengan tatapan datar yang populer di media sosial. Ia sering digambarkan sebagai karakter jahat, \"menyeramkan\", atau \"villain\" dalam konten viral.", R.drawable.cat1, "https://www.instagram.com/hobbikats/"),
        Cat(2, "Mr. Pxxxxx Cat", "Mujaer atau Tabby", "Kucing ini merujuk pada humor brainrot yang sering di instagram sebagai candaan yang mengarah ke dewasa tapi sebenarnya hanya murni absurd dan brainrot jokes.", R.drawable.cat2, "https://www.instagram.com/kiji1674/"),
        Cat(3, "OIIA Cat", "Munchkin Tabby", "Ethel, yang dikenal sebagai \"OIIA Cat\" di media sosial, adalah kucing penyelamat berusia 14 tahun yang viral karena meme berputar. Ethel buta, ompong, dan bertubuh mungil/kerdil, sering berputar-putar saat waktu makan. Diadopsi pada 2018 dari The Cat Welfare Association, Ethel kini hidup bahagia dan sehat dengan pemiliknya, Donna, setelah sebelumnya obesitas dan berjuang dengan kondisi kesehatan.", R.drawable.cat3, "https://www.instagram.com/oiiacat_ethel/"),
        Cat(4, "Freaky Cat", "Orange Cat", "Rigby Cat (iamrigbycat) adalah kucing selebriti internet populer di TikTok (2.8M+ pengikut) dan Instagram yang dikenal memiliki fainting goat syndrome (sindrom kambing pingsan), menyebabkan ia sering bertingkah lucu dan kaku. Kadang juga dikenal sebagai Freaky Cat.", R.drawable.cat4, "https://www.instagram.com/iamrigbycat/"),
        Cat(5, "Smudge Cat", "Domestic Shorthair", "Smudge Cat adalah kucing putih asal Ottawa, Kanada, yang viral sebagai meme \"Wanita Berteriak pada Kucing\" (Woman Yelling at a Cat). Terkenal karena ekspresi bingung/tidak suka saat duduk di depan sepiring salad, Smudge menjadi ikon internet. Meme ini menggabungkan fotonya dengan Taylor Armstrong dari Real Housewives of Beverly Hills.", R.drawable.cat5, "https://www.instagram.com/smudge_lord/")
    )
}