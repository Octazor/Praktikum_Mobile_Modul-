package com.example.compose
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent adalah tempat kita memasukkan UI Jetpack Compose ke layar
        setContent {
            // Nah, di sinilah fungsi kamu akhirnya "digunakan" atau dipanggil!
            DiceRollerApp()
        }
    }
}

@androidx.compose.runtime.Composable
fun DiceRollerApp() {
    // State untuk menyimpan nilai dadu (0 berarti dadu kosong/tampilan awal)
    var dice1 by androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(0) }
    var dice2 by androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(0) }
    val context = androidx.compose.ui.platform.LocalContext.current

    // Tampilan awal aplikasi akan menampilkan 2 buah dadu kosong [cite: 16]
    val imageResource1 = getDiceImage(dice1)
    val imageResource2 = getDiceImage(dice2)

    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.ui.Modifier.Companion.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.Companion.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
            modifier = androidx.compose.ui.Modifier.Companion.fillMaxWidth()
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = imageResource1),
                contentDescription = "Dadu 1"
            )
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = imageResource2),
                contentDescription = "Dadu 2"
            )
        }

        androidx.compose.foundation.layout.Spacer(
            modifier = androidx.compose.ui.Modifier.Companion.height(
                32.dp
            )
        )

        androidx.compose.material3.Button(onClick = {
            // Mengacak angka dadu 1 s/d 6 saat menekan tombol "Roll" [cite: 21]
            dice1 = kotlin.random.Random.Default.nextInt(1, 7)
            dice2 = kotlin.random.Random.Default.nextInt(1, 7)

            // Mengimplementasikan kondisional untuk mengecek nilai dadu [cite: 11]
            if (dice1 == dice2) {
                // Pesan jika mendapat dadu double [cite: 28]
                android.widget.Toast.makeText(
                    context,
                    "Selamat, anda dapat dadu double!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                // Pesan jika nilai dadu berbeda [cite: 21]
                android.widget.Toast.makeText(
                    context,
                    "Anda belum beruntung!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            androidx.compose.material3.Text(text = "Roll")
        }
    }
}

// Fungsi kondisional When untuk menentukan gambar dadu [cite: 11]
fun getDiceImage(diceValue: Int): Int {
    return when (diceValue) {
        0 -> R.drawable.dice_0 // Gambar 1. Tampilan Awal Aplikasi
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}