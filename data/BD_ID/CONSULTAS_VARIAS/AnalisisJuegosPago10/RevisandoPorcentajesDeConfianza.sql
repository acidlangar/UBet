select count(1) Total, 
	sum(case 
			when ResultadoReal = '1' then 1 
			else 0 
	end) AciertoCasa,
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) Sorpresas,
	sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Empates
from v_result_pronosticos
where 
	pagoPaginas_1 >= 1.3
	and pagoPaginas_2 >= 10
	and fecha < '28/02/2015'


select count(1) Total, 
	sum(case 
			when ResultadoReal = '2' then 1 
			else 0 
	end) Casa,
	sum(case 
			when ResultadoReal = '1' then 1 
			else 0 
	end) Sorpresas,
	sum(case 
			when ResultadoReal = 'X' then 1 
			else 0 
	end) Empates
from v_result_pronosticos
where 
	pagoPaginas_1 >= 10
	and fecha < '28/02/2015'




select distinct Liga, NomEqL
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and ResultadoReal in ('X')
	and fecha < '28/02/2015'


select distinct Liga, NomEqL
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and ResultadoReal in ('2')
	and fecha < '28/02/2015'
