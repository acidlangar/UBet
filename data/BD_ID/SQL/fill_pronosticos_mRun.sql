idFecha = 1
-- para cada vfecha en cursor de dias, crear los runs
		
	idFecha = idFecha + 1
	

-- Iterar desde i = @gWait + 1 hasta idFecha


-------------------------------
-------------------------------

USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cal_runs]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cal_runs]
GO 

set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[cal_runs] 
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @LimInf NUMERIC(5,3) 
	DECLARE @LinSup NUMERIC(5,3) 
	DECLARE @vFechaInicial VARCHAR(10)
	DECLARE @vFechaCursor VARCHAR(10)
	DECLARE @vIdFecha INT
	
	SET @LimInf = 50
	SET @LinSup = 60
	SET @vIdFecha = 1
	

   TRUNCATE TABLE RUN	
   
	select 
		@vFechaInicial = min(sq.fecha)
	from 
	(
	select temporada, cd_liga, min(fecha) fecha
	from partido
	where
		(temporada = '2014' and cd_liga in (1,3,6))
		or	(temporada = '2014/2015' and cd_liga in (2,4,5,7,8,9))
	group by temporada, cd_liga
	) SQ

	-- crear cursor de las fecha 
	DECLARE cFechas CURSOR FAST_FORWARD  FOR 
		select distinct 
			rank() over(order by fecha) rank, 
			convert(VARCHAR(10), fecha,101)
		from partido
		where
			fecha >= @vFechaInicial
		order by fecha
		
	OPEN cFechas; 
	FETCH cFechas INTO @vIdFecha, @vFechaCursor
	
	WHILE @@FETCH_STATUS = 0 BEGIN
	
		INSERT INTO [ID_BT].[dbo].[RUN]
           ([idFecha]
           ,[idRun]
           ,[Fecha]
           ,[cd_liga]
           ,[NomEqL]
           ,[Acierto]
           ,[Acumulado])
     	SELECT 
			@vIdFecha,
			rank() over (order by fecha) as rank,
			fecha,
			cd_liga,
			equipo_local,
			case
				when goles_local > goles_visitante then 1 
				else 0
			end,
			NULL 
		FROM PARTIDO 
			1/cuota1 >= LimInf
			and 1/cuota1 < LinSup
			and convert(VARCHAR(10), fecha,101) = vfecha
			(
				temporada = '2014' and cd_liga in (1,3,6))
				or	(temporada = '2014/2015' and cd_liga in (2,4,5,7,8,9)
			)
		order by
			fecha
		
	
		FETCH cFechas INTO @vIdFecha, @vFechaCursor
	END
	
	CLOSE cFechas;  
	DEALLOCATE cFechas;  

  
END

