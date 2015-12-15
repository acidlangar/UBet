use id_bt
go

-- calculando para la fecha '21/03/2015'
select fecha, * 
from v_result_pronosticos
where
	fecha in ('07/02/2015') -- ('21/02/2015')
	and pagoPaginas >= 1.3
	and pagoPaginas <= 1.99
	and porcConfianza >= 70
	and acierto = 1
order by 
	1 desc, PagoPaginas desc

/*
select top 1 fecha
from v_result_pronosticos
where
	fecha < '18/03/2015'
order by fecha desc


select fecha, count(1)
from v_result_pronosticos
where
	fecha < '27/03/2015'
	and pagoPaginas >= 1.3
	and pagoPaginas <= 1.99
	and	porcConfianza >= 70
group by fecha
order by 1 desc


*/

