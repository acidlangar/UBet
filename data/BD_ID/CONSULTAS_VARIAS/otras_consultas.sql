use id_bt
go
/*
select
*
from pronostico_m
where
	fecha >= '13/03/2015'
	and fecha <= '16/03/2015'
	and porcconfianza >= 70
	and acierto = 0
order by 
	porcconfianza desc


select
acierto, count(*)
from pronostico_m
where
	fecha >= '13/03/2015'
	and fecha <= '16/03/2015'
	and porcconfianza >= 70
	and pagopaginas >= 1.3
	and pagopaginas < 2
group by
	acierto 
*/
select top 4
pronostico, pronosticodobles, aciertodobles, pagopaginas_1, pagopaginas_2, pagopaginas_x 
from pronostico_m
where
	fecha >= '13/03/2015'
	and fecha <= '16/03/2015'
	and porcconfianza >= 70
	and pagopaginas >= 1.3
	and pagopaginas < 2

select ((3.30 * 3.40) - 1) * 15 