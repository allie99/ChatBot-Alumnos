package com.example.chatbotutm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("evaluacion")
    fun preguntasEvaluacion():Call<List<Pregunta>>//investigar
    @GET("evaluacion/{idPregunta}")
    fun respuestaEvaluacion(@Path("idPregunta") idPregunta: Int):Call<Pregunta>
    @GET("reinscripcion")
    fun preguntasReinscripcion():Call<List<Pregunta>>//investigar
    @GET("reinscripcion/{idPregunta}")
    fun respuestaReinscripcion(@Path("idPregunta") idPregunta: Int):Call<Pregunta>
    @POST("alumno/autenticar")
    fun autenticar(@Body aunteticar:Autenticar):Call<alumnoAutenticado>
    @GET("chat/semestre/{matricula}")
    fun obtener_idSemestre(@Path("matricula") matricula: Int):Call<Alumno>
    @GET("chat/{matricula}")
    fun listarMaterias(@Path("matricula") matricula: Int):Call<List<Materia>>
   @GET("chat/materiasr/{matricula}/{semestre}")
    fun materias_Recursadas(@Path("matricula") matricula: Int, @Path("semestre") semestre: Int):Call<List<Materia>>
    @GET("chat/matcursada/{matricula}/{idM}")
    fun  mat_cursada(@Path("matricula") matricula: Int, @Path("idM") idM: Int):Call<MateriaCursada>
    @GET("chat/asistenciamat/{matricula}/{parcial}/{materia}")
    fun  asistencia_x_materia(@Path("matricula") matricula: Int, @Path("parcial") parcial: Int, @Path("materia") materia: Int):Call<Parcial>
    @GET("chat/asistenciao/{matricula}/{materia}")
    fun  asistencia_Ordinario(@Path("matricula") matricula: Int, @Path("materia") materia: Int):Call<Evaluacion>
    @GET("chat/reporte/{matricula}/{parcial}")
    fun  reporte_entrega(@Path("matricula") matricula: Int, @Path("parcial") parcial: Int):Call<Parcial>
    @GET("chat/calfinal/{matricula}/{materia}")
    fun  calficacion_final(@Path("matricula") matricula: Int, @Path("materia") materia: Int):Call<Evaluacion>
    @GET("chat/extrau/{matricula}/{materia}")
    fun  calf_extra1(@Path("matricula") matricula: Int, @Path("materia") materia: Int):Call<Calificacion>
    @GET("chat/tiposem/{matricula}")
    fun tipo_semestre(@Path("matricula") matricula: Int):Call<Semestre>
    @GET("chat/recursaimp/{matricula}/{idM}")
    fun  recursa_estadoImpar(@Path("matricula") matricula: Int, @Path("idM") idM: Int):Call<Materia>
    @GET("chat/matcursadas/{matricula}")
    fun materias_cursadas(@Path("matricula") matricula: Int):Call<Resultado>
    @GET("chat/idmaterias/{matricula}")
    fun lista_idMaterias(@Path("matricula") matricula: Int):Call<List<Materia>>
    @GET("chat/matreprobadas/{matricula}")
    fun mat_reprobadas(@Path("matricula") matricula: Int):Call<List<Evaluacion>>
}
