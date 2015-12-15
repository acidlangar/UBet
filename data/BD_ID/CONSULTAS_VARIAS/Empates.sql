use id_bt
go

select 
	liga, 
	sum(
		(Acierto * PagoPaginas) - 1 
	) Resultado,
	sum(1) Cantidad
from pronostico_m
where
	--PorcConfianza >= 50
	--and 
	Pronostico = 'X'
	and PagoPaginas > 0
	and Fecha < '04/01/2015'
group by 
	liga
having
	sum(1) > 10
order by 
	2 desc