package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Memanggil UI Compose untuk ditampilkan ke layar
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    // State management untuk menyimpan nilai input pengguna
    var billAmount by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableIntStateOf(15) }
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val tipOptions = listOf(15, 18, 20)

    // Logika Perhitungan Tip
    val amount = billAmount.toDoubleOrNull() ?: 0.0
    var tip = amount * (tipPercentage / 100.0)
    if (roundUp) {
        tip = ceil(tip)
    }

    // Format hasil ke dalam bentuk mata uang (USD)
    val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Calculate Tip",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 1. Input Biaya Layanan
        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // 2. Pilihan Persentase Tip (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = "$tipPercentage%",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tip Percentage") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                // Memperbaiki warning menuAnchor dengan menambahkan type
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text("$selectionOption%") },
                        onClick = {
                            tipPercentage = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        // 3. Pengaturan Pembulatan Tip (Switch)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        // 4. Tampilan Hasil
        Text(
            text = "Tip Amount: $formattedTip",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}