use id_bt
go

select Pronostico, PronosticoDobles, PorcConfianza_1, PorcConfianza_2, PorcConfianza_X  from pronostico_m

delete pronostico_m

select Acierto, count(1)
from pronostico_m
where
	pronostico = 'X'
	and porcConfianza_X > 50
	and pagoPaginas <> 0
group by acierto


select min(pagoPaginas), max(pagoPaginas), sum(pagoPaginas) / count(1)
from pronostico_m
where
	pronostico = 'X'
	and pagoPaginas <> 0