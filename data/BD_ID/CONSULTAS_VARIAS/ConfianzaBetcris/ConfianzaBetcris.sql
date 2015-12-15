USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cons_confianza_casas]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cons_confianza_casas]
go

CREATE PROCEDURE [dbo].[cons_confianza_casas] 
(
	@pLiga varchar(50)
)
AS
BEGIN
	SET NOCOUNT ON;
	
	DECLARE @cnt INT;
	DECLARE @LimSup decimal(18,3);
	DECLARE @LimInf decimal(18,3);

set @cnt = 1
		---Creas la tabla temporal
		CREATE TABLE #Tabla_Temp (
			Inversa varchar(100), 
			Confianza decimal(18,3), 
			Aciertos int,
			Fallos int,
			Total int)



	WHILE @cnt <= 100
	BEGIN
	   set @LimSup = @cnt
	    set @LimInf = @cnt - 1


		---Insertas datos en esta
		INSERT INTO #Tabla_Temp (Inversa, 
			Confianza, 
			Aciertos,
			Fallos,
			Total)
	select 
		'Mayor igual ' + convert(varchar(18), @LimInf) + ' y menor ' + convert(varchar(18), @LimSup) Inversa,
		sum(
			case
				when ResultadoReal = '1' then 1
				else 0
			end 
		) * 100 / count(1) Confianza,
		sum(
			case
				when ResultadoReal = '1' then 1
				else 0
			end 
		) Aciertos,
		sum(
			case
				when ResultadoReal != '1' then 1
				else 0
			end 
		) Fallos,
		count(1) Total
	from v_result_pronosticos
	where
		PagoPaginas_1 > 0
		and 1/PagoPaginas_1 >= (@LimInf/100)
		and 1/PagoPaginas_1 < (@LimSup/100)

		
	   SET @cnt = @cnt + 1;
	END;

---Devuelves los datos de la tabla temporal
		SELECT * FROM #Tabla_Temp

		---Borras la tabla temporal, si queres aca, sino lo tenes que hacer en otro SP
		DROP TABLE #Tabla_Temp

END

/*
select 
	'Betcris 90 a 100' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.9

union 

select 
	'Betcris 80 a 89.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.8
	and 1/PagoPaginas_1 < 0.9

union 

select 
	'Betcris 70 a 79.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.7
	and 1/PagoPaginas_1 < 0.8

union 

select 
	'Betcris 60 a 79.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.6
	and 1/PagoPaginas_1 < 0.7

union 

select 
	'Betcris 50 a 59.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.5
	and 1/PagoPaginas_1 < 0.6

union 

select 
	'Betcris 40 a 49.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.4
	and 1/PagoPaginas_1 < 0.5

union 

select 
	'Betcris 30 a 39.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.3
	and 1/PagoPaginas_1 < 0.4

union 

select 
	'Betcris 20 a 29.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.2
	and 1/PagoPaginas_1 < 0.3

union 

select 
	'Betcris 10 a 19.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 >= 0.1
	and 1/PagoPaginas_1 < 0.2

union 

select 
	'Betcris 0 a 9.99' Inversa,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) Aciertos,
	sum(
		case
			when ResultadoReal != '1' then 1
			else 0
		end 
	) Fallos,
	count(1) Total,
	sum(
		case
			when ResultadoReal = '1' then 1
			else 0
		end 
	) * 100 / count(1) PorcentajeConfianza
from v_result_pronosticos
where
	PagoPaginas_1 > 0
	and 1/PagoPaginas_1 < 0.1

*/