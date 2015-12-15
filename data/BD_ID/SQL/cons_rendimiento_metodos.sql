USE [ID_BT]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cons_rendimiento_metodos]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cons_rendimiento_metodos]
GO

-- =============================================
-- Author:		Isaac Ortiz
-- Create date: 20/07/2015
-- Description:	Realiza la consulta sobre la tabla de rendimiento
-- ordenando y proyectando a cien juegos 
-- =============================================
CREATE PROCEDURE cons_rendimiento_metodos
AS
BEGIN
	SET NOCOUNT ON;

	SELECT 
		R.metodo,
		C.Descripcion Descripcion,
		R.rendimiento,
		100 + (R.rendimiento * 100) Proyeccion100Juegos,
		R.PorcAciertos,
		R.CantTotalJuegos
	FROM RENDIMIENTO R	
	JOIN CONF_METODO C ON C.Metodo = R.MetodoBasico
	WHERE
		R.CantTotalJuegos > 119
	ORDER BY 
		R.rendimiento desc
END
GO
