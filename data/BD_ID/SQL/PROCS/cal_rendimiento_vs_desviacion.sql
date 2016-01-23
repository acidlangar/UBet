USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cal_rendimiento_vs_desviacion]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cal_rendimiento_vs_desviacion]
GO

-- =============================================
-- Author:		Isaac Ortiz
-- Create date: 13/01/2016
-- Description:	Dados la cantidad de partidos de la historia a tomar en cuenta, la desviación a evaluar, y el tipo de partido, calcula el rendimiento
-- =============================================
CREATE PROCEDURE [dbo].[cal_rendimiento_vs_desviacion] 
(
	@pCantHistoria INT,
	@pTipoPartido INT,
	@pDesviacion DECIMAL(18,3),
	@pTipoResultado VARCHAR(1)
)
AS
BEGIN

-- PARAM ENTRADA:
--    @CantHistoria
--    @TipoPartido
--    @DesviacionRegistrada
--    @TipoResultado

-- PARAM SALIDA:
--     TotalRendimiento
--     CantPartidos   Cantidad de Partidos en la BD en los que ocurrio el caso
--     CantResultadoEsperado
--     CantResultadoContrario
--     Acierto
--     CuotaPromedio
--     Ganancias
--     Perdidas 

declare @vcd_liga	int
declare @vfecha	datetime
declare @vequipo_local	varchar(50)
declare @vresultado	nchar
declare @vcuota1	float
declare @vcuotaX	float
declare @vcuota2	float

declare @vcd_liga_h	int
declare @vfecha_h	datetime
declare @vequipo_local_h	varchar(50)
declare @vresultado_h	nchar
declare @vcuota1_h	float
declare @vcuotaX_h	float
declare @vcuota2_h	float

declare @vContador int
declare @vLlegueHist int


DECLARE @vPromCuota1 NUMERIC(18,3)
DECLARE @vPromCuotaX NUMERIC(18,3)
DECLARE @vPromCuota2 NUMERIC(18,3)
DECLARE @vProb1 NUMERIC(18,3)
DECLARE @vProbX NUMERIC(18,3)
DECLARE @vProb2 NUMERIC(18,3)
DECLARE @vRealidad1 INT
DECLARE @vRealidadX INT
DECLARE @vRealidad2 INT
DECLARE @vOverround NUMERIC(18,3)
DECLARE @vExpectativa1 NUMERIC(18,3)
DECLARE @vExpectativa2 NUMERIC(18,3)
DECLARE @vExpectativaX NUMERIC(18,3)
DECLARE @vDesv1 NUMERIC(18,3)
DECLARE @vDesv2 NUMERIC(18,3)
DECLARE @vDesvX NUMERIC(18,3)

CREATE TABLE #TEMP_HIST(
	cd_liga	int,
	fecha	datetime,
	equipo_local	varchar(50),
	resultado	nchar,
	cuota1	float,
	cuotaX	float,
	cuota2	float
)

DELETE REP_RENDIMIENTO_VS_DESVIACION

-- CUERPO DEL PROCEDIMIENTO
-- Hacemos un cursor sobre todos los partidos del tipo a tomar en cuenta de la BD ordenamos desde mas viejo a mas nuevo
DECLARE cPartidosTipo CURSOR FOR
	SELECT 
		cd_liga,
        fecha,
		equipo_local,
        resultado,
        cuota1,
        coutaX cuotaX,
        cuota2
	FROM PARTIDO
	WHERE
		tipopartido = @pTipoPartido
	ORDER BY fecha

open cPartidosTipo
FETCH FROM cPartidosTipo INTO @vcd_liga, @vfecha, @vequipo_local, @vresultado, @vcuota1, @vcuotaX, @vcuota2

WHILE @@FETCH_STATUS = 0 
BEGIN
	-- Para cada partido llenaremos la tabla REP_RENDIMIENTO_VS_DESVIACION
           SET @vLlegueHist = 0  
			DELETE #TEMP_HIST


		  -- Realizamos otro cursor, que tega los Ultimos 100 partidos, anteriores al partido actual, esta vez ordenados de más reciente a mas viejo
			DECLARE cHistoria CURSOR FOR
				SELECT TOP 100
					cd_liga,
					fecha,
					equipo_local,
					resultado,
					cuota1,
					coutaX cuotaX,
					cuota2
				FROM PARTIDO
				WHERE
					tipopartido = @pTipoPartido
                    AND fecha < DATEADD(MM, 105, @vfecha)
				ORDER BY fecha DESC		  

			OPEN cHistoria
            FETCH FROM cHistoria INTO @vcd_liga_h, @vfecha_h, @vequipo_local_h, @vresultado_h, @vcuota1_h, @vcuotaX_h, @vcuota2_h

			SET @vContador = 0

			WHILE @@FETCH_STATUS = 0
			BEGIN

			    -- Con un contador, controlamos si insertaremos o no en la tabla temporal.
				IF @vcontador < @pCantHistoria 
				BEGIN	
					-- Hacemos los insert en una tabla temporal, del partido.
					INSERT INTO #TEMP_HIST (
						cd_liga,
						fecha,
						equipo_local,
						resultado,
						cuota1,
						cuotaX,
						cuota2
					) VALUES (
						@vcd_liga_h, 
						@vfecha_h, 
						@vequipo_local_h, 
						@vresultado_h, 
						@vcuota1_h, 
						@vcuotaX_h, 
						@vcuota2_h
					)				

				END
				ELSE 
				BEGIN
					SET @vLlegueHist = 1

				END

				SET @vContador = @vContador + 1

				FETCH cHistoria INTO @vcd_liga_h, @vfecha_h, @vequipo_local_h, @vresultado_h, @vcuota1_h, @vcuotaX_h, @vcuota2_h
			END  -- END CICLO PARA HISTORIA

			CLOSE cHistoria
			DEALLOCATE cHistoria

		    IF @vLlegueHist = 1 
            begin
				  -- Con la historia reciente del partido en la tabla temporal, procedemos a:
				
				  -- Calcular las cuotas promedios y los porcentajes promedios
				  SELECT 
						@vPromCuota1 = AVG(cuota1) ,
						@vPromCuotaX = AVG(cuotax) ,
						@vPromCuota2 = AVG(cuota2) ,
						@vProb1 = 1/AVG(cuota1) ,
						@vProbX = 1/AVG(cuotaX) ,
						@vProb2 = 1/AVG(cuota2) ,
						@vRealidad1 = (SUM(
							CASE
								WHEN Resultado = '1' THEN 1
								ELSE 0
							END
						)),
						@vRealidadX = (SUM(
							CASE
								WHEN Resultado = 'X' THEN 1
								ELSE 0
							END
						)),
						@vRealidad2 = (SUM(
							CASE
								WHEN Resultado = '2' THEN 1
								ELSE 0
							END
						))
                  FROM #TEMP_HIST
                  
				  -- Eliminamos el overround
				  SET @vOverround =	(@vProb1	+ @vProbX + @vProb2	) - 1		  

				  SET @vProb1 = @vProb1 - (@vOverround/3)
				  SET	@vProbX = @vProbX - (@vOverround/3)
				  SET 	@vProb2 = @vProb2 - (@vOverround/3)

				  -- Calculamos la Expectativa de Resultados, Y la realidad de Resultados.
				  SET @vExpectativa1 = @vProb1 * @pCantHistoria
				  SET @vExpectativaX = @vProbX * @pCantHistoria	
				  SET @vExpectativa2 = @vProb2 * @pCantHistoria

				  -- Calculamos las desviaciones. 
				  SET @vDesv1 = @vRealidad1 - @vExpectativa1 
				  SET @vDesvX = @vRealidadX - @vExpectativaX 
				  SET @vDesv2 = @vRealidad2 - @vExpectativa2  

				  -- Insertamos en la tabla REP_RENDIMIENTO_VS_DESVIACION, las cuotas promedios, los porcentajes, las expectativas, las realidades, y las desviaciones
	              INSERT INTO [ID_BT].[dbo].[REP_RENDIMIENTO_VS_DESVIACION]
					   ([cd_liga]
					   ,[fecha]
					   ,[equipo_local]
					   ,[Rendimiento1]
					   ,[RendimientoX]
					   ,[Rendimiento2]
					   ,[DesvCantJuego1]
					   ,[DesvCantJuegoX]
					   ,[DesvCantJuego2]
                       ,[DesvPorcentual1]
					   ,[DesvPorcentualX]
					   ,[DesvPorcentual2]
					   ,[Expectativa1]
					   ,[ExpectativaX]
					   ,[Expectativa2]
					   ,[Realidad1]
					   ,[RealidadX]
					   ,[Realidad2])
				 VALUES
					   (@vcd_liga 
					   ,@vfecha
					   ,@vequipo_local
					   ,(CASE WHEN @vresultado = '1' THEN @vcuota1 - 1 ELSE -1 END)
					   ,(CASE WHEN @vresultado = 'X' THEN @vcuotaX - 1 ELSE -1 END)
					   ,(CASE WHEN @vresultado = '2' THEN @vcuota2 - 1 ELSE -1 END)
					   ,@vDesv1
					   ,@vDesvX
					   ,@vDesv2
                       ,((@vRealidad1*100)/@vExpectativa1)-100
					   ,((@vRealidadX*100)/@vExpectativaX)-100
					   ,((@vRealidad2*100)/@vExpectativa2)-100
                       ,@vExpectativa1
					   ,@vExpectativaX
					   ,@vExpectativa2
					   ,@vRealidad1
					   ,@vRealidadX
					   ,@vRealidad2) 

            end -- fin del if de la llegada a la historia
	FETCH cPartidosTipo INTO @vcd_liga, @vfecha, @vequipo_local, @vresultado, @vcuota1, @vcuotaX, @vcuota2
END  -- END CICLO POR PARTIDOS TIPOS

CLOSE cPartidosTipo
DEALLOCATE cPartidosTipo

-- Luego de calculada la tabla, llamamos al procedimiento de la consuta de los resultados cons_rendimiento_vs_desviacion	  

END