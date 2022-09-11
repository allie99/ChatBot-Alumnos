package com.example.chatbotutm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.login.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreguntasIns : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas_ins)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Preguntas frecuentes"
        val este = this;
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<List<Pregunta>> = apiService.preguntasReinscripcion()
        resultado.enqueue(object : Callback<List<Pregunta>> {
            override fun onFailure(call: Call<List<Pregunta>>, t: Throwable) {
                Toast.makeText(this@PreguntasIns, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Pregunta>>,
                response: Response<List<Pregunta>>
            ) {
                val pregunta = Array(response.body()!!.size) { "" }
                for (i in response.body()!!.indices) {
                    pregunta[i] = response.body()!![i].pregunta
                }
                val lvpreguntasIns = findViewById<ListView>(R.id.lvpreguntasIns)
                val adaptador: ArrayAdapter<*> =
                    ArrayAdapter(este, android.R.layout.simple_list_item_1, pregunta)
                lvpreguntasIns.adapter = adaptador
                lvpreguntasIns.setOnItemClickListener() { parent, view, position, id ->
                    val idPregunta = response.body()!![position].idPregunta
                    val intent = Intent(este, RespuestaReinscripcion::class.java).apply {
                        putExtra("idPregunta", "" + idPregunta)
                    }
                    startActivity(intent)
                }
            }
        })
    }
}