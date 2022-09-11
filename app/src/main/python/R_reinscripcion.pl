der_reiniscribir(PagoC, MB,MT,ML, Acreditado):-colegiatura(PagoC),adeudos(MB,MT,ML),materiasAcreditadas(Acreditado).
colegiatura(1).

adeudos(MB,MT,ML):-Adeudo_MB(MB),Adeudo_MT(MT),Adeudo_ML(ML).
Adeudo_MB(1).
Adeudo_MT(1).
Adeudo_ML(1).


materia('Aprobada',PromFinal):-PromFinal>=6.0.


reinscripcion('Aprobado',NumMat,a):-numsemestreP(a),NumMat>=4 .
reinscripcion('Aprobado',NumMat,b):-numsemestreI(b),NumMat>=3 .
numsemestreI(a).
numsemestreP(b).

baja(temp):- not(reinscripcion('Aprobado',NumMat,a)).
baja(temp):- not ( reinscripcion('Aprobado',NumMat,b)).

