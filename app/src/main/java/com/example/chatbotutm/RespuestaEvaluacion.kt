package com.example.chatbotutm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.login.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RespuestaEvaluacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuesta_evaluacion)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val message:Int = intent.getStringExtra("idPregunta").toString().toInt()
        val respuesta = findViewById<TextView>(R.id.resEvaluacion)
        val articulo = findViewById<TextView>(R.id.articuloEvaluacion)
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Pregunta> = apiService.respuestaEvaluacion(message)
        resultado.enqueue(object : Callback<Pregunta> {
            override fun onFailure(call: Call<Pregunta>, t: Throwable) {
                Toast.makeText(this@RespuestaEvaluacion, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Pregunta>, response: Response<Pregunta>) {
                println(response.body()!!)
                respuesta.text=response.body()!!.respuesta
                articulo.text=response.body()!!.articulo
                supportActionBar?.title = response.body()!!.pregunta
            }
        })
    }
}