package com.example.projetohub

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log // Logs: i, d, v
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private val TAG = "HubActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "HubActivity iniciada com sucesso. (Nível Info)") // Evento importante

        findViewById<Button>(R.id.btnPlacar).setOnClickListener {
            Log.d(TAG, "Navegando para PlacarActivity. (Nível Debug)") // Fluxo de navegação
            val intent = Intent(this, PlacarActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnCalculadora).setOnClickListener {
            Log.d(TAG, "Navegando para CalculadoraActivity. (Nível Debug)") // Fluxo de navegação
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnImc).setOnClickListener {
            Log.d(TAG, "Navegando para ImcActivity. (Nível Debug)") // Fluxo de navegação
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnToggleThemeMain).setOnClickListener {
            Log.v(TAG, "Botão 'Alternar Tema' clicado. Executando toggle. (Nível Verbose)") // Ação detalhada
            toggleTheme()
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
        Log.i(TAG, "Tema alterado para: ${if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "Escuro" else "Claro"}. (Nível Info)") // Evento de estado
    }
}