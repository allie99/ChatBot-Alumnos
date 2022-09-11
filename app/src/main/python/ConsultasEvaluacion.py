# -*- coding: utf-8 -*-
"""
Created on Fri Aug 12 02:52:50 2022

@author: Alejandra
"""
import mysql.connector
import pytholog
conexion1 = mysql.connector.connect(host="localhost", port=3306,  user="root", passwd="x",  database="info")
data = conexion1.cursor(buffered=True)
data.execute("show tables")
for tabla in data:
    print(tabla)
    
def matriculaExiste(matricula):
    
    data.execute("select matricula from alumno where matricula = '%s'" %(matricula))
    results = data.fetchone() #list la respuesta de consulta
    return results[0]

def listar_materias(matricula):    
    #print(matricula)
    data.execute("select M.idMateria, M.nombre from materias M, carnet C, alumno A, calificacionesmat CM WHERE C.periodo=A.periodo and ((C.idCM1=CM.idCM and CM.idMateria= M.idMateria )or (C.idCM2=CM.idCM and CM.idMateria= M.idMateria)  or  (C.idCM3=CM.idCM and CM.idMateria= M.idMateria) or  (C.idCM4=CM.idCM and CM.idMateria= M.idMateria) or (C.idCM5=CM.idCM and CM.idMateria= M.idMateria)) and C.matricula=A.matricula and A.matricula= '%s'" %(matricula) )
    results = data.fetchall() #list la respuesta de consulta
    return results

def obtener_idSemestre(matricula):
    data.execute("select idSemetre from alumno  where matricula='%s'"%(matricula))
    results=data.fetchone()
    return results[0]

#semestre =obtener_idSemestre('2017020266')
#print(semestre)

def materias_Recursadas(matricula):
    idAnterior=obtener_idSemestre(matricula)
    data.execute("select M.idMateria from  materias M, carnet C, alumno A, calificacionesmat CM WHERE C.periodo=A.periodo and C.idSemestre!='%s' and C.id=CM.idCarnet and ((C.idCM1=CM.idCM and CM.idMateria= M.idMateria )or (C.idCM2=CM.idCM and CM.idMateria= M.idMateria)  or  (C.idCM3=CM.idCM and CM.idMateria= M.idMateria) or  (C.idCM4=CM.idCM and CM.idMateria= M.idMateria) or (C.idCM5=CM.idCM and CM.idMateria= M.idMateria)) and C.matricula=A.matricula and A.matricula= '%s'"%(idAnterior,matricula))    
    results=data.fetchall()
    return results[0]

#semestre =materias_Recursadas('2017020266')
#print(semestre)

def mat_cursada(matricula,idM):
    data.execute("select CM.idMateria from  alumno A, carnet C, semestre S ,calificacionesmat CM where A.matricula= C.matricula and C.matricula ='%s' and A.periodo= C.periodo  and (CM.idCM =C.idCM1 or CM.idCM =C.idCM2 or CM.idCM =C.idCM3 or CM.idCM =C.idCM4 or CM.idCM =C.idCM5)  and CM.idCM='%s'" %(matricula,idM))
    recursada= data.fetchone()
    if (recursada[0]!=None):
        return 1
    else:
        return 0
#mat=mat_cursada('2017020266','1')
#print(mat)  
    

def asistencia_x_materia(matricula,parcial, materia):
    matricula=matriculaExiste(matricula)
    #matricula=matricula[0]
   #results=listar_materias(matricula)
    #for mat in results:
    #    print(mat[0],mat[1])
    #materia=input("Ingresa el id de la meteria desada...")  #ID materia deseada"""
        #Ingresa el numero que corresponde a la materia que cursas
        #¿De que parcial se habla?      
        #¿Tengo derecho a evaluacion?
    """print("Parciales de un Semestrel")
    print("1.-Primer Parcial")
    print("2.-Segundo Parcial")
    print("3.-Tercer Parcial")
    parcial= input("Ingresa el parcial a consultar(Del 1 al 3 el parcial deseado)..")"""
    if parcial== '1' or parcial== '2' or parcial== '3':
            data.execute("select P.asistencia from carnet C, alumno A, parcial P WHERE  P.idMateria='%s' and C.id=P.idCarnet and  P.numParcial='%s' and A.matricula = C.matricula and A.matricula='%s'" %(materia,parcial,matricula))
            asistencia= data.fetchone() #list la respuesta de consulta
            return asistencia[0]

def asistencia_Ordinario(matricula,materia):
    #results=listar_materias(matricula)
    #for mat in results:
    #    print(mat[0],mat[1])
    #materia=input("Ingresa el id de la meteria desada...")"""
    data.execute("select EF.asistencia from evaluacionf EF WHERE EF.matricula ='%s' and EF.idMateria='%s'"%(matricula, materia))
    asistencia= data.fetchone()
    return asistencia[0]

#asiste=asistencia_Ordinario('2017020266')
#print(asiste)


#asistencia_x_materia()
def asistencia_Promedio(matricula,materia):
    """results=listar_materias(matricula)
    for mat in results:
        print(mat[0],mat[1])
    materia=input("Ingresa el id de la meteria desada...") """ #ID materia deseada
    Ap1= asistencia_x_materia(matricula,'1',materia)
    Ap2= asistencia_x_materia(matricula,'2',materia)
    Ap3= asistencia_x_materia(matricula,'3',materia)
    asistenciaF= asistencia_Ordinario(matricula, materia)
    asistenciaP= int((Ap1+Ap2+Ap3+asistenciaF)/4)
    return asistenciaP
    
#asiste=asistencia_Promedio('2017020266')
#print(asiste)
#print(asistencia_x_materia('2017020266','3','1'))

#Reporte
def reporte_entrga(matricula, parcial):
    data.execute("select P.estadoR from carnet C, alumno A, parcial P , calificacionesmat CM WHERE  A.matricula = C.matricula and A.matricula='%s' and C.id=CM.idCarnet and CM.idCarnet=P.idCarnet and P.numParcial='%s' " %(matricula, parcial))
    estadoR= data.fetchone()
    return estadoR[0]

#estadoR=reporte_entrga(2017020266,1)
#rint(estadoR)


def calificacion_final(matricula,materia):
    #results=listar_materias(matricula)
    #for mat in results:
    #    print(mat[0],mat[1])
    #materia=input("Ingresa el id de la meteria desada...")
    data.execute("select EF.CalfPromedio from evaluacionf EF WHERE EF.matricula ='%s' and EF.idMateria='%s'"%(matricula, materia))
    calFinal= data.fetchone()
    return calFinal[0]
    
#calFinal=calificacion_final('2017020266','1')
#print(calFinal)

def calf_extra1(matricula,materia):
    """results=listar_materias(matricula)
    for mat in results:
        print(mat[0],mat[1])
    materia=input("Ingresa el id de la meteria desada...")"""
    #data.execute("select CE.califEx1 from evaluacionf EF, calificaciones CE, calificaciones CE1 where  EF.matricula=CE.matricula and CE.matricula ='%s' and EF.idMateria= CE.idMateria and (CE.idCEEE>CE1.idCEEE)  and CE.idMateria='%s' and EF.tipoEvaluacion='1' "%(matricula, materia))
    data.execute("select CE.califEx1 from evaluacionf EF, calificaciones CE, alumno A, carnet C , calificacionesmat CM where  A.matricula= C.matricula and EF.matricula=CE.matricula and CE.matricula ='%s' and A.periodo= C.periodo and  C.id=CM.idCarnet and (C.idCM1=CM.idCM or C.idCM2=CM.idCM or C.idCM3=CM.idCM or C.idCM4=CM.idCM or C.idCM5=CM.idCM) and CM.idMateria=EF.idMateria and EF.idMateria= CE.idMateria and CE.idMateria='%s'  and CM.idEF=EF.idEF   and CM.idCEEE=CE.idCEEE  and EF.tipoEvaluacion='1'" %(matricula, materia))
    calExtra1= data.fetchone()
    return calExtra1[0]

#calExtra=calf_extra1('2017020266','6')
#print(calExtra)


def calf_extra2(matricula,materia):
    """results=listar_materias(matricula)
    for mat in results:
        print(mat[0],mat[1])
    materia=input("Ingresa el id de la meteria desada...")"""
    data.execute("select CE.califEx2 from evaluacionf EF, calificaciones CE, alumno A, carnet C , calificacionesmat CM where  A.matricula= C.matricula and EF.matricula=CE.matricula and CE.matricula ='%s' and A.periodo= C.periodo and  C.id=CM.idCarnet and (C.idCM1=CM.idCM or C.idCM2=CM.idCM or C.idCM3=CM.idCM or C.idCM4=CM.idCM or C.idCM5=CM.idCM) and CM.idMateria=EF.idMateria and EF.idMateria= CE.idMateria and CE.idMateria='%s'  and CM.idEF=EF.idEF   and CM.idCEEE=CE.idCEEE  and EF.tipoEvaluacion='1'" %(matricula, materia))
    calExtra2= data.fetchone()
    return calExtra2[0]

#calExtra=calf_extra2('2017020266','1')
#print(calExtra)
def  calificacionE(matricula,materia):
     data.execute("select CE.especial from evaluacionf EF, calificaciones CE, calificaciones CE1 where  EF.matricula=CE.matricula and CE.matricula ='%s' and EF.idMateria= CE.idMateria and (CE.idCEEE>CE1.idCEEE)  and CE.idMateria='%s' and EF.tipoEvaluacion='1'"%(matricula, materia))
     calEspecial= data.fetchone()
     return calEspecial[0]
    
def tipo_semestre(matricula):
     data.execute("select S.tipoS from semestre S, alumno A where A.matricula ='%s' and A.idSemetre=S.idSemestre" %(matricula))
     tipoSem= data.fetchone()
     return tipoSem[0]
 
#typeS=tipo_semestre('2017020266')
#print(typeS)
def tipo_evalaucion(matricula, idM):
    data.execute("select EF.tipoEvaluacion from evaluacionf EF WHERE EF.matricula ='%s' and EF.idMateria='%s'"%(matricula, idM))
    tipoE= data.fetchone()
    return tipoE[0]
    
def recursa_estadoImpar(matricula,idM):
    #results=listar_materias(matricula)
    #for mat in results:
    #    print(mat[0],mat[1])# MODIFICAR SI ID MAT ES EL MISMO ENTONCES RETORNA UNO DE LO CONTRARIO 0 UN IF DE LA CONSULTA
   # idM=input("Ingresa el id de la meteria desada...")
    data.execute("select M.idMateria, M.nombre from  materias M , alumno A, carnet C, semestre S ,calificacionesmat CM where A.matricula= C.matricula and C.matricula ='%s' and A.periodo= C.periodo and A.idSemetre>C.idSemestre and C.idSemestre=S.idSemestre and S.tipoS='A' and CM.idCM =C.idCM1 and M.idMateria='%s'"%(matricula,idM))
    recursada= data.fetchone()
    return recursada[0]
    if(idM== recursada[0]):
        return 1
    else:
        return -1
#typeS=recursa_estadoImpar('2017020266','1')
#print(typeS)

def recursa_estadoPar(matricula, idM, idM2):
    data.execute("select M.idMateria from  materias M , alumno A, carnet C, semestre S ,calificacionesmat CM where A.matricula= C.matricula and C.matricula ='%s' and A.periodo= C.periodo and A.idSemetre>C.idSemestre and C.idSemestre=S.idSemestre and S.tipoS='B' and ( (CM.idCM =C.idCM1 and M.idMateria='%s') or (CM.idCM =C.idCM2 and M.idMateria='%s')) "%(matricula,idM, idM2))
    recursada= data.fetchall()
    if (idM==recursada[0][0] and idM2==recursada[1][0]) or (idM==recursada[0][0] and idM2!=0):
        return 1
    else :
        return -1
        
def  materias_cursadas(matricula): #el numero de materias cursadas actualmente
    data.execute("select  COUNT(DISTINCT CM.idMateria) from carnet C, alumno A, calificacionesmat CM  , materias M where A.matricula=C.matricula and A.matricula='%s'  and C.periodo=A.periodo and C.id= CM.idCarnet and (C.idCM1=CM.idCM  or C.idCM2=CM.idCM or C.idCM3=CM.idCM or C.idCM4=CM.idCM or C.idCM5=CM.idCM) and CM.idMateria=M.idMateria" %(matricula))
    numMat=data.fetchone()
    return numMat[0]
#num_materias=materias_cursadas('2017020266')
#print(num_materias)

def lista_idMaterias(matricula): # la lista de los id de las materias cursadas actualmente
    data.execute("select  DISTINCT CM.idMateria from carnet C, alumno A, calificacionesmat CM  , materias M where A.matricula=C.matricula and A.matricula='%s'  and C.periodo=A.periodo and C.id= CM.idCarnet and (C.idCM1=CM.idCM  or C.idCM2=CM.idCM or C.idCM3=CM.idCM or C.idCM4=CM.idCM or C.idCM5=CM.idCM) and CM.idMateria=M.idMateria "%(matricula))    
    listId=data.fetchall()
    return listId
    
def num_Mrepeobadas(matricula): # el numero de materias no aporobadas  antes del extra, actualmente
    contador=0
    results=lista_idMaterias(matricula)
    for mat in results:
       matCalf=calificacion_final(matricula,mat[0])
       #print(matCalf)
       if matCalf<6.0 :
           #print("Aqui")
           contador += 1
    return contador
           
#num=num_Mrepeobadas('2017020266')       
#print(num) 
def noAprobada_imparA(matricula,idMR):
    tipoS=tipo_semestre(matricula)
    estado=0
    #print(tipoS)
    if tipoS=='A': 
        calEx2=calf_extra2(matricula,idMR)
        #print(calEx2)
        if calEx2<6.0 :
            estado=1
    return estado

def num_matAprobadas(matricula):
    count=0
    results=lista_idMaterias(matricula)
    for mat in results:
       matCalf=calificacion_final(matricula,mat[0])
       if matCalf>=6.0:
           count+=1
       else:
           matCalf=calf_extra1(matricula,mat[0])
           if matCalf>=6.0:
               count+=1 
           else:
               matCalf=calf_extra2(matricula,mat[0])
               if matCalf>=6.0:
                   count+=1 
               else:
                    matCalf=calificacionE(matricula,mat[0])
                    if matCalf>=6.0:
                        count+=1 
    return count
print(num_matAprobadas('2017020266'))           
               
estado=noAprobada_imparA('2017020266',1)
print(estado)

