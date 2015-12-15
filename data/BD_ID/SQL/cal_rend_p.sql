set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Isaac Ortiz
-- Create date: 24/07/2015
-- Description:	Llena la tabla de rendimiento en base a los metodos configurados
-- y en base a la vista de los resultados
-- =============================================
CREATE PROCEDURE [dbo].[cal_rend_p] 
AS
BEGIN
	SET NOCOUNT ON;

	TRUNCATE TABLE RENDIMIENTO	

   INSERT INTO RENDIMIENTO
	SELECT 
		CM.Metodo MetodoBasico,
		CM.Metodo Metodo,
		(1.00*SUM(
			CASE
				WHEN V.ResultadoReal = CM.Pronostico THEN V.PagoResultado-1 
				ELSE -1 
			END
                )) / (1.00*Count(1)) Rendimiento,	
		( 
			(1.00*SUM(CASE
				WHEN V.ResultadoReal = CM.Pronostico THEN 1 
				ELSE 0
			END)) * 100 
		) / (Count(1) * 1.00) PorcAciertos,
		Count(1) CantTotalJuegos
	FROM CONF_METODO CM, V_RESULT_PRONOSTICOS V  
	WHERE
		V.PagoPaginas_1 > 0 
		AND V.PagoPaginas_X > 0 
		AND V.PagoPaginas_2 > 0 
		AND ISNULL(CM.ProbLimInfL,0) < 1/V.PagoPaginas_1
		AND 1/V.PagoPaginas_1 <= ISNULL(CM.ProbLimSupL,2.0)
                AND ISNULL(CM.ProbLimInfX,0) < 1/V.PagoPaginas_X
		AND 1/V.PagoPaginas_X <= ISNULL(CM.ProbLimSupX,2.0)
                AND ISNULL(CM.ProbLimInfV,0) < 1/V.PagoPaginas_2
		AND 1/V.PagoPaginas_2 <= ISNULL(CM.ProbLimSupV,2.0)
	GROUP BY 
		CM.Metodo

   INSERT INTO RENDIMIENTO
	SELECT 
		CM.Metodo MetodoBasico,
		CM.Metodo + '-' + V.liga Metodo,
		(1.00*SUM(
			CASE
				WHEN V.ResultadoReal = CM.Pronostico THEN V.PagoResultado-1 
				ELSE -1 
			END
                )) / (1.00*Count(1)) Rendimiento,	
		( 
			(1.00*SUM(CASE
				WHEN V.ResultadoReal = CM.Pronostico THEN 1 
				ELSE 0
			END)) * 100 
		) / (Count(1) * 1.00) PorcAciertos,
		Count(1) CantTotalJuegos
	FROM CONF_METODO CM, V_RESULT_PRONOSTICOS V  
	WHERE
		V.PagoPaginas_1 > 0 
		AND V.PagoPaginas_X > 0 
		AND V.PagoPaginas_2 > 0 
		AND ISNULL(CM.ProbLimInfL,0) < 1/V.PagoPaginas_1
		AND 1/V.PagoPaginas_1 <= ISNULL(CM.ProbLimSupL,2.0)
                AND ISNULL(CM.ProbLimInfX,0) < 1/V.PagoPaginas_X
		AND 1/V.PagoPaginas_X <= ISNULL(CM.ProbLimSupX,2.0)
                AND ISNULL(CM.ProbLimInfV,0) < 1/V.PagoPaginas_2
		AND 1/V.PagoPaginas_2 <= ISNULL(CM.ProbLimSupV,2.0)
	GROUP BY 
		CM.Metodo, V.liga



	--SELECT * FROM RENDIMIENTO	
END

