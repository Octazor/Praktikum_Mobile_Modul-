package com.example.diceroll

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan file Kotlin ini dengan tampilan XML
        setContentView(R.layout.activity_main)

        // Mengenali komponen UI dari XML berdasarkan ID-nya
        val ivDice1: ImageView = findViewById(R.id.iv_dice1)
        val ivDice2: ImageView = findViewById(R.id.iv_dice2)
        val btnRoll: Button = findViewById(R.id.btn_roll)

        // Memberikan aksi ketika tombol "Roll" diklik
        btnRoll.setOnClickListener {
            // Mengacak angka dadu 1 s/d 6
            val dice1 = Random.nextInt(1, 7)
            val dice2 = Random.nextInt(1, 7)

            // Mengubah gambar dadu sesuai angka yang didapat
            ivDice1.setImageResource(getDiceImage(dice1))
            ivDice2.setImageResource(getDiceImage(dice2))

            // Logika kondisional When/If untuk mengecek nilai dadu
            if (dice1 == dice2) {
                // Pesan jika mendapat dadu double
                Toast.makeText(this, "Selamat, anda dapat dadu double!", Toast.LENGTH_SHORT).show()
            } else {
                // Pesan jika nilai dadu berbeda
                Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi kondisional When untuk menentukan gambar dadu
    private fun getDiceImage(diceValue: Int): Int {
        return when (diceValue) {
            0 -> R.drawable.dice_0
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}