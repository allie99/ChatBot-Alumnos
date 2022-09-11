package com.example.chatbotutm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBot : AppCompatActivity() {
    var valor1= 0;
    object Global {
        var i = 0
        var res = 0.0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot2)
        sin_derExtras(2017020423)

    }

    fun listarMaterias(){//********
        val matricula = 2017020423
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<List<Materia>> = apiService.listarMaterias(matricula)
        resultado.enqueue(object : Callback<List<Materia>> {
            override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                println(response.body()!!)
            }
        })
    }

    fun materias_Recursadas(matricula: Int){
        var idSemestre=0
        var materias:List<Materia>
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Alumno> = apiService.obtener_idSemestre(matricula)
        resultado.enqueue(object : Callback<Alumno> {
            override fun onFailure(call: Call<Alumno>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Alumno>, response: Response<Alumno>) {
                idSemestre = response.body()!!.idSemetre
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<Materia>> = apiService.materias_Recursadas(matricula,idSemestre)
                resultado.enqueue(object : Callback<List<Materia>> {
                    override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                        materias = response.body()!!
                        println("Materias recursadas:"+materias)
                    }
                })
            }
        })
    }


    fun mat_cursada(matricula: Int, materia: Int){
        var materiaCursada = MateriaCursada(0,0,0,0,0,0,0)
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<MateriaCursada> = apiService.mat_cursada(matricula,materia)
        resultado.enqueue(object : Callback<MateriaCursada> {
            override fun onFailure(call: Call<MateriaCursada>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MateriaCursada>, response: Response<MateriaCursada>) {
                materiaCursada = response.body()!!
                if(materiaCursada!=null)
                    println("1")
                else
                    println("0")
            }
        })
    }

    fun asistencia_regla(matricula: Int, parcial:Int, materia:Int){
        var asistencia=0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,parcial,materia)
        resultado.enqueue(object : Callback<Parcial> {
            override fun onFailure(call:  Call<Parcial>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                asistencia = response.body()!!.asistencia
            }
        })
    }

    fun der_evalucacion(matricula: Int, materia: Int, parcial: Int){
        var asistencia=0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,parcial,materia)
        resultado.enqueue(object : Callback<Parcial> {
            override fun onFailure(call:  Call<Parcial>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                asistencia = response.body()!!.asistencia
                var reporteAnt = 0
                if (parcial>1){
                    var parcialAnt = parcial-1
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Parcial> = apiService.reporte_entrega(matricula,parcialAnt)
                    resultado.enqueue(object : Callback<Parcial> {
                        override fun onFailure(call: Call<Parcial>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                            reporteAnt = response.body()!!.estadoR
                        }
                    })
                }
                else{
                    reporteAnt = 1 //Cuando se trata del primer reporte, se considera que el anterior se entrego
                }
                var reporteAct = 0
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<Parcial> = apiService.reporte_entrega(matricula,parcial)
                resultado.enqueue(object : Callback<Parcial> {
                    override fun onFailure(call: Call<Parcial>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                        reporteAct = response.body()!!.estadoR
                        println("Asistencia: "+asistencia)
                        println("Reporte Anterior"+reporteAnt)
                        println("Reporte Actual"+reporteAct)
                    }
                })
            }
        })
    }

    fun der_ExamOrdi(matricula: Int, materia: Int){
        var asistencia = 0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Evaluacion> = apiService.asistencia_Ordinario(matricula,materia)
        resultado.enqueue(object : Callback<Evaluacion> {
            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                asistencia = response.body()!!.asistencia
            }
        })
    }

    fun derecho_Extraordinario(matricula: Int, materia: Int){
        var calfinal = 0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula,materia)
        resultado.enqueue(object : Callback<Evaluacion> {
            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                calfinal = response.body()!!.CalfPromedio
                var Ap1=0
                var Ap2=0
                var Ap3=0
                var asistenciaF=0
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,1,materia)
                resultado.enqueue(object : Callback<Parcial> {
                    override fun onFailure(call: Call<Parcial>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                        Ap1 = response.body()!!.asistencia
                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                        val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,2,materia)
                        resultado.enqueue(object : Callback<Parcial> {
                            override fun onFailure(call: Call<Parcial>, t: Throwable) {
                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                                Ap2 = response.body()!!.asistencia
                                println(Ap2)
                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,3,materia)
                                resultado.enqueue(object : Callback<Parcial> {
                                    override fun onFailure(call: Call<Parcial>, t: Throwable) {
                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                                        Ap3 = response.body()!!.asistencia
                                        println(Ap3)
                                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                        val resultado: Call<Evaluacion> = apiService.asistencia_Ordinario(matricula,materia)
                                        resultado.enqueue(object : Callback<Evaluacion> {
                                            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                            }
                                            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                                                asistenciaF = response.body()!!.asistencia
                                                val asistenciaP = (Ap1 + Ap2 + Ap3 + asistenciaF) / 4
                                                println("Asistencia promedio: "+asistenciaP)
                                                println("Calficacion final: "+calfinal)
                                            }
                                        })

                                    }
                                })
                            }
                        })
                    }
                })

            }
        })
    }

    fun solicitar_extra(matricula: Int, materia: Int){
        var estadoEx1=0
        var estadoEx2=0
        var materiaCursada = MateriaCursada(0,0,0,0,0,0,0)
        var cursa = 0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<MateriaCursada> = apiService.mat_cursada(matricula,materia)
        resultado.enqueue(object : Callback<MateriaCursada> {
            override fun onFailure(call: Call<MateriaCursada>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<MateriaCursada>, response: Response<MateriaCursada>) {
                materiaCursada = response.body()!!
                if(materiaCursada!=null)
                    cursa = 1
                else
                    cursa = 0
                if(cursa==1){
                    var califEx1=0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                    resultado.enqueue(object : Callback<Calificacion> {
                        override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                            califEx1 = response.body()!!.califEx1.toDouble()
                            if(califEx1!=null){
                                estadoEx1=1
                                var califEx2=0.0
                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                                resultado.enqueue(object : Callback<Calificacion> {
                                    override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                        califEx2 = response.body()!!.califEx2.toDouble()
                                        if(califEx2!=null)
                                            estadoEx2 = 1
                                        else
                                            estadoEx2 = 0
                                        println("*****")
                                    }
                                })

                            }
                        }
                    })
                }
                else{
                    var calfinal = 0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula,materia)
                    resultado.enqueue(object : Callback<Evaluacion> {
                        override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                            calfinal = response.body()!!.CalfPromedio
                            if(calfinal<6)
                                println("No puede solicitar estra")//nosotros decidimos?
                        }
                    })
                }
            }
        })
    }

    fun sin_derExtras(matricula: Int){
        var materias=0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Resultado> = apiService.materias_cursadas(matricula)
        resultado.enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                println("Materias cursadas: "+response.body()!!)
                materias = response.body()!!.resultado
                var numMaterias=0
                var idlista : List<IdMaterias> = ArrayList()
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<Evaluacion>> = apiService.mat_reprobadas(matricula)
                resultado.enqueue(object : Callback<List<Evaluacion>> {
                    override fun onFailure(call: Call<List<Evaluacion>>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<List<Evaluacion>>, response: Response<List<Evaluacion>>) {
                        numMaterias = response.body()!!.size
                        println("Numero de materias resprobadas:"+numMaterias)
                    }
                })

            }
        })
    }

    fun mat_derEspecia(matricula: Int, materia: Int){
        var estadoR = 1
        var idSemestre=0
        var materias:List<Materia>
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Alumno> = apiService.obtener_idSemestre(matricula)
        resultado.enqueue(object : Callback<Alumno> {
            override fun onFailure(call: Call<Alumno>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Alumno>, response: Response<Alumno>) {
                idSemestre = response.body()!!.idSemetre
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<Materia>> = apiService.materias_Recursadas(matricula,idSemestre)
                resultado.enqueue(object : Callback<List<Materia>> {
                    override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                        materias = response.body()!!
                        println("Materias recursadas:"+materias)
                        for(idMat in materias){
                            if(idMat.idMateria==materia){
                                estadoR = 1
                                var califEx1=0.0
                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                                resultado.enqueue(object : Callback<Calificacion> {
                                    override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                        califEx1 = response.body()!!.califEx1.toDouble()
                                        println("***********"+califEx1)
                                        var califEx2=0.0
                                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                                        resultado.enqueue(object : Callback<Calificacion> {
                                            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                            }
                                            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                califEx2 = response.body()!!.califEx2.toDouble()
                                                println("Estado: "+estadoR)
                                                println("EXtra 1: "+califEx1)
                                                println("EXtra 2: "+califEx2)
                                            }
                                        })
                                    }
                                })

                            }
                        }
                    }
                })
            }
        })
    }

    fun freprobar_especial(matricula: Int, materia: Int){
        var especial=0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
        resultado.enqueue(object : Callback<Calificacion> {
            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                especial = response.body()!!.especial.toDouble()
            }
        })
    }

    fun derecho_verano(matricula: Int, materia: Int){
        var tipoSemestre=""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
                println("Tipo de semestre:"+tipoSemestre)
                var estado = 0
                if (tipoSemestre.equals("A")) {
                    var califEx2=0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                    resultado.enqueue(object : Callback<Calificacion> {
                        override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                            califEx2 = response.body()!!.califEx2.toDouble()
                            if (califEx2 < 6.0)
                                estado = 1
                            println("EStado:"+estado)
                        }
                    })
                }
            }
        })
    }

    fun cursar_verano(matricula: Int, materia: Int, pago:Int){
        var estadoV=0
        var tipoSemestre=""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
                println("Tipo de semestre:"+tipoSemestre)
                var estado = 0
                if (tipoSemestre.equals("A")) {
                    var califEx2=0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,materia)
                    resultado.enqueue(object : Callback<Calificacion> {
                        override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                            califEx2 = response.body()!!.califEx2.toDouble()
                            if (califEx2 < 6.0)
                                estado = 1
                            println("EStado:"+estado)

                        }
                    })
                }
            }
        })
    }
/*
    fun reinscripcion(matricula: Int){
        var tipoSemestre=""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
                var  count = 0
                var idlista : List<IdMaterias>
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<IdMaterias>> = apiService.lista_idMaterias(matricula)
                resultado.enqueue(object : Callback<List<IdMaterias>> {
                    override fun onFailure(call: Call<List<IdMaterias>>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<List<IdMaterias>>, response: Response<List<IdMaterias>>) {
                        idlista = response.body()!!
                        println("Lista id Materias:"+idlista)
                        for( mat in idlista){
                            var calfinal = 0.0
                            val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                            val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula,mat.idMateria)
                            resultado.enqueue(object : Callback<Evaluacion> {
                                override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                                    Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                }
                                override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                                    calfinal = response.body()!!.CalfPromedio
                                    if( calfinal >= 6.0)
                                        count += 1
                                    else {
                                        var calextra1 = 0.0
                                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,mat.idMateria)
                                        resultado.enqueue(object : Callback<Calificacion> {
                                            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                            }
                                            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                calextra1 = response.body()!!.califEx1.toDouble()
                                                if( calextra1 >= 6.0)
                                                    count += 1
                                                else {
                                                    var calextra2 = 0.0
                                                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula, mat.idMateria)
                                                    resultado.enqueue(object : Callback<Calificacion> {
                                                        override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                        }
                                                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                            calextra2 = response.body()!!.califEx2.toDouble()
                                                            if(  calextra2 >= 6.0)
                                                                count += 1
                                                            else {
                                                                var especial=0.0
                                                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                                                val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,mat.idMateria)
                                                                resultado.enqueue(object : Callback<Calificacion> {
                                                                    override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                                    }
                                                                    override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                                        especial = response.body()!!.especial.toDouble()
                                                                        if (especial >= 6.0)
                                                                            count += 1
                                                                        println("Materias reprobadas:"+count)
                                                                    }
                                                                })
                                                            }
                                                        }
                                                    })
                                                }
                                            }
                                        })
                                    }
                                }
                            })
                        }
                    }
                })
            }
        })
    }
*/
/*    fun baja_temp(matricula: Int) {
        var tipoSemestre = ""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
                var count = 0
                var idlista: List<IdMaterias>
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<List<IdMaterias>> = apiService.lista_idMaterias(matricula)
                resultado.enqueue(object : Callback<List<IdMaterias>> {
                    override fun onFailure(call: Call<List<IdMaterias>>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<List<IdMaterias>>, response: Response<List<IdMaterias>>) {
                        idlista = response.body()!!
                        println("Lista id Materias:" + idlista)
                        for (mat in idlista) {
                            var calfinal = 0.0
                            val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                            val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula, mat.idMateria)
                            resultado.enqueue(object : Callback<Evaluacion> {
                                override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                                    Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                }
                                override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                                    calfinal = response.body()!!.CalfPromedio
                                    if (calfinal >= 6.0)
                                        count += 1
                                    else {
                                        var calextra1 = 0.0
                                        val apiService: APIService =
                                            RestEngine.getRestEngine().create(APIService::class.java)
                                        val resultado: Call<Calificacion> =
                                            apiService.calf_extra1(matricula, mat.idMateria)
                                        resultado.enqueue(object : Callback<Calificacion> {
                                            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                            }
                                            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                calextra1 = response.body()!!.califEx1.toDouble()
                                                if (calextra1 >= 6.0)
                                                    count += 1
                                                else {
                                                    var calextra2 = 0.0
                                                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula, mat.idMateria)
                                                    resultado.enqueue(object : Callback<Calificacion> {
                                                        override fun onFailure(call: Call<Calificacion>, t: Throwable) { Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                        }
                                                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                            calextra2 = response.body()!!.califEx1.toDouble()
                                                            if (calextra2 >= 6.0)
                                                                count += 1
                                                            else {
                                                                var especial = 0.0
                                                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                                                val resultado: Call<Calificacion> = apiService.calf_extra1(matricula, mat.idMateria)
                                                                resultado.enqueue(object : Callback<Calificacion> {
                                                                    override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                                    }
                                                                    override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                                        especial = response.body()!!.especial.toDouble()
                                                                        if (especial >= 6.0)
                                                                            count += 1
                                                                        println("Materias aprobadas:" + count)
                                                                    }
                                                                })
                                                            }
                                                        }
                                                    })
                                                }
                                            }
                                        })
                                    }
                                }
                            })
                        }
                    }
                })
            }
        })
    }
*/
    fun asistencia_Ordinario(matricula: Int, materia: Int): Int {
        var asistencia = 0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Evaluacion> = apiService.asistencia_Ordinario(matricula,materia)
        resultado.enqueue(object : Callback<Evaluacion> {
            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
               asistencia = response.body()!!.asistencia
                Global.i= Global.i + 1
            }
        })
        return asistencia
    }

    fun asistencia_Promedio(matricula: Int, materia: Int){
        var Ap1=0
        var Ap2=0
        var Ap3=0
        var asistenciaF=0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,1,materia)
        resultado.enqueue(object : Callback<Parcial> {
            override fun onFailure(call: Call<Parcial>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                Ap1 = response.body()!!.asistencia
                println(Ap1)
                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,2,materia)
                resultado.enqueue(object : Callback<Parcial> {
                    override fun onFailure(call: Call<Parcial>, t: Throwable) {
                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                        Ap2 = response.body()!!.asistencia
                        println(Ap2)
                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                        val resultado: Call<Parcial> = apiService.asistencia_x_materia(matricula,3,materia)
                        resultado.enqueue(object : Callback<Parcial> {
                            override fun onFailure(call: Call<Parcial>, t: Throwable) {
                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
                                Ap3 = response.body()!!.asistencia
                                println(Ap3)
                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                val resultado: Call<Evaluacion> = apiService.asistencia_Ordinario(matricula,materia)
                                resultado.enqueue(object : Callback<Evaluacion> {
                                    override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                                        asistenciaF = response.body()!!.asistencia
                                        val asistenciaP = (Ap1 + Ap2 + Ap3 + asistenciaF) / 4
                                        println("Asistencia promedio:"+asistenciaP)
                                    }
                                })

                            }
                        })
                    }
                })
            }
        })
    }

    fun reporte_entrega(){
        val matricula= 2017020423
        val numParcial=3
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Parcial> = apiService.reporte_entrega(matricula,numParcial)
        resultado.enqueue(object : Callback<Parcial> {
            override fun onFailure(call: Call<Parcial>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Parcial>, response: Response<Parcial>) {
            }
        })
    }

    fun calificacion_final(matricula: Int, materia: Int):Double{
        var calfinal = 0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula,materia)
        resultado.enqueue(object : Callback<Evaluacion> {
            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
               calfinal = response.body()!!.CalfPromedio
            }
        })
        return calfinal
    }

    fun calf_extra1(matricula: Int, idMateria: Int): Double {
        var califEx1=0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,idMateria)
        resultado.enqueue(object : Callback<Calificacion> {
            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                califEx1 = response.body()!!.califEx1.toDouble()
            }
        })
        return  califEx1
    }

    fun calf_extra2(matricula: Int, idMR: Int): Double {
        var califEx2=0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,idMR)
        resultado.enqueue(object : Callback<Calificacion> {
            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                califEx2 = response.body()!!.califEx2.toDouble()
            }
        })
        return califEx2
    }

    fun calificacionE(matricula: Int, idMateria: Int): Double {
        var especial=0.0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,idMateria)
        resultado.enqueue(object : Callback<Calificacion> {
            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                 especial = response.body()!!.especial.toDouble()
            }
        })
        return especial
    }

    fun tipo_semestre(matricula: Int): String {
        var tipoSemestre=""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
            }
        })
        return tipoSemestre
    }

    fun tipo_evaluacion(){
        val matricula= 2017020423
        val idMateria=2
        var tipoEvaluacion=2
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Evaluacion> = apiService.asistencia_Ordinario(matricula,idMateria)
        resultado.enqueue(object : Callback<Evaluacion> {
            override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                tipoEvaluacion = response.body()!!.tipoEvaluacion
                println(tipoEvaluacion)
            }
        })
    }

    fun recursa_estadoImpar(): Int {
        val matricula= 2017020423
        val idMateria=2
        var recursada=Materia(0,"")
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Materia> = apiService.recursa_estadoImpar(matricula,idMateria)
        resultado.enqueue(object : Callback<Materia> {
            override fun onFailure(call: Call<Materia>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Materia>, response: Response<Materia>) {
                println(response.body()!!)
                recursada=response.body()!!
            }
        })
        return if(recursada!=null)
            1
        else
            -1
    }

    fun materias_cursadas(){
        val matricula= 2017020423
        var materias=0
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Resultado> = apiService.materias_cursadas(matricula)
        resultado.enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                println(response.body()!!)
            }
        })
    }
/*
    fun lista_idMaterias(matricula:Int): List<IdMaterias> {
        var idlista : List<IdMaterias> = ArrayList()
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<List<Materia>> = apiService.lista_idMaterias(matricula)
        resultado.enqueue(object : Callback<List<Materia>> {
            override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                idlista = response.body()!!
                println(idlista)
            }
        })
        return idlista
    }
*/
    fun num_Mreporobadas(matricula: Int) {
        var numMaterias=0
        var idlista : List<IdMaterias> = ArrayList()
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<List<Evaluacion>> = apiService.mat_reprobadas(matricula)
        resultado.enqueue(object : Callback<List<Evaluacion>> {
            override fun onFailure(call: Call<List<Evaluacion>>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Evaluacion>>, response: Response<List<Evaluacion>>) {
                numMaterias = response.body()!!.size
                println("Numero de materias resprobadas:"+numMaterias)
            }
        })
    }

    fun noAprobada_imparA(matricula: Int, idMR:Int){
        var tipoSemestre=""
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<Semestre> = apiService.tipo_semestre(matricula)
        resultado.enqueue(object : Callback<Semestre> {
            override fun onFailure(call: Call<Semestre>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Semestre>, response: Response<Semestre>) {
                tipoSemestre = response.body()!!.tipoS
                println("Tipo de semestre:"+tipoSemestre)
                var estado = 0
                if (tipoSemestre.equals("A")) {
                    var califEx2=0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,idMR)
                    resultado.enqueue(object : Callback<Calificacion> {
                        override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                            califEx2 = response.body()!!.califEx2.toDouble()
                            if (califEx2 < 6.0)
                                estado = 1
                            println("EStado:"+estado)
                        }
                    })
                }
            }
        })
    }
  /*  fun solicitarVerano(){
        val semestre="A"
        if(semestre=="A")
    }*/
}
/*
    fun num_matAprobadas(matricula:Int){
        var  count = 0
        var idlista : List<IdMaterias>
        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
        val resultado: Call<List<IdMaterias>> = apiService.lista_idMaterias(matricula)
        resultado.enqueue(object : Callback<List<IdMaterias>> {
            override fun onFailure(call: Call<List<IdMaterias>>, t: Throwable) {
                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<IdMaterias>>, response: Response<List<IdMaterias>>) {
                idlista = response.body()!!
                println("Lista id Materias:"+idlista)
                for( mat in idlista){
                    var calfinal = 0.0
                    val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                    val resultado: Call<Evaluacion> = apiService.calficacion_final(matricula,mat.idMateria)
                    resultado.enqueue(object : Callback<Evaluacion> {
                        override fun onFailure(call: Call<Evaluacion>, t: Throwable) {
                            Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<Evaluacion>, response: Response<Evaluacion>) {
                            calfinal = response.body()!!.CalfPromedio
                            if( calfinal >= 6.0)
                                count += 1
                            else {
                                var calextra1 = 0.0
                                val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,mat.idMateria)
                                resultado.enqueue(object : Callback<Calificacion> {
                                    override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                        Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                        calextra1 = response.body()!!.califEx1.toDouble()
                                        if( calextra1 >= 6.0)
                                            count += 1
                                        else {
                                            var calextra2 = 0.0
                                            val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                            val resultado: Call<Calificacion> = apiService.calf_extra1(matricula, mat.idMateria)
                                            resultado.enqueue(object : Callback<Calificacion> {
                                                override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                    Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                }
                                                override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                    calextra2 = response.body()!!.califEx1.toDouble()
                                                    if(  calextra2 >= 6.0)
                                                        count += 1
                                                    else {
                                                        var especial=0.0
                                                        val apiService: APIService = RestEngine.getRestEngine().create(APIService::class.java)
                                                        val resultado: Call<Calificacion> = apiService.calf_extra1(matricula,mat.idMateria)
                                                        resultado.enqueue(object : Callback<Calificacion> {
                                                            override fun onFailure(call: Call<Calificacion>, t: Throwable) {
                                                                Toast.makeText(this@ChatBot, "Matricula incorrecta", Toast.LENGTH_SHORT).show()
                                                            }
                                                            override fun onResponse(call: Call<Calificacion>, response: Response<Calificacion>) {
                                                                especial = response.body()!!.especial.toDouble()
                                                                if (especial >= 6.0)
                                                                    count += 1
                                                                println("Materias aprobadas:"+count)
                                                            }
                                                        })
                                                    }
                                                }
                                            })
                                        }
                                    }
                                })
                            }
                        }
                    })
                }
            }
        })
    }
}*/