use id_bt

select top 10 *
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10


select top 1 fecha
from v_result_pronosticos
group by fecha
order by fecha desc


select fecha
from v_result_pronosticos
group by fecha
order by fecha desc


select count(1) Total, 
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end)
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'


select count(1) Total, 
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) Victoria2
from v_result_pronosticos
where 
	pagoPaginas_2 > 3
	and pagoPaginas_2 <= 6
	and fecha < '28/02/2015'



select count(1) Total, 
	sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Victoria2
from v_result_pronosticos
where 
	pagoPaginas_X > 3
	and pagoPaginas_X <= 6
	and fecha < '28/02/2015'





select fecha, count(1)
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'
group by fecha
order by 2 desc



select fecha, count(1) CantJuegos_Visitan3_6, sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) VictoriaVisitante
from v_result_pronosticos
where 
	pagoPaginas_2 > 3
	and pagoPaginas_2 <= 6
	and fecha < '28/02/2015'
group by fecha
order by 1 desc



select fecha, count(1) CantJuegos_Empate3_6, sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Empate
from v_result_pronosticos
where 
	pagoPaginas_X > 3
	and pagoPaginas_X <= 6
	and fecha < '28/02/2015'
group by fecha
order by 1 desc


-- buenas fechas '30/08/2014'; '21/02/2015'
-- malas fechas '13/12/2014'
select PagoPaginas_2, ResultadoReal, Liga, Fecha, NomEqL, NomEqV
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha = '09/08/2014'