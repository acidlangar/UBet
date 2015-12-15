use id_bt
go

select
	sq.liga, sq.Nom Nom, sum(sq.CantJ) CantJ, sum(sq.Empates) Empates, sum(sq.Rend) Rend
from
(
select 
	liga, 
	NomEqL Nom, 
	count(1) CantJ, 
	sum(
	case 
		when ResultadoReal = 'X' then 1
		else 0
	end
	) Empates,
	sum(
	 ((case when ResultadoReal = 'X' then 1 else 0 end) * PagoPaginas_X) - 1
	) * 100 Rend
from v_result_pronosticos
where 
	fecha < '28/03/2015'
	--and fecha > '13/12/2014'

group by
	liga, NomEqL

union all

select 
	liga, 
	NomEqV Nom, 
	count(1) CantJ, 
	sum(
	case 
		when ResultadoReal = 'X' then 1
		else 0
	end
	) Empates,
	sum(
	 ((case when ResultadoReal = 'X' then 1 else 0 end) * PagoPaginas_X) - 1
	) * 100 Rend
from v_result_pronosticos
where 
	fecha < '28/03/2015'
	--and fecha > '13/12/2014'
group by
	liga, NomEqV
) sq
--where
--	sq.liga = 'espana-1'
group by sq.liga, sq.Nom
order by 5 desc
--sp_help v_result_pronosticos

-- select distinct fecha from v_result_pronosticos order by fecha desc


