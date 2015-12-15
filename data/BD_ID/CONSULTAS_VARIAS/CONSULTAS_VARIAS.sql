use id_bt
go

sp_help pronostico_m

SELECT * FROM TIPO_PRONOSTICO

SELECT * FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 0
	
SELECT liga, acierto, count(*) 
FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
group by 
	liga, acierto
order by 1, 2


SELECT * FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 1
	AND ACIERTO = 0
	
SELECT acierto, count(*) 
FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 1
group by 
	acierto	


SELECT CONVERT(NUMERIC, '1,24')

SELECT CONVERT(NUMERIC(3,2), REPLACE('1,24', ',', '.'))




----------------------------------
-- rentabilidad del > 70
----------------------------------
SELECT Acierto, PagoPaginas, 
	case 
		when Acierto = 1 then (100*PagoPaginas - 100)
		when Acierto = 0 then - 100
	end G
FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 1


SELECT *
FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 1
	and acierto = 0


select sum(SQ.G)
from 
(
SELECT Acierto, PagoPaginas, 
	case 
		when Acierto = 1 then (100*PagoPaginas - 100)
		when Acierto = 0 then - 100
	end G
FROM PRONOSTICO_M
WHERE
	PorcConfianza >= 70
	AND LIGA = 1
) SQ


SELECT *
FROM PRONOSTICO_M
WHERE
	PronosticoDobles = '12'
	and AciertoDobles = 0


-----------------------
sp_help PRONOSTICO_M
SELECT *
FROM PRONOSTICO_M
WHERE
	NomEqL = 'Fenerbahce'




SELECT AciertoDobles, count(*)
FROM PRONOSTICO_M
where
liga = 1
group by
	AciertoDobles


select sum(SQ.G)
from 
(
SELECT AciertoDobles, PagoPaginasDobles, 
	case 
		when AciertoDobles = 1 then (100*PagoPaginasDobles - 100)
		when AciertoDobles = 0 then - 100
	end G
FROM PRONOSTICO_M
WHERE
	LIGA = 1
) SQ
