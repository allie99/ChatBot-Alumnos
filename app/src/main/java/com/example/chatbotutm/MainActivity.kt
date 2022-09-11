package com.example.chatbotutm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.chatbotutm.ui.ChatBot

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun opcDos(view: View){
        val intent = Intent(this, PreguntasIns::class.java).apply{
        }
        startActivity(intent)
    }

    fun opcTres(view: View){
        val intent = Intent(this, PreguntasEvaluacion::class.java).apply{
        }
        startActivity(intent)
    }
    fun opcChat(view: View){
        val intent = Intent(this, ChatBot::class.java).apply{
        }
        startActivity(intent)
    }
}