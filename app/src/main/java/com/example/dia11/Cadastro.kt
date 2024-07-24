package com.example.dia11

import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class Cadastro : AppCompatActivity() {

    private lateinit var inserirEmail: EditText
    private lateinit var inserirSenha: EditText
    private lateinit var inserirNome: EditText
    private lateinit var botaoCadastrar: Button
    private lateinit var textoSenha1: EditText
    private lateinit var textoSenha2: EditText
    private lateinit var imagemMostrarSenha1: ImageView
    private lateinit var imagemMostarSenha2 : ImageView

    private var senhaVisivel1:Boolean = false
    private var senhaVisivel2:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#2D3176")

        textoSenha1 = findViewById(R.id.senhaCadastro)
        imagemMostrarSenha1 = findViewById(R.id.olho_aberto1)
        textoSenha2 = findViewById(R.id.senhaCadastro2)
        imagemMostarSenha2 = findViewById(R.id.olho_aberto2)

        imagemMostrarSenha1.setOnClickListener {
            senhaVisivel1 = !senhaVisivel1
            alternarVisibilidadeSenha(textoSenha1,imagemMostrarSenha1,senhaVisivel1)
        }

        imagemMostarSenha2.setOnClickListener {
            senhaVisivel2 = !senhaVisivel2
            alternarVisibilidadeSenha(textoSenha2 , imagemMostarSenha2,senhaVisivel2)
        }


        inserirEmail = findViewById(R.id.emailCadastro)
        inserirSenha = findViewById(R.id.senhaCadastro)
        inserirNome = findViewById(R.id.nomeUsario)
        botaoCadastrar = findViewById(R.id.cadastrar)

        botaoCadastrar.setOnClickListener {
            val email = inserirEmail.text.toString().trim()
            val senha = inserirSenha.text.toString().trim()
            val nome = inserirNome.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty() || nome.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            cadastrarUsuario(email, senha, nome)
        }
    }

    private fun cadastrarUsuario(email: String, senha: String, nome: String) {
        val file = File(filesDir, "usuarios.txt")

        try {
            if (file.exists()) {
                if (file.readLines().any { it.split(",").getOrNull(0) == email }) {
                    Toast.makeText(
                        this,
                        "Usuário com este email já cadastrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }

            file.appendText("$email,$senha,$nome\n")
            Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show()

            inserirEmail.text.clear()
            inserirSenha.text.clear()
            inserirNome.text.clear()

        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun alternarVisibilidadeSenha(textoSenha :EditText, imagemMostrarSenha: ImageView, senhaVisivel:Boolean) {
        if (senhaVisivel) {
            // Mostrar senha
            textoSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imagemMostrarSenha.setImageResource(R.drawable.olho_aberto)
        } else {
            // Esconder senha
            textoSenha.transformationMethod = PasswordTransformationMethod.getInstance()
            imagemMostrarSenha.setImageResource(R.drawable.olho_fechado)
        }

        textoSenha.setSelection(textoSenha.text.length)
    }
}
