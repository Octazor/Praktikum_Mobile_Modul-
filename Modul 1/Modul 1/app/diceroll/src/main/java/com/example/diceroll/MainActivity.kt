package com.example.diceroll
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent adalah tempat kita memasukkan UI Jetpack Compose ke layar
        setContent {
            // Nah, di sinilah fungsi kamu akhirnya "digunakan" atau dipanggil!
            DiceRollerApp()
        }
    }
}

@Composable
fun DiceRollerApp() {
    // State untuk menyimpan nilai dadu (0 berarti dadu kosong/tampilan awal)
    var dice1 by remember { mutableIntStateOf(0) }
    var dice2 by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    // Tampilan awal aplikasi akan menampilkan 2 buah dadu kosong [cite: 16]
    val imageResource1 = getDiceImage(dice1)
    val imageResource2 = getDiceImage(dice2)

    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.Companion.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageResource1),
                contentDescription = "Dadu 1"
            )
            Image(
                painter = painterResource(id = imageResource2),
                contentDescription = "Dadu 2"
            )
        }

        Spacer(
            modifier = Modifier.Companion.height(
                32.dp
            )
        )

        Button(onClick = {
            // Mengacak angka dadu 1 s/d 6 saat menekan tombol "Roll" [cite: 21]
            dice1 = Random.Default.nextInt(1, 7)
            dice2 = Random.Default.nextInt(1, 7)

            // Mengimplementasikan kondisional untuk mengecek nilai dadu [cite: 11]
            if (dice1 == dice2) {
                // Pesan jika mendapat dadu double [cite: 28]
                Toast.makeText(
                    context,
                    "Selamat, anda dapat dadu double!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Pesan jika nilai dadu berbeda [cite: 21]
                Toast.makeText(
                    context,
                    "Anda belum beruntung!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(text = "Roll")
        }
    }
}

// Fungsi kondisional When untuk menentukan gambar dadu [cite: 11]
fun getDiceImage(diceValue: Int): Int {
    return when (diceValue) {
        0 -> R.drawable.dice_0 // Gambar 1. Tampilan Awal Aplikasi [cite: 20]
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}
