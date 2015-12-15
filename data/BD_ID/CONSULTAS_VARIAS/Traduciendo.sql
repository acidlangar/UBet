use id_bt
go

select liga, AciertoDobles, count(*)
from pronostico_m
where
	Acierto = 0
	and Fecha < '04/01/2015'
group by
	liga, AciertoDobles
order by
	1,2 


select top 10
	liga, 
	(CASE
		WHEN Acierto = 1 THEN Pronostico
		WHEN Acierto = 0 AND AciertoDobles = 1 THEN (PronosticoDobles + ' menos ' + Pronostico)
		WHEN Acierto = 0 AND AciertoDobles = 0 THEN ('Contrario ' + PronosticoDobles)
	END) Resultado
from pronostico_m
where
	Fecha < '04/01/2015'
	