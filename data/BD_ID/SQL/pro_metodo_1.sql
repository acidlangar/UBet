use id_bt
go

DECLARE @vIdRun INT
DECLARE @vIdFecha INT
DECLARE @vAcumAnterior INT
DECLARE @vIdFAnterior INT
DECLARE @vMaxRun INT
DECLARE @vAcumulado INT
DECLARE @vCodPronostico INT

begin 
	delete pronostico where cd_Metodo = 1

-- se procede hacer el calculo de los acumulados
	SET @vIdRun = 1 
	SET @vCodPronostico = 1	

	SELECT
		@vMaxRun = MAX(idRun)
	FROM RUN
	
	WHILE @vIdRun <= @vMaxRun
	BEGIN
		DECLARE c CURSOR FAST_FORWARD  FOR 
			SELECT
				idFecha,
				idRun
			FROM RUN
			WHERE
				idRun = @vIdRun
			ORDER BY
				idFecha
				
		OPEN c; 
		FETCH c INTO @vIdFecha, @vIdRun
	
		SET @vAcumAnterior = 0
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SET @vIdFAnterior = NULL
		
			SELECT @vIdFAnterior = MAX(idFecha)	
			FROM RUN
			WHERE 
				idRun = @vIdRun
				AND idFecha < @vIdFecha
		
			IF @vIdFAnterior IS NOT NULL
			BEGIN
				select
					@vAcumAnterior = acumulado
				FROM RUN
				where	
					idFecha = @vIdFAnterior
					AND idRun = @vIdRun

				IF @vAcumAnterior = 4
				BEGIN
					INSERT INTO [ID_BT].[dbo].[PRONOSTICO]
						   ([cd_Metodo]
						   ,[codigo]
						   ,[cd_liga]
						   ,[temporada]
						   ,[fecha_str]
						   ,[equipo_local]
						   ,[resultado]
						   ,[stake])
					 SELECT
						   1
						   ,@vCodPronostico
						   ,R.cd_liga
						   ,P.temporada
						   ,convert(varchar(10), R.fecha, 101) fecha_str
						   ,R.NomEqL
						   ,'1'
						   ,1
					FROM RUN R  -- sp_help partido
					JOIN PARTIDO P ON P.cd_liga = R.cd_liga
								AND P.Equipo_Local = R.NomEqL
								AND P.fecha = R.fecha
					WHERE
						R.idRun = @vIdRun
						AND R.idFecha = @vIdFecha

					SET @vCodPronostico = @vCodPronostico + 1
				END

				IF @vAcumAnterior = 5
				BEGIN
					INSERT INTO [ID_BT].[dbo].[PRONOSTICO]
						   ([cd_Metodo]
						   ,[codigo]
						   ,[cd_liga]
						   ,[temporada]
						   ,[fecha_str]
						   ,[equipo_local]
						   ,[resultado]
						   ,[stake])
					 SELECT
						   1
						   ,@vCodPronostico
						   ,R.cd_liga
						   ,P.temporada
						   ,convert(varchar(10), R.fecha, 101) fecha_str
						   ,R.NomEqL
						   ,'1'
						   ,2
					FROM RUN R  -- sp_help partido
					JOIN PARTIDO P ON P.cd_liga = R.cd_liga
								AND P.Equipo_Local = R.NomEqL
								AND P.fecha = R.fecha
					WHERE
						R.idRun = @vIdRun
						AND R.idFecha = @vIdFecha

					SET @vCodPronostico = @vCodPronostico + 1
				END

				IF @vAcumAnterior = 6
				BEGIN
					INSERT INTO [ID_BT].[dbo].[PRONOSTICO]
						   ([cd_Metodo]
						   ,[codigo]
						   ,[cd_liga]
						   ,[temporada]
						   ,[fecha_str]
						   ,[equipo_local]
						   ,[resultado]
						   ,[stake])
					 SELECT
						   1
						   ,@vCodPronostico
						   ,R.cd_liga
						   ,P.temporada
						   ,convert(varchar(10), R.fecha, 101) fecha_str
						   ,R.NomEqL
						   ,'1'
						   ,5
					FROM RUN R  -- sp_help partido
					JOIN PARTIDO P ON P.cd_liga = R.cd_liga
								AND P.Equipo_Local = R.NomEqL
								AND P.fecha = R.fecha
					WHERE
						R.idRun = @vIdRun
						AND R.idFecha = @vIdFecha

					SET @vCodPronostico = @vCodPronostico + 1
				END

				IF @vAcumAnterior = 7
				BEGIN
					INSERT INTO [ID_BT].[dbo].[PRONOSTICO]
						   ([cd_Metodo]
						   ,[codigo]
						   ,[cd_liga]
						   ,[temporada]
						   ,[fecha_str]
						   ,[equipo_local]
						   ,[resultado]
						   ,[stake])
					 SELECT
						   1
						   ,@vCodPronostico
						   ,R.cd_liga
						   ,P.temporada
						   ,convert(varchar(10), R.fecha, 101) fecha_str
						   ,R.NomEqL
						   ,'1'
						   ,10
					FROM RUN R  -- sp_help partido
					JOIN PARTIDO P ON P.cd_liga = R.cd_liga
								AND P.Equipo_Local = R.NomEqL
								AND P.fecha = R.fecha
					WHERE
						R.idRun = @vIdRun
						AND R.idFecha = @vIdFecha

					SET @vCodPronostico = @vCodPronostico + 1
				END
			END		
			
			FETCH c INTO @vIdFecha, @vIdRun
		END
		
		CLOSE c;  
		DEALLOCATE c;  

	
		SET @vIdRun = @vIdRun + 1 
	END
end