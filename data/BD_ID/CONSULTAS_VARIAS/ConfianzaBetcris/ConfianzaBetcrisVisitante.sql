use id_bt
go

select 
	'Betcris 90 a 100' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.9

union 

select 
	'Betcris 80 a 89.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.8
	and 1/PagoPaginas_2 < 0.9

union 

select 
	'Betcris 70 a 79.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.7
	and 1/PagoPaginas_2 < 0.8

union 

select 
	'Betcris 60 a 79.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.6
	and 1/PagoPaginas_2 < 0.7

union 

select 
	'Betcris 50 a 59.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.5
	and 1/PagoPaginas_2 < 0.6

union 

select 
	'Betcris 40 a 49.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.4
	and 1/PagoPaginas_2 < 0.5

union 

select 
	'Betcris 30 a 39.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.3
	and 1/PagoPaginas_2 < 0.4

union 

select 
	'Betcris 20 a 29.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.2
	and 1/PagoPaginas_2 < 0.3

union 

select 
	'Betcris 10 a 19.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 >= 0.1
	and 1/PagoPaginas_2 < 0.2

union 

select 
	'Betcris 0 a 9.99' Inversa,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '2' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '2' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_2 > 0
	and 1/PagoPaginas_2 < 0.1