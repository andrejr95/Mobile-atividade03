package com.example.projetohub

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class ImcActivity : AppCompatActivity() {

    private lateinit var pesoEditText: EditText
    private lateinit var alturaEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var imcTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var pesoIdealTextView: TextView
    private lateinit var statusImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        pesoEditText = findViewById(R.id.pesoEditText)
        alturaEditText = findViewById(R.id.alturaEditText)
        calculateButton = findViewById(R.id.calculateButton)
        imcTextView = findViewById(R.id.imcTextView)
        resultTextView = findViewById(R.id.resultTextView)
        pesoIdealTextView = findViewById(R.id.pesoIdealTextView)
        statusImageView = findViewById(R.id.statusImageView)

        calculateButton.setOnClickListener {
            calculateIMC()
        }

        findViewById<Button>(R.id.btnHomeImc).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnToggleThemeImc).setOnClickListener {
            toggleTheme()
        }
    }

    private fun calculateIMC() {
        val pesoString = pesoEditText.text.toString()
        val alturaString = alturaEditText.text.toString()

        if (pesoString.isNotEmpty() && alturaString.isNotEmpty()) {
            val peso = pesoString.toDoubleOrNull() ?: 0.0
            val altura = alturaString.toDoubleOrNull() ?: 0.0

            if (peso > 0 && altura > 0) {
                val alturaMetros = altura / 100.0
                val imc = peso / (alturaMetros * alturaMetros)

                imcTextView.text = getString(R.string.imc_result, imc)

                val status = getStatusForIMC(imc)
                resultTextView.text = getString(R.string.imc_status_title, status)
                resultTextView.setTextColor(ContextCompat.getColor(this, getColorForStatus(status)))

                val pesoIdealMessage = calculatePesoIdeal(peso, alturaMetros, imc)
                pesoIdealTextView.text = pesoIdealMessage
                pesoIdealTextView.visibility = View.VISIBLE // Adicionado: torna a view visível

                val imageResId = getImageForStatus(status)
                if (imageResId != 0) {
                    statusImageView.setImageResource(imageResId)
                    statusImageView.visibility = View.VISIBLE
                } else {
                    statusImageView.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, R.string.imc_valores_validos, Toast.LENGTH_SHORT).show()
                pesoIdealTextView.visibility = View.GONE // Adicionado: esconde a view em caso de erro
                statusImageView.visibility = View.GONE // Adicionado: esconde a imagem em caso de erro
            }
        } else {
            Toast.makeText(this, R.string.imc_preencher_campos, Toast.LENGTH_SHORT).show()
            pesoIdealTextView.visibility = View.GONE // Adicionado: esconde a view em caso de erro
            statusImageView.visibility = View.GONE // Adicionado: esconde a imagem em caso de erro
        }
    }

    private fun getStatusForIMC(imc: Double): String {
        return when {
            imc < 18.5 -> getString(R.string.status_abaixo_peso)
            imc < 25 -> getString(R.string.status_peso_normal)
            imc < 30 -> getString(R.string.status_sobrepeso)
            imc < 35 -> getString(R.string.status_obesidade_1)
            imc < 40 -> getString(R.string.status_obesidade_2)
            else -> getString(R.string.status_obesidade_morbida)
        }
    }

    private fun getColorForStatus(status: String): Int {
        return when (status) {
            getString(R.string.status_abaixo_peso) -> R.color.imc_abaixo_peso
            getString(R.string.status_peso_normal) -> R.color.imc_peso_normal
            getString(R.string.status_sobrepeso) -> R.color.imc_sobrepeso
            getString(R.string.status_obesidade_1) -> R.color.imc_obesidade_1
            getString(R.string.status_obesidade_2) -> R.color.imc_obesidade_2
            getString(R.string.status_obesidade_morbida) -> R.color.imc_obesidade_morbida
            else -> android.R.color.black
        }
    }

    private fun getImageForStatus(status: String): Int {
        return when (status) {
            getString(R.string.status_abaixo_peso) -> R.drawable.abaixo
            getString(R.string.status_peso_normal) -> R.drawable.normal
            getString(R.string.status_sobrepeso) -> R.drawable.sobrepeso
            getString(R.string.status_obesidade_1) -> R.drawable.obesidade1
            getString(R.string.status_obesidade_2) -> R.drawable.obesidade2
            getString(R.string.status_obesidade_morbida) -> R.drawable.obesidade3
            else -> 0
        }
    }

    private fun calculatePesoIdeal(peso: Double, alturaMetros: Double, imc: Double): String {
        val pesoMinimoNormal = 18.5 * (alturaMetros * alturaMetros)
        val pesoMaximoNormal = 24.9 * (alturaMetros * alturaMetros)

        return when {
            imc < 18.5 -> {
                val pesoGanhar = pesoMinimoNormal - peso
                getString(R.string.imc_peso_ganhar, pesoGanhar)
            }
            imc > 24.9 -> {
                val pesoPerder = peso - pesoMaximoNormal
                getString(R.string.imc_peso_perder, pesoPerder)
            }
            else -> getString(R.string.imc_peso_normal_parabens)
        }
    }

    private fun toggleTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newMode = if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(newMode)
    }
}