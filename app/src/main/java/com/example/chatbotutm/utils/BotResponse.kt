package com.example.chatbotutm.utils

import android.widget.Toast
import com.example.chatbotutm.APIService
import com.example.chatbotutm.Materia
import com.example.chatbotutm.utils.Constants.OPEN_GOOGLE
import com.example.chatbotutm.utils.Constants.OPEN_SEARCH
import com.example.login.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object BotResponse {
    fun basicResponses(_message: String):String{//devuelve un mensaje dependiendo de la cadena que reciba
        val random = (0..2).random()
        val message = _message.toLowerCase()//mensaje insertado en el constructor en minusculas
        return when{
            message.contains("tengo derecho a extraordinario?")-> {
                val matricula = 2017020423
                var materias:List<Materia> = ArrayList()
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<Materia>> = apiService.listarMaterias(matricula)
                resultado.enqueue(object : Callback<List<Materia>> {
                    override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                        println( "Matricula incorrecta")
                    }
                    override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                        println(response.body()!!)
                        materias = response.body()!!

                    }
                })
                "I flipped a coin and it landed on $materias"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")//invoca al objeto y envia parametroa
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hello") -> {//se revisa el contenido del mensaje y se elije una respuesta
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Buongiorno!"
                    else -> "error" }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }
            else -> {//lo que no se puede responder
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}