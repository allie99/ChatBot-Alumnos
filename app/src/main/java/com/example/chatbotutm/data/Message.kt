package com.example.chatbotutm.data

import android.content.Context

data class Message (val message:String, val id:String, val time:String, val contexto:Context, val opciones:Array<String>){
}