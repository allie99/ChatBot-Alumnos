package com.example.chatbotutm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Materia (
    var idMateria: Int,
    var nombre: String
    ): Parcelable