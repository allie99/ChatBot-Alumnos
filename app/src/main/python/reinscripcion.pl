der_reiniscribir(PagoC, MB,MT,ML, Acreditado):-colegiatura(PagoC),adeudos(MB,MT,ML),materiasAcreditadas(Acreditado).
colegiatura(1).

adeudos(MB,MT,ML):-adeudo_MB(MB),adeudo_MT(MT),adeudo_ML(ML).
adeudo_MB(1).
adeudo_MT(1).
adeudo_ML(1).


materia('Aprobada',PromFinal):-PromFinal>=6.0.


reinscripcion('Aprobado',NumMat,TipoS):-numsemestreI(TipoS),NumMat>=4 .
reinscripcion('Aprobado',NumMat,TipoS):-numsemestreP(TipoS),NumMat>=3 .
numsemestreI('A').
numsemestreP('B').

baja(NumMat, TipoS):- \+ (reinscripcion('Aprobado',NumMat,TipoS)).
baja(NumMat,TipoS):- \+ ( reinscripcion('Aprobado',NumMat,TipoS)).

