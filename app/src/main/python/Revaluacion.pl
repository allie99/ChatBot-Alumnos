asistencia_Periodo(Asiste):- (Asiste) >=80.
reporte(X):- entrega(X).
entrega(1).

der_Evaluacion(Asiste, X,RA):- asistencia_Periodo(Asiste), reporte(X), reporte_Anterior(RA).
 
reporte_Anterior(Confirma):-entrega(Confirma).

der_ExamenOrdinario(Asistencia):- Asistencia >= 85.

lista(Asistencia):- Asistencia >= 70.

der_Extra(CalF, Asistencia):-(CalF<6.0),lista(Asistencia).

der_ExtraP(Asistencia):- \+ (lista(Asistencia)).

solicitarExtra(Cursada, Extra1, Extra2):- materia(Cursada), presentaE1(Extra1),presentaE2(Extra2).
materia(1).
presentaE1(1).
presentaE1(0).
presentaE2(0).

noDerExtra(NumAsig,NumReprob):- NumReprob=4,NumAsig>=5 .
noDerExtra(NumAsig,NumReprob):-NumReprob=3,NumAsig==4.
noDerExtra(NumAsig,NumReprob):-NumReprob=2 ,NumAsig>=3.
noDerExtra(NumAsig,NumReprob):-NumReprob=1,NumAsig=1.

solicitudEspecial(Recursa, Ext1, Ext2):-materia1(Recursa),(Ext1<6.0),(Ext2<6.0).

solicitudEspecial(Recursa, Estado):-materia1(Recursa),derExtra(Estado).
materia1(1).
derExtra(1).

examReprobadoE(Calf):- (Calf < 6).

der_Verano(Band,A):-no_Aprobada(Band),num_semestreI(A).
no_Aprobada(1).
num_semestreI('A').

matVerano(DerechoMateria, Pago):-derecho(DerechoMateria),recibo(Pago).
recibo(1).
derecho(1).     













