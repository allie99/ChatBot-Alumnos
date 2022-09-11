package com.example.chatbotutm.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbotutm.APIService
import com.example.chatbotutm.Materia
import com.example.chatbotutm.R
import com.example.chatbotutm.Resultado
import com.example.chatbotutm.data.Message
import com.example.chatbotutm.utils.Constants.OPEN_GOOGLE
import com.example.chatbotutm.utils.Constants.OPEN_SEARCH
import com.example.chatbotutm.utils.Constants.RECEIVE_ID
import com.example.chatbotutm.utils.Constants.SEND_ID
import com.example.chatbotutm.utils.SolveMath
import com.example.chatbotutm.utils.Time
import com.example.login.RestEngine
import kotlinx.android.synthetic.main.activity_chat_bot.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatBot : AppCompatActivity() {

    private val TAG = "MainActivity"
    var este=this
    var opciones= emptyArray<String>()
    //You can ignore this messageList if you're coming from the tutorial,
    // it was used only for my personal debugging
    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        recyclerView()

        clickEvents() //manejador de eventos

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?") //mensaje predeterminado
    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {//cada vez que se haga click en en el edittext, este se posicionara en la parte inferior de la pantalla
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {//configura el adaptador para el recycler de los mensajes
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {//Para que cuando el usuario navegue fuera la aplicacion y estes regrese, se eencuentre con la parte inferior , es decir en el chat deberan verse los ultimos mensajes
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)//para que los mensajes no se desplacen
            }
        }
    }

    private fun sendMessage() {//funcion para enviar mensajes
        val message = et_message.text.toString() //se obtine el mensaje del editext
        val timeStamp = Time.timeStamp()
        if (message.isNotEmpty()) {//si el mensaje no esta vacio
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp, este, opciones))
            et_message.setText("")//inicializa para despues continuar escribiendo otra oracion

            adapter.insertMessage(Message(message, SEND_ID, timeStamp, este, opciones))//se envia el objeto mensaje
            rv_messages.scrollToPosition(adapter.itemCount - 1)//se desplaza a la posicion del mensaje

            botResponse(message) //toma el mensaje que se decide enviar y lo coloca en el objeto que se creo anteriormente
        }
    }

    private fun botResponse(_message: String) {//funcion que recibe mensajes reviza con que responder
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000) //Fake response delay
            withContext(Dispatchers.Main) {//cambia a los subprocesos de interfaz de usuario
                var respuesta = "" //Gets the response
                val message = _message.toLowerCase()//mensaje insertado en el constructor en minusculas
                 when{
                     message.contains("¿tengo derecho a evaluación?")-> {
                         val matricula= 2017020423
                         var materias=0
                         val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                         val resultado: Call<List<Materia>> = apiService.lista_idMaterias(matricula)
                         resultado.enqueue(object : Callback<List<Materia>>{
                             override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                                 Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                             }
                             override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                                 println(response.body()!!)
                                 respuesta = "¿De cual materia?"//revisa que respuesta le corresponde al mensaje enviado e inserta el mensaje de respuesta
                                 var opciones = Array(response.body()!!.size){""}
                                 for (i in response.body()!!.indices) {
                                     opciones[i] = response.body()!![i].nombre
                                 }
                               /*  val materias = findViewById<ListView>(R.id.opciones)
                                 val adaptador: ArrayAdapter<*> = ArrayAdapter(este, android.R.layout.simple_list_item_1,opciones)
                                 materias.adapter = adaptador*/
                                 messagesList.add(Message(respuesta, RECEIVE_ID, timeStamp, este, opciones))//Adds it to our local list
                                 adapter.insertMessage(Message(respuesta, RECEIVE_ID, timeStamp, este, opciones))//Inserts our message into the adapter
                                 rv_messages.scrollToPosition(adapter.itemCount - 1)//Scrolls us to the position of the latest message

                             }
                         })
                     }

                 }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {//corrutina
            delay(1000)
            withContext(Dispatchers.Main) { //cambia al hilo principal y asi actualizar la interfaz del usuario
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp, este, opciones))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp, este, opciones))//toma rl mensaje enviado

                rv_messages.scrollToPosition(adapter.itemCount - 1)//actualiza la interfaz con el ultimo mensaje, accede a la ultima posicion
            }
        }
    }
}