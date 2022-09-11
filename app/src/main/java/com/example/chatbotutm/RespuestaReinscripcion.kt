package com.example.chatbotutm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.login.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RespuestaReinscripcion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuesta_reinscripcion)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Respuesta"
        val message:Int = intent.getStringExtra("idPregunta").toString().toInt()
        val respuesta = findViewById<TextView>(R.id.resInscripcion)
        val articulo = findViewById<TextView>(R.id.articuloReins)
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Pregunta> = apiService.respuestaReinscripcion(message)
        resultado.enqueue(object : Callback<Pregunta> {
            override fun onFailure(call: Call<Pregunta>, t: Throwable) {
                Toast.makeText(this@RespuestaReinscripcion, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Pregunta>, response: Response<Pregunta>) {
                println(response.body()!!)
                respuesta.text=response.body()!!.respuesta
                articulo.text=response.body()!!.articulo
            }
        })
    }
}