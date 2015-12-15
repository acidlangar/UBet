use id_bt
go

select count(1)
from pronostico_m


select distinct fecha
from pronostico_m
order by fecha desc


			select 
				PagoPaginas_1, PagoPaginas_X, 1/PagoPaginas_1 Inv_1, 1/PagoPaginas_X Inv_X, (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 , 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100, ResultadoReal
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha < '28/02/2015'
				and 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 > 0



			select 
				(1/PagoPaginas_1 + 1/PagoPaginas_X)*100 Porcentaje, 
				100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 GP, 
				ResultadoReal
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha < '28/02/2015'
				and 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 > 0



			select 
				sum(
					case
						when sq.ResultadoReal = '2' then -100
						else sq.gp
					end
				)
			from 
			(
			select 
				--(1/PagoPaginas_1 + 1/PagoPaginas_X)*100 Porcentaje, 
				100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 GP, 
				ResultadoReal
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha < '28/02/2015'
				and 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 > 0
			) sq


			select 
				sum(
					case
						when ResultadoReal = '2' then PagoPaginas_2-1
						else -1
					end
				)
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha < '28/02/2015'
				and 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 > 0
			

			


			select 
				--(1/PagoPaginas_1 + 1/PagoPaginas_X)*100 Porcentaje, 
				100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 GP, 
				ResultadoReal
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha < '28/02/2015'
				and 100 - (1/PagoPaginas_1 + 1/PagoPaginas_X)*100 > 0
			





select 
	fecha,
	ResultadoReal,
	PagoPaginas_1,
	PagoPaginas_X,
	PagoPaginas_2
from v_result_pronosticos
where 
	fecha < '28/02/2015'
	and nomEqL = 'Barcelona'
	and liga = 'espana-1'
order by fecha


select 
	fecha,
	ResultadoReal,
	PagoPaginas_1,
	PagoPaginas_X,
	PagoPaginas_2
from v_result_pronosticos
where 
	fecha < '28/02/2015'
	and nomEqV = 'Barcelona'
	and liga = 'espana-1'
order by fecha



select 
	fecha,
	ResultadoReal,
	PagoPaginas_1,
	PagoPaginas_X,
	PagoPaginas_2
from v_result_pronosticos
where 
	fecha < '28/02/2015'
	and nomEqL = 'Real Madrid'
	and liga = 'espana-1'
order by fecha


select 
	fecha,
	ResultadoReal,
	PagoPaginas_1,
	PagoPaginas_X,
	PagoPaginas_2
from v_result_pronosticos
where 
	fecha < '28/02/2015'
	and nomEqV = 'Real Madrid'
	and liga = 'espana-1'
order by fecha



