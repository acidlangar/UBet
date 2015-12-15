use id_bt

select top 10 *
from v_result_pronosticos



select fecha, count(1) CantJuegos_Impredecibles, sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Empate,
	sum(case 
			when ResultadoReal = '1' then 1 
			else 0 
	end) Local,
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) Visitante
from v_result_pronosticos
where 
	pagoPaginas_1 >= 2
	and pagoPaginas_1 < 4
	and pagoPaginas_2 >= 2
	and pagoPaginas_2 < 4
	and fecha < '28/02/2015'
group by fecha
order by 1 desc



select count(1) CantJuegos_Impredecibles, sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Empate,
	sum(case 
			when ResultadoReal = '1' then 1 
			else 0 
	end) Local,
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) Visitante
from v_result_pronosticos
where 
	pagoPaginas_1 >= 2
	and pagoPaginas_1 < 4
	and pagoPaginas_2 >= 2
	and pagoPaginas_2 < 4
	and liga = 'italia'
	and fecha < '28/02/2015'
order by 1 desc
