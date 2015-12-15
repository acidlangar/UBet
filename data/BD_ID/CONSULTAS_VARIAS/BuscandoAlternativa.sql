-- select distinct top 1 fecha from pronostico_m order by fecha desc
/*
-- favoritos, solo 1 di positivo de 10.
select top 10
		fecha,
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
		--and pagopaginas >= 1.3
		--and pagopaginas <= 1.99
		and porcconfianza <= 50
group by fecha
order by fecha desc



-- empates. Cuatro positivos de diez
select top 10
		fecha,
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
group by fecha
order by fecha desc


-- Apuesta contra favoritos
-- favoritos, 
select top 5 
	Liga, Pronostico, Acierto, PorcConfianza_1, PagoPaginas_1, PorcConfianza_2, PagoPaginas_2, PorcConfianza_X, PagoPaginas_X
from pronostico_m 

select top 5 
	case
		when pronostico = '1' and pagoPaginas_2 >= pagoPaginas_X then '2' 
		when pronostico = '1' and pagoPaginas_2 < pagoPaginas_X then 'X' 
		when pronostico = '2' and pagoPaginas_1 >= pagoPaginas_X then '1' 
		when pronostico = '2' and pagoPaginas_1 < pagoPaginas_X then 'X' 
		when pronostico = 'X' and pagoPaginas_1 >= pagoPaginas_2 then '1' 
		when pronostico = 'X' and pagoPaginas_1 < pagoPaginas_2 then '2' 
	end ApuestaPor,
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
	Liga, Pronostico, PronosticoDobles, Acierto, AciertoDobles, PorcConfianza_1, PagoPaginas_1, PorcConfianza_2, PagoPaginas_2, PorcConfianza_X, PagoPaginas_X
from pronostico_m 

select top 10
		fecha,
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
		and pagopaginas_2 >= 4
		and porcconfianza < 50
group by fecha
order by fecha desc
*/
select top 10
		sq2.fecha,
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
select sq.fecha, 
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
group by sq2.fecha
order by sq2.fecha desc
/*
select 
-17.000
+381.000
-20.000
-100.000
+253.000
+1169.000
-40.000
-46.000
-200.000
+229.000
*/