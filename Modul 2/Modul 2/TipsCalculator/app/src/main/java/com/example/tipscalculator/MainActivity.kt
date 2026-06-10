package com.example.tipscalculator
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel untuk setiap View dari XML
    private lateinit var etBillAmount: TextInputEditText
    private lateinit var spinnerTipPercentage: Spinner
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var tvTipAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi View (menghubungkan variabel dengan ID di XML)
        etBillAmount = findViewById(R.id.etBillAmount)
        spinnerTipPercentage = findViewById(R.id.spinnerTipPercentage)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        tvTipAmount = findViewById(R.id.tvTipAmount)

        // 2. Pasang Listener agar aplikasi reaktif
        setupListeners()
    }

    private fun setupListeners() {
        // Listener untuk memantau perubahan teks saat user mengetik Bill Amount
        etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTip()
            }
        })

        // Listener untuk memantau pilihan Spinner (Tip Percentage)
        spinnerTipPercentage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateTip()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Listener untuk memantau status Switch (Round Up)
        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }

    private fun calculateTip() {
        // Ambil teks dari input dan ubah menjadi Double (angka desimal)
        val stringInTextField = etBillAmount.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // Jika input kosong atau tidak valid, tampilkan $0.00
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Tentukan persentase berdasarkan posisi item Spinner yang dipilih
        // Posisi 0 = 15%, Posisi 1 = 18%, Posisi 2 = 20% (sesuai arrays.xml)
        val tipPercentage = when (spinnerTipPercentage.selectedItemPosition) {
            0 -> 0.15
            1 -> 0.18
            else -> 0.20
        }

        // Hitung tip awal
        var tip = cost * tipPercentage

        // Jika switch round-up diaktifkan, bulatkan ke atas menggunakan fungsi ceil
        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        // Tampilkan hasil akhir
        displayTip(tip)
    }

    @SuppressLint("SetTextI18n")
    private fun displayTip(tip: Double) {
        // Format angka menjadi format mata uang US Dollar
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        tvTipAmount.text = "Tip Amount: $formattedTip"
    }
}