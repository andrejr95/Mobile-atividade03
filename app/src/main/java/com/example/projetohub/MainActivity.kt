package com.example.projetohub

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.projetohub.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPlacar).setOnClickListener {
            val intent = Intent(this, PlacarActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnCalculadora).setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnImc).setOnClickListener {
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnToggleThemeMain).setOnClickListener {
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
    }
}