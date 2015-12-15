use id_bt
go

select  
	liga, 
	sum(
		(Acierto * PagoPaginas) - 1 
	) Resultado
from pronostico_m
where 
	PorcConfianza >= 70
	and PagoPaginas >= 1.3
	and PagoPaginas < 2
	and Fecha < '04/01/2015'
group by 
	liga
order by 
	2 desc



select
	sq.liga,
	((sq.Aciertos * 100) / (sq.Aciertos + sq.Fallos)) PorcAcierto,
	sq.Aciertos,
	sq.Fallos
from 
(
	select  
		liga, 
		sum(
			case
				when Acierto = 1 then 1
				else 0
			end
		) Aciertos, 
		sum(
			case
				when Acierto = 0 then 1
				else 0
			end
		)Fallos 
	from pronostico_m
	where 
		PorcConfianza >= 70
		and PagoPaginas >= 1.1
		and PagoPaginas < 2
		and Fecha < '04/01/2015'
	group by 
		liga
) sq
where
	sq.Aciertos >= 10
order by 2 desc