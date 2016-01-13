use id_bt
go

select 
	count(1) TotalSuperFavoritos,
	avg(cuota1) PromedioCuota,
	(count(1) * 1/avg(cuota1)) Expectativa,
	sum(
		case
			when resultado = '1' then 1
			else 0
		end
	) Realidad
from partido
where
	cuota1 <= 1.2


select 
	count(1) TotalSuperFavoritos,
	avg(cuota2) PromedioCuotaSorpresa,
	(count(1) * 1/avg(cuota2)) ExpectativaSopresa,
	sum(
		case
			when resultado = '2' then 1
			else 0
		end
	) RealidadSorpresa
from partido
where
	cuota1 <= 1.2


select 
	count(1) TotalSuperFavoritos,
	avg(coutaX) PromedioCuotaEmpates,
	(count(1) * 1/avg(coutaX)) ExpectativaEmpates,
	sum(
		case
			when resultado = 'X' then 1
			else 0
		end
	) RealidadSorpresa
from partido
where
	cuota1 <= 1.2


select Resultado
from partido
where
	cuota1 <= 1.2
order by fecha