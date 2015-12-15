use id_bt
go

select 
	'Favoritos mayores a cinco' M,
	sum((acierto * pagopaginas) - 1) Rend, 
	count(*) CantT,
	sum((acierto * pagopaginas) - 1)/count(*) RazonRend,
	sum( acierto ) Aciertos,
	count(*) - sum(acierto) desaciertos,
	convert(decimal,sum( acierto ))/convert(decimal,count(*) - sum(acierto)) RazonAcieDesac
from pronostico_m
where
	pagopaginas >= 5
	and Pronostico <> 'X'
	and porcConfianza >= 50


select 
	'Favoritos mayores a 70' M,
	sum((acierto * pagopaginas) - 1) Rend, 
	count(*) CantT,
	sum((acierto * pagopaginas) - 1)/count(*) RazonRend,
	sum( acierto ) Aciertos,
	count(*) - sum(acierto) desaciertos,
	convert(decimal,sum( acierto ))/convert(decimal,count(*) - sum(acierto)) RazonAcieDesac
from pronostico_m
where
	pagopaginas >= 1.3
	and pagopaginas < 2
	and porcConfianza >= 70

--select distinct fecha from pronostico_m
--select convert(decimal,431)/convert(decimal,72), convert(decimal,65)/convert(decimal,72)
