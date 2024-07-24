package com.example.dia11

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class Login : AppCompatActivity() {

    // Declaração das variáveis de UI
    private lateinit var inserirEmail: EditText
    private lateinit var inserirSenha: EditText
    private lateinit var buttonLogin: Button
    private lateinit var cadastro: TextView

    private val maximoTentativas = 5
    private var tentativas = 0
    private val tempoDesabilitado: Long = 5000 // 5 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicialização das views
        cadastro = findViewById(R.id.novaConta)
        inserirEmail = findViewById(R.id.emailSegundaTela)
        inserirSenha = findViewById(R.id.senhaSegundaTela)
        buttonLogin = findViewById(R.id.botaoLogin)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#2D3176")

        // Configuração do listener para o texto de cadastro
        cadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão de login
        buttonLogin.setOnClickListener {
            // Validação das credenciais
            validarCredenciais()
        }
    }

    private fun validarCredenciais() {
        val email = inserirEmail.text.toString().trim()
        val senha = inserirSenha.text.toString().trim()

        // Verifica se o campo de email está vazio
        if (email.isEmpty()) {
            // Exibe mensagem de erro
            Toast.makeText(this, "Por favor, insira seu email.", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se o campo de senha está vazio
        if (senha.isEmpty()) {
            // Exibe mensagem de erro
            Toast.makeText(this, "Por favor, insira sua senha.", Toast.LENGTH_SHORT).show()
            return
        }

        // Se ambos email e senha forem válidos, realiza o login
        realizarLogin(email, senha)
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
            // Email ou senha incorretos
            Toast.makeText(this, "Email ou senha incorretos.", Toast.LENGTH_SHORT).show()
        }
    }
}
