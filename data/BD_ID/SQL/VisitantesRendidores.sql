/*select 
	Liga,
	NomEqL Local, 
	count(1) CantJuegos, 
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end
	 ) VictoriasLocales,
	sum(
		case
			when ResultadoReal <> '1' then 1
			else 0
		end
	 ) NoVictoriasLocales,
	sum(
		((case
			when ResultadoReal = '1' then 1
			else 0
		end) * PagoResultado ) - 1

	 ) * 100 Rendimiento
from V_RESULT_PRONOSTICOS
where
	fecha < '27/03/2015'
	--and liga = 'espana-1'
group by liga, NomEqL
having count(1) > 10
order by 6 desc*/

-- sp_help V_RESULT_PRONOSTICOS


select 
	Liga,
	NomEqV Local, 
	count(1) CantJuegos, 
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end
	 ) VictoriasLocales,
	sum(
		case
			when ResultadoReal <> '2' then 1
			else 0
		end
	 ) NoVictoriasLocales,
	sum(
		((case
			when ResultadoReal = '2' then 1
			else 0
		end) * PagoResultado ) - 1

	 ) * 100 Rendimiento
from V_RESULT_PRONOSTICOS
where
	fecha < '27/03/2015'
	--and liga = 'espana-1'
group by liga, NomEqV
having count(1) > 10
order by 6 desc






select ResultadoReal, PagoResultado
from v_result_pronosticos
where
	liga = 'brasil'
	and fecha < '27/03/2015'
	and NomEqV = 'Cruzeiro'
	and ResultadoReal = '2'


	
/*

	

select 
	fecha, 
	count(1) CantPagosFuertes, 
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end
	) CantAciertosFuertes
from v_result_pronosticos
where 
	pagoPaginas_2 >= 5
	pagoPaginas_2 < 10
	and fecha < '27/03/2015'
group by fecha

*/

/*
select 
	count(1) CantPagosFuertes, 
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end
	) CantAciertosFuertes,
	sum(
		((case
			when ResultadoReal = '2' then 1
			else 0
		end) * PagoResultado ) - 1

	 ) * 100 Rendimiento
from v_result_pronosticos
where 
	pagoPaginas_2 >= 5
	and pagoPaginas_2 < 10
	and fecha < '27/03/2015'
*/
