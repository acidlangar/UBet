/*
select PagoPaginas_1, PagoPaginas_X, ResultadoReal, Liga, Fecha, NomEqL, NomEqV
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'

-- promedio de pagos a favorito y empate
select sum(PagoPaginas_1)/count(1) PromedioPagos_1, sum(PagoPaginas_X)/count(1) PromedioPagos_X
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'


-- agrupado por fechas tomar los cuatro que tengan mejor pago
select PagoPaginas_1, PagoPaginas_X, ResultadoReal, Liga, Fecha, NomEqL, NomEqV
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'
order by fecha, PagoPaginas_1 desc

-- identificamos las fechas donde hay cuatro o mas partidos
select Fecha, count(1) CantJuegos
from v_result_pronosticos
where 
	pagoPaginas_2 >= 10
	and fecha < '28/02/2015'
group by Fecha
having count(1) >= 4
*/
-- de las 24 fechas... debemos seleccionar los primeros cuatro, o en su 
-- defecto vamos a armar grupos de cuatro.
begin

declare @vCFecha smalldatetime
declare @vCCant int

declare @vLiga [varchar](50)
declare @vFecha smalldatetime
declare @vNomEqL [varchar](50) 
declare @vNomEqV [varchar](50) 
declare @vPagoPaginas_1 [decimal](18, 3) 
declare @vPagoPaginas_X [decimal](18, 3) 
declare @vResultadoReal [varchar](1) 

declare @cantParleyFavoritos int
declare @cantParleyUnEmpate int
declare @ningunParley int

set @cantParleyFavoritos = 0
set @cantParleyUnEmpate = 0
set @ningunParley = 0

declare @cantEmpates int
declare @cantSorpresas int
declare @contJuegos int

declare cJuegosPorFechas cursor read_only fast_forward for
	select Fecha, count(1) CantJuegos
	from v_result_pronosticos
	where 
		pagoPaginas_2 >= 10
		and fecha < '28/02/2015'
	group by Fecha
	having count(1) >= 4

	OPEN cJuegosPorFechas 
    FETCH cJuegosPorFechas INTO @vCFecha, @vCCant

	WHILE @@FETCH_STATUS = 0 
	BEGIN

		-- procedemos hacer revision interna de los grupos de cuatro
		declare CGrupo CURSOR read_only fast_forward for
			select top 4
				PagoPaginas_1, PagoPaginas_X, ResultadoReal, Liga, Fecha, NomEqL, NomEqV
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha = @vCFecha
			order by fecha, PagoPaginas_1 asc


		open CGrupo
		fetch CGrupo into @vPagoPaginas_1, @vPagoPaginas_X, @vResultadoReal, @vLiga, @vFecha, @vNomEqL, @vNomEqV

		set @cantEmpates = 0
		set @cantSorpresas = 0
		set @contJuegos = 0

		while @@fetch_status = 0
		begin
			set @contJuegos = @contJuegos + 1 			

			if	@vResultadoReal = 'X' begin 
				set @cantEmpates = @cantEmpates + 1
			end

			if	@vResultadoReal = '2' begin 
				set @cantSorpresas = @cantSorpresas + 1
			end
			
			if @contJuegos = 4 begin

				if (@cantSorpresas > 0) or (@cantEmpates > 1) begin
					select 'fallo' result
					set @ningunParley = @ningunParley + 1
				end else begin

					if(@cantEmpates = 1) begin
						select 'acierto parley empates' result
						set @cantParleyUnEmpate = @cantParleyUnEmpate + 1
					end else begin
						select 'acierto parley favoritos' result
						set @cantParleyFavoritos = @cantParleyFavoritos + 1
					end 
					
				end 

			select top 4
				PagoPaginas_1, PagoPaginas_X, ResultadoReal, Liga, Fecha, NomEqL, NomEqV
			from v_result_pronosticos
			where 
				pagoPaginas_2 >= 10
				and fecha = @vCFecha
			order by fecha, PagoPaginas_1 asc

			end 
			fetch CGrupo into @vPagoPaginas_1, @vPagoPaginas_X, @vResultadoReal, @vLiga, @vFecha, @vNomEqL, @vNomEqV
		end

		close CGrupo
		deallocate CGrupo
		FETCH cJuegosPorFechas INTO @vCFecha, @vCCant
	END

	CLOSE cJuegosPorFechas
	DEALLOCATE cJuegosPorFechas

	select @cantParleyFavoritos cantParleyFavoritos, @cantParleyUnEmpate cantParleyUnEmpate, @ningunParley ningunParley

end



