use id_bt
go

-- sp_help v_result_pronosticos

declare @vliga varchar(50)
declare @vequipo varchar(50)

/*
X italia	Empoli
X alemania-b	Bochum
X portugal	Ac.Coimbra
PIERDE italia	Parma
*/

set @vliga = 'belgica'
set @vequipo = 'Zulte Wareg'

select 
	@vequipo Equipo,
	sum(
		((
		case
			when NomEqL = @vequipo and ResultadoReal = '2' then 1
			when NomEqV = @vequipo and ResultadoReal = '1' then 1
			else 0
		end
		) * PagoResultado) - 1
    ) * 100 RendPorPerdidas,
	sum(
		(
		case
			when NomEqL = @vequipo and ResultadoReal = '2' then 1
			when NomEqV = @vequipo and ResultadoReal = '1' then 1
			else 0
		end
		) 
    )  CantPerdidas,
	sum(
		((
		case
			when NomEqL = @vequipo and ResultadoReal = '1' then 1
			when NomEqV = @vequipo and ResultadoReal = '2' then 1
			else 0
		end
		) * PagoResultado) - 1
    ) * 100 RendPorGanar,
	sum(
		(
		case
			when NomEqL = @vequipo and ResultadoReal = '1' then 1
			when NomEqV = @vequipo and ResultadoReal = '2' then 1
			else 0
		end
		) 
    )  CantVictorias,
	sum(
		((
		case
			when NomEqL = @vequipo and ResultadoReal = 'X' then 1
			when NomEqV = @vequipo and ResultadoReal = 'X' then 1
			else 0
		end
		) * PagoResultado) - 1
    ) * 100 RendPorEmpates,
	sum(
		(
		case
			when NomEqL = @vequipo and ResultadoReal = 'X' then 1
			when NomEqV = @vequipo and ResultadoReal = 'X' then 1
			else 0
		end
		) 
    )  CantEmpates
from v_result_pronosticos
where 
	fecha < '28/03/2015'
	and liga = @vliga
	and (
		NomEqL = @vequipo
		or NomEqV = @vequipo
	)
	


select 
	'Perdidas' DetallePerdidas,
	PagoPaginas_1,
	PagoPaginas_2,
	PagoPaginas_X,
	Fecha,
	NomEqL,
	NomEqV,
	Pronostico,
	Acierto,
	PronosticoDobles,
	AciertoDobles
from v_result_pronosticos
where 
	fecha < '28/03/2015'
	and liga = @vliga
	and (
		(NomEqL = @vequipo and ResultadoReal = '2')
		or (NomEqV = @vequipo and ResultadoReal = '1')
	)