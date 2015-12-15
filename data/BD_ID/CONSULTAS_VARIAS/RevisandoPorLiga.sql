use id_bt
go

--
-- TAMBIEN ES BIEN IMPORTANTE, HACER EL ANÁLISIS POR EQUIPO, RENDIMIENTO POR EQUIPO EN VEZ DE POR CANTIDAD DE GANADOS....
-- CÚAL HA SIDO EL EQUIPO MÁS RENDIDOR HASTA EL MOMENTO... Y CUÁL HA SI EL EQUIPO EN CONTRA POR EL CUAL ES MÁS RENDIDOR!!!
--



select --top 10
		liga, 
		sum((pagopaginas*acierto) - 1) * 100 PorcResultante,
		count(1) CantTotaJuegos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) CantTotalDesaciertos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) * 100 / count(1) PorcDesaciertos
	from pronostico_m
	where 
		fecha < '27/03/2015'
		and pagopaginas >= 1.3
		and pagopaginas <= 1.99
		and porcconfianza >= 70
group by liga
having count(1) > 10
order by 2 desc




select --top 10
		liga, 
		sum((pagopaginas*acierto) - 1) * 100 PorcResultante,
		count(1) CantTotaJuegos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) CantTotalDesaciertos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) * 100 / count(1) PorcDesaciertos
	from pronostico_m
	where 
		fecha < '27/03/2015'
		and pronostico = 'X'
		and porcconfianza >= 50
group by liga
having count(1) > 10
order by 2 desc




select 	liga, 
		sum((pagopaginas*acierto) - 1) * 100 PorcResultante,
		count(1) CantTotaJuegos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) CantTotalDesaciertos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) * 100 / count(1) PorcDesaciertos
	from pronostico_m
	where 
		fecha < '27/03/2015'
		and pronostico = '1'
		and porcconfianza >= 50
group by liga
having count(1) > 10
order by 2 desc



select 
		sq2.liga,
		sum((sq2.pagopaginas*sq2.acierto) - 1) * 100 PorcResultante,
		count(1) CantTotaJuegos,
		sum(case
				when sq2.acierto = 1 then 0
				else 1 
			end) CantTotalDesaciertos,
		sum(case
				when sq2.acierto = 1 then 0
				else 1 
			end) * 100 / count(1) PorcDesaciertos
from
(
select sq.liga, 
	case 
		when sq.ResultadoReal = '2' then 1
		else 0
	end Acierto, 
	pagoPaginas_2 pagoPaginas
from
(
select 
	case
		when acierto = 1 and pronostico = '1' then '1'
		when acierto = 1 and pronostico = '2' then '2'
		when acierto = 1 and pronostico = 'X' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = '1' and pronosticodobles = '12' then 'X'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = '1' and pronosticodobles = '1X' then '2'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '1' and pronosticodobles = '12' then '2'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '1' and pronosticodobles = '1X' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = '2' and pronosticodobles = '21' then 'X'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = '2' and pronosticodobles = 'X2' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '2' and pronosticodobles = '21' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '2' and pronosticodobles = 'X2' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = 'X' and pronosticodobles = '1X' then '2'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = 'X' and pronosticodobles = 'X2' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = 'X' and pronosticodobles = '1X' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = 'X' and pronosticodobles = 'X2' then '2'

		else 'problema'
	end ResultadoReal,
		*
	from pronostico_m
	where 
		fecha < '27/03/2015'
		and pronostico = '1'
		and pagopaginas_2 >= 2
		and pagopaginas_2 < 3
		and porcconfianza < 50
) sq
) sq2
group by sq2.liga
having count(1) > 10
order by 2 desc