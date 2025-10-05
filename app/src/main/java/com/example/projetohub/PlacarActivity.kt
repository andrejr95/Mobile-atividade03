package com.example.projetohub

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.projetohub.R

class PlacarActivity : AppCompatActivity() {

    private lateinit var placarTimeA: TextView
    private lateinit var placarTimeB: TextView
    private lateinit var faltasTimeA: TextView
    private lateinit var faltasTimeB: TextView

    private var pontuacaoA = 0
    private var faltasA = 0
    private var pontuacaoB = 0
    private var faltasB = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)

        placarTimeA = findViewById(R.id.placarTimeA)
        placarTimeB = findViewById(R.id.placarTimeB)
        faltasTimeA = findViewById(R.id.contadorFaltasA)
        faltasTimeB = findViewById(R.id.contadorFaltasB)

        findViewById<Button>(R.id.tresPontosA).setOnClickListener { adicionarPontos(3, "A") }
        findViewById<Button>(R.id.doisPontosA).setOnClickListener { adicionarPontos(2, "A") }
        findViewById<Button>(R.id.tiroLivreA).setOnClickListener { adicionarPontos(1, "A") }
        findViewById<Button>(R.id.faltaTimeA).setOnClickListener { adicionarFalta("A") }

        findViewById<Button>(R.id.tresPontosB).setOnClickListener { adicionarPontos(3, "B") }
        findViewById<Button>(R.id.doisPontosB).setOnClickListener { adicionarPontos(2, "B") }
        findViewById<Button>(R.id.tiroLivreB).setOnClickListener { adicionarPontos(1, "B") }
        findViewById<Button>(R.id.faltaTimeB).setOnClickListener { adicionarFalta("B") }

        findViewById<Button>(R.id.reiniciarPartida).setOnClickListener { reiniciarPartida() }

        // Referenciando os botões na nova hierarquia de layout
        findViewById<Button>(R.id.btnHomePlacar).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnToggleThemePlacar).setOnClickListener {
            toggleTheme()
        }

        updatePlacar()
        updateFaltas()
    }

    private fun adicionarPontos(pontos: Int, time: String) {
        if (time == "A") {
            pontuacaoA += pontos
        } else {
            pontuacaoB += pontos
        }
        updatePlacar()
    }

    private fun adicionarFalta(time: String) {
        if (time == "A") {
            faltasA++
        } else {
            faltasB++
        }
        updateFaltas()
    }

    private fun reiniciarPartida() {
        pontuacaoA = 0
        pontuacaoB = 0
        faltasA = 0
        faltasB = 0
        findViewById<EditText>(R.id.timeA).setText(R.string.placar_nome_time_a)
        findViewById<EditText>(R.id.timeB).setText(R.string.placar_nome_time_b)
        updatePlacar()
        updateFaltas()
    }

    private fun updatePlacar() {
        placarTimeA.text = pontuacaoA.toString()
        placarTimeB.text = pontuacaoB.toString()
    }

    private fun updateFaltas() {
        faltasTimeA.text = getString(R.string.placar_faltas, faltasA)
        faltasTimeB.text = getString(R.string.placar_faltas, faltasB)
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