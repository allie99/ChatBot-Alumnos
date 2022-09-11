package com.example.chatbotutm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Evaluacion (
    var idEF:Int,
    var matricula:Int,
    var idMateria:Int,
    var tipoEvaluacion:Int,
    var CalfOrdinario:Double,
    var CalfPromedio:Double,
    var asistencia:Int
    ): Parcelable