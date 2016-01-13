use id_bt
go

select 
	count(1) totalJuegos,
	SUM(
		case 
			when resultado = '1' then 1
			else 0
		end	
	) totalVictorias,
	SUM(
		case 
			when resultado = 'X' then 1
			else 0
		end	
	) totalEmpates,
	SUM(
		case 
			when resultado = '2' then 1
			else 0
		end	
	) totalDerrotas,
	AVG(Cuota1) PromeCuota1,
	AVG(CoutaX) PromeCuotaX,
	AVG(Cuota2) PromeCuota2
from partido
where
	equipo_local = 'Barcelona'
	and cuota1 <= 1.2


select 
	AVG(cuota1) PromeCuota1,
	(1/AVG(cuota1)) * COUNT(1) ExpectativaVictorias,
	SUM(
		case 
			when resultado = '1' then 1
			else 0
		end	
	) RealidadVictorias
from partido
where
	equipo_local = 'Barcelona'
	and cuota1 <= 1.2


select 
	AVG(cuota2) PromeCuota1,
	(1/AVG(cuota2)) * COUNT(1) ExpectativaDerrotas,
	SUM(
		case 
			when resultado = '2' then 1
			else 0
		end	
	) RealidadDerrotas
from partido
where
	equipo_local = 'Barcelona'
	and cuota1 <= 1.2


select 
	AVG(coutaX) PromeCuotaX,
	(1/AVG(coutaX)) * COUNT(1) ExpectativaEmpates,
	SUM(
		case 
			when resultado = 'X' then 1
			else 0
		end	
	) RealidadEmpates
from partido
where
	equipo_local = 'Barcelona'
	and cuota1 <= 1.2

select ((1/AVG(coutaX)) * COUNT(1)) 
	+ ((1/AVG(cuota2)) * COUNT(1)) 
	+ (1/AVG(cuota1)) * COUNT(1)   SumatoriaExpectativa
from partido
where
	equipo_local = 'Barcelona'
	and cuota1 <= 1.2