package com.example.dia11

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    private lateinit var textoSenha: EditText
    private lateinit var imagemMostrarSenha: ImageView

    private var senhaVisivel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textoSenha = findViewById(R.id.senhaSegundaTela)
        imagemMostrarSenha = findViewById(R.id.olho_aberto)

        imagemMostrarSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            alternarVisibilidadeSenha()
        }
    }
    private fun alternarVisibilidadeSenha() {
        if (senhaVisivel) {
            textoSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imagemMostrarSenha.setImageResource(R.drawable.olho_aberto)
        } else {
            textoSenha.transformationMethod = PasswordTransformationMethod.getInstance()
            imagemMostrarSenha.setImageResource(R.drawable.olho_fechado)
        }

        textoSenha.setSelection(textoSenha.text.length)
    }
}