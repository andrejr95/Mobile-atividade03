package com.example.projetohub

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.projetohub.R

class CalculadoraActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private lateinit var tvHistory: TextView

    private var currentInput: String = ""
    private var operand: Double? = null
    private var pendingOp: String? = null
    private var historyText: String = ""
    private var isResultDisplayed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        tvDisplay = findViewById(R.id.txtResultado)
        tvHistory = findViewById(R.id.txtHistory)

        val digits = listOf(
            "0" to R.id.btn0, "1" to R.id.btn1, "2" to R.id.btn2, "3" to R.id.btn3,
            "4" to R.id.btn4, "5" to R.id.btn5, "6" to R.id.btn6, "7" to R.id.btn7,
            "8" to R.id.btn8, "9" to R.id.btn9, "." to R.id.btnPonto
        )
        digits.forEach { (digit, id) ->
            findViewById<Button>(id).setOnClickListener { appendDigit(digit) }
        }

        val ops = listOf(
            getString(R.string.op_somar) to R.id.btnSomar,
            getString(R.string.op_subtrair) to R.id.btnSubtrair,
            getString(R.string.op_multiplicar) to R.id.btnMultiplicar,
            getString(R.string.op_dividir) to R.id.btnDividir
        )
        ops.forEach { (op, id) ->
            findViewById<Button>(id).setOnClickListener { onOperator(op) }
        }

        findViewById<Button>(R.id.btnIgual).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }

        // Referenciando os botões na nova hierarquia de layout
        findViewById<Button>(R.id.btnHomeCalc).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnToggleThemeCalc).setOnClickListener {
            toggleTheme()
        }

        updateDisplay()
        updateHistoryDisplay()
    }

    private fun appendDigit(d: String) {
        if (isResultDisplayed) {
            currentInput = ""
            operand = null
            pendingOp = null
            isResultDisplayed = false
        }
        if (d == "." && currentInput.contains(".")) return
        currentInput = if (currentInput == "0") d else currentInput + d
        updateDisplay()
    }

    private fun onOperator(op: String) {
        if (isResultDisplayed) {
            isResultDisplayed = false
        }
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull()
            if (value != null) {
                if (operand == null) {
                    operand = value
                } else {
                    val result = performOperation(operand!!, value, pendingOp)
                    val operation = "${operand} ${pendingOp} ${value} = ${result}\n"
                    historyText += operation
                    operand = result
                }
            }
            currentInput = ""
        }
        pendingOp = op
        updateDisplay()
        updateHistoryDisplay()
    }

    private fun onEquals() {
        if (operand != null && currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull() ?: return
            val result = performOperation(operand!!, value, pendingOp)

            val operation = "${operand} ${pendingOp} ${value} = ${result}\n"
            historyText += operation

            currentInput = result.toString()
            operand = null
            pendingOp = null
            isResultDisplayed = true

            updateDisplay()
            updateHistoryDisplay()
        }
    }

    private fun performOperation(a: Double, b: Double, op: String?): Double {
        return when (op) {
            getString(R.string.op_somar) -> a + b
            getString(R.string.op_subtrair) -> a - b
            getString(R.string.op_multiplicar) -> a * b
            getString(R.string.op_dividir) -> if (b == 0.0) {
                Toast.makeText(this, getString(R.string.calc_divisao_por_zero), Toast.LENGTH_SHORT).show()
                a
            } else a / b
            else -> b
        }
    }

    private fun clearAll() {
        currentInput = ""
        operand = null
        pendingOp = null
        historyText = ""
        isResultDisplayed = false
        updateDisplay()
        updateHistoryDisplay()
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val displayStr = StringBuilder()
        if (operand != null) {
            displayStr.append(operand)
            if (pendingOp != null) {
                displayStr.append(" ").append(pendingOp).append(" ")
            }
        }
        displayStr.append(currentInput)

        tvDisplay.text = if (displayStr.toString().isEmpty()) "0" else displayStr.toString()
    }

    private fun updateHistoryDisplay() {
        tvHistory.text = historyText
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