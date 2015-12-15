USE [ID_BT]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cal_result_mpf_x_fecha]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cal_result_mpf_x_fecha]
GO

-- =============================================
-- Author:		Isaac Ortiz
-- Create date: 01/05/2015
-- Description:	Procedimiento que para una fecha calcula el resultado obtenido
-- de aplicar el metodo. Se guarda el resultado del metodo en la tabla RESULT_MPF
-- =============================================
CREATE PROCEDURE cal_result_mpf_x_fecha 
(
	@pFecha varchar(10), -- fecha a la que se desea calcular el resultado
	@pLimInfPPg decimal(18,3), -- Limite inferior permitido para seleccionar juegos
	@pLimSupPPg decimal(18,3), -- Limite superior permitido para seleccionar juegos
	@pRendObj int, -- Porcentaje de rendimiento objetivo con respecto al disponible
	@pCantMinJuegosRespaldo int-- Cantidad de juegos minimos de respaldo para continuar jugando despues lograr el objetivo
)
AS
BEGIN
	SET NOCOUNT ON;

	select sum((pagopaginas*acierto) - 1) * 100 ResultGeneral
	from pronostico_m
	where 
		fecha = @pFecha 
		and pagopaginas >= @pLimInfPPg 
		and pagopaginas <= @pLimSupPPg 
		and porcconfianza >= 70


	select pagopaginas, acierto from pronostico_m
	where 
		fecha = @pFecha 
		and pagopaginas >= @pLimInfPPg 
		and pagopaginas <= @pLimSupPPg 
		and porcconfianza >= 70



	--SELECT * from result_mpf
END
GO
