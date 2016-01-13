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

-- CUERPO DEL PROCEDIMIENTO
-- Hacemos un cursor sobre todos los partidos del tipo a tomar en cuenta de la BD ordenamos desde mas viejo a mas nuevo

-- Para cada partido llenamos la tabla REP_RENDIMIENTO_VS_DESVIACION
      -- Realizamos otro cursor, que tega los Ultimos 100 partidos, anteriores al partido actual, esta vez ordenados de más reciente a mas viejo
	  
		  -- Con un contador, controlamos si debemos salir del cursor o no.
		  
		  -- Hacemos los insert en una tabla temporal, del partido.
	  
	  -- Con la historia reciente del partido en la tabla temporal, procedemos a:
	  
	  -- Calcular las cuotas promedios y los porcentajes promedios
	  
	  -- Eliminamos el overround
	  
	  -- Calculamos la Expectativa de Resultados, Y la realidad de Resultados.
	  
	  -- Calculamos las desviaciones. 
	  
	  -- Insertamos en la tabla REP_RENDIMIENTO_VS_DESVIACION, las cuotas promedios, los porcentajes, las expectativas, las realidades, y las desviaciones
	  
-- Luego de calculada la tabla, llamamos al procedimiento de la consuta de los resultados cons_rendimiento_vs_desviacion	  

END