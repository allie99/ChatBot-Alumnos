package com.example.chatbotutm.ui

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotutm.R
import com.example.chatbotutm.data.Message
import com.example.chatbotutm.utils.Constants.RECEIVE_ID
import com.example.chatbotutm.utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

                //Remove message on the item clicked
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MessageViewHolder {//vista del mensaje
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) { //logica que decidira donde mostrar el mensaje, si a la izq. o a la der.
        val currentMessage = messagesList[position]

        when (currentMessage.id) {
            SEND_ID -> { //remitente
                holder.itemView.tv_message.apply {//se establece el texto
                    holder.itemView.tvv_message.text = currentMessage.message
                    visibility = View.VISIBLE //para que sea visible el texto

                }
                holder.itemView.tv_bot_message.visibility = View.GONE//asegura que el cuadro de mensaje sea el unico visivle
                holder.itemView.respuestaMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                if(currentMessage.equals("¿De cual materia?")){
                    holder.itemView.respuestaMessage.apply {
                        holder.itemView.respuesta.text = currentMessage.message
                        val adaptador: ArrayAdapter<*> = ArrayAdapter(currentMessage.contexto, android.R.layout.simple_list_item_1,currentMessage.opciones)
                        holder.itemView.opciones.adapter = adaptador
                        visibility = View.VISIBLE
                    }
                    holder.itemView.tv_message.visibility = View.GONE//el mensaje desaparece si el bot decide enviar un mensaje, de modo que solo recibamos un mensaje a la vez
                    holder.itemView.tv_bot_message.visibility = View.GONE
                }
                else{
                    holder.itemView.tv_bot_message.apply {
                        holder.itemView.tvv_bot_message.text = currentMessage.message
                        visibility = View.VISIBLE
                    }
                    holder.itemView.tv_message.visibility = View.GONE//el mensaje desaparece si el bot decide enviar un mensaje, de modo que solo recibamos un mensaje a la vez
                }
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size) //notifica que se agrego un nuevo elemento y asi mostrar una animacion en la parte inferior cada vez que se agregue un nuevo mensaje
    }

}