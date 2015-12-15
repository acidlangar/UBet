use id_bt
go

select count(*) from pronostico_m

select acierto, count(*)
from pronostico_m
where PagoPaginas > 3
group by acierto

select acierto, count(*)
from pronostico_m
where pronostico = 'X'
group by acierto

select acierto, PagoPaginas
from pronostico_m
where pronostico = 'X'

select acierto, count(*)
from pronostico_m
where PagoPaginas >= 1.25 and PagoPaginas < 2 
and PorcConfianza >= 70
group by acierto

select acierto, PagoPaginas 
from pronostico_m
where PagoPaginas >= 1.25 and PagoPaginas < 2 
and PorcConfianza >= 70
order by PagoPaginas desc


sp_help pronostico_m