package com.example.dia11

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class Login : AppCompatActivity() {

    private lateinit var inserirEmail: EditText
    private lateinit var inserirSenha: EditText
    private lateinit var verificarEmail: TextView
    private lateinit var verificarSenha: TextView
    private lateinit var buttonLogin: Button
    private lateinit var cadastro: TextView

    private val maximoTentativas = 5
    private var tentativas = 0
    private val tempoDesabilitado: Long = 5000 // 5 segundos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cadastro = findViewById(R.id.novaConta)
        inserirEmail = findViewById(R.id.emailSegundaTela)
        inserirSenha = findViewById(R.id.senhaSegundaTela)
        verificarEmail = findViewById(R.id.emailError)
        verificarSenha = findViewById(R.id.senhaError)
        buttonLogin = findViewById(R.id.botaoLogin)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#2D3176")

        cadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            validarCredenciais()
        }
    }

    private fun validarCredenciais() {
        val email = inserirEmail.text.toString().trim()
        val senha = inserirSenha.text.toString().trim()

        var emailValido = true
        var senhaValida = true

        if (!isValidEmail(email)) {
            verificarEmail.visibility = View.VISIBLE
            emailValido = false
        } else {
            verificarEmail.visibility = View.GONE
        }

        if (senha.isEmpty() || senha.length < 8) {
            verificarSenha.visibility = View.VISIBLE
            senhaValida = false
        } else {
            verificarSenha.visibility = View.GONE
        }

        if (emailValido && senhaValida) {
            realizarLogin(email, senha)
        } else {
            tentativas++
            if (tentativas >= maximoTentativas) {
                desabilitarLoginTemporariamente()
            }
        }
    }

    private fun realizarLogin(email: String, senha: String) {
        val file = File(filesDir, "usuarios.txt")
        val usuarios = try {
            file.readLines().map {
                val partes = it.split(",")
                Usuario(partes[0], partes[1], partes[2])
            }
        } catch (e: IOException) {
            e.printStackTrace()
            listOf() // Retorna uma lista vazia em caso de erro
        }

        val usuario = usuarios.find { it.email == email }

        if (usuario != null && usuario.senha == senha) {
            // Login bem-sucedido
            Toast.makeText(this, "Login bem-sucedido. Seja bem-vindo, ${usuario.nome}!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Email ou senha incorretos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun desabilitarLoginTemporariamente() {
        buttonLogin.isEnabled = false
        inserirEmail.isEnabled = false
        inserirSenha.isEnabled = false

        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        handler.postDelayed({
            buttonLogin.isEnabled = true
            inserirEmail.isEnabled = true
            inserirSenha.isEnabled = true
            tentativas = 0
        }, tempoDesabilitado)
    }
}
