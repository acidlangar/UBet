USE [ID_BT]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cal_result_acpf_x_fecha]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[cal_result_acpf_x_fecha]
GO

-- =============================================
-- Author:		Isaac Ortiz
-- Create date: 01/05/2015
-- Description:	Procedimiento que para una fecha calcula el resultado obtenido
-- de aplicar el metodo. Se guarda el resultado del metodo en la tabla RESULT_MPF
-- =============================================
CREATE PROCEDURE cal_result_acpf_x_fecha 
(
	@pFecha varchar(10), -- fecha a la que se desea calcular el resultado
	@pLimInfPPg decimal(18,3), -- Limite inferior permitido para seleccionar juegos
	@pLimSupPPg decimal(18,3)
)
AS
BEGIN
	SET NOCOUNT ON;
	
	delete [ID_BT].[dbo].[RESULT_ACPF] where fecha = @pFecha 

	if (
		exists(
			select top 1 1
			from pronostico_m
			where 
			fecha = @pFecha 
			and pagopaginas >= @pLimInfPPg 
			and pagopaginas <= @pLimSupPPg 
			and porcconfianza >= 70
		) 
	)
begin
	INSERT INTO [ID_BT].[dbo].[RESULT_ACPF]
           ([Fecha]
           ,[PorcResultante]
           ,[CantTotaJuegos]
           ,[CantTotalDesaciertos]
           ,[PorcDesaciertos])
    -- VALUES
	select 
		@pFecha Fecha,
		sum((pagopaginas*acierto) - 1) * 100 PorcResultante,
		count(1) CantTotaJuegos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) CantTotalDesaciertos,
		sum(case
				when acierto = 1 then 0
				else 1 
			end) * 100 / count(1) PorcDesaciertos
	from pronostico_m
	where 
		fecha = @pFecha 
		and pagopaginas >= @pLimInfPPg 
		and pagopaginas <= @pLimSupPPg 
		and porcconfianza >= 70
end 
else 
begin
	INSERT INTO [ID_BT].[dbo].[RESULT_ACPF]
           ([Fecha]
           ,[PorcResultante]
           ,[CantTotaJuegos]
           ,[CantTotalDesaciertos]
           ,[PorcDesaciertos])
    VALUES (
		@pFecha 
		, 0
		, 0
		, 0
		, 0
	)

end


	select * from result_acpf where fecha = @pFecha 

/*
	select pagopaginas, acierto from pronostico_m
	where 
		fecha = @pFecha 
		and pagopaginas >= @pLimInfPPg 
		and pagopaginas <= @pLimSupPPg 
		and porcconfianza >= 70
*/


	--SELECT * from result_mpf
END
GO
