package com.example.chatbotutm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatbotutm.ui.ChatBot
import com.example.login.RestEngine
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IniciarSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
    }
    fun funExiste(view: View){
        val este=this
        val autent= Autenticar(2017020423,passwordEditText.text.toString())
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<alumnoAutenticado> = apiService.autenticar(autent)
            resultado.enqueue(object : Callback<alumnoAutenticado> {
                override fun onFailure(call: Call<alumnoAutenticado>, t: Throwable) {
                    Toast.makeText(this@IniciarSesion, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<alumnoAutenticado>, response: Response<alumnoAutenticado>) {
                    val intent = Intent(este,   ChatBot::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
    }
}