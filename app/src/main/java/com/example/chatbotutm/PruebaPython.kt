package com.example.chatbotutm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class PruebaPython : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_python)
        if (!Python. isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py:Python = Python.getInstance()
        val pyobj:PyObject = py.getModule("ConsultasEvaluacion")
        val obj:PyObject = pyobj.callAttr("materias_cursadas","2017020266")
        println(obj)

    }

}