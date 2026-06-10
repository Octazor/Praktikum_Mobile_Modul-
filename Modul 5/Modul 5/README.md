# Modul Mobile Cat App

Aplikasi Android Studio berbasis Jetpack Compose untuk praktikum Modul 4 dan Modul 5, dengan tema galeri kucing.

## Fitur

- UI 100% Jetpack Compose, tanpa layout XML.
- List gambar kucing dari The Cat API.
- Halaman detail kucing.
- Tombol explicit intent untuk membuka gambar kucing di browser.
- ViewModel dengan `ViewModelFactory` yang menerima parameter `String` untuk judul aplikasi.
- `StateFlow` untuk state list, loading, error, detail, filter favorit, dan dark mode.
- Timber logging untuk:
  - data item masuk ke list,
  - tombol Detail ditekan,
  - tombol Explicit Intent ditekan,
  - data kucing yang dipilih ketika berpindah ke halaman detail.
- Ktor Client sebagai networking library.
- KotlinX Serialization untuk parsing JSON.
- Coil untuk image loading.
- SharedPreferences untuk menyimpan pengaturan mode gelap.
- Room untuk menyimpan cache data kucing dan status favorit.

## Caching Strategy Room

Aplikasi memakai strategi cache-first dengan refresh manual/awal:

1. UI mengamati data Room melalui `Flow`.
2. Saat aplikasi dibuka atau tombol Refresh ditekan, repository mengambil data baru dari The Cat API.
3. Data hasil API disimpan ke Room dengan `OnConflictStrategy.REPLACE`.
4. Status favorit lama dipertahankan agar tidak hilang ketika data disegarkan.
5. Jika internet gagal, UI tetap bisa menampilkan data terakhir yang tersimpan di Room.

Strategi ini cocok untuk galeri kucing karena data gambar tidak harus real-time, tetapi pengguna tetap butuh aplikasi bisa dibuka ketika koneksi buruk.

## Cara Menjalankan

1. Buka folder `ModulMobileCatApp` di Android Studio.
2. Tunggu Gradle Sync selesai.
3. Jalankan aplikasi di emulator atau perangkat Android.
4. Pastikan perangkat memiliki koneksi internet untuk mengambil gambar kucing pertama kali.

Jika Android Studio pernah mencoba memakai Gradle `9.0-milestone-1`, pilih **File > Sync Project with Gradle Files** lagi setelah wrapper membaca `gradle-8.10.2-bin.zip`.

## Verifikasi

Proyek sudah diverifikasi dengan:

```bash
./gradlew assembleDebug --no-daemon
```

Hasilnya sukses setelah wrapper dikunci ke Gradle `8.10.2` dan target JVM Java/Kotlin disamakan ke Java 17.

## Catatan Debugger

Breakpoint yang relevan dapat dipasang di:

- `CatRepository.refreshCats()` saat data dari API disimpan ke Room.
- `CatViewModel.openDetail()` saat tombol Detail ditekan.
- `CatViewModel.logExplicitIntent()` saat tombol Buka gambar ditekan.

Fungsi debugger adalah membantu memeriksa alur program, nilai variabel, dan penyebab bug saat aplikasi berjalan. `Step Into` masuk ke fungsi yang dipanggil, `Step Over` menjalankan baris saat ini tanpa masuk ke fungsi, dan `Step Out` keluar dari fungsi yang sedang diperiksa.
