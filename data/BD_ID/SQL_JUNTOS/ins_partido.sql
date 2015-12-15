USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ins_partido]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ins_partido]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE ins_partido(
	@pCountry VARCHAR(25), 
	@pLeague VARCHAR(50),
	@pTemp VARCHAR(25), 
	@pFecha VARCHAR(19), -- 'DD/MM/YYYY HH:MM:SS'	
	@pEqL VARCHAR(50),
	@pEqV VARCHAR(50),
	@pGL INT,
	@pGV INT,
	@pRStr VARCHAR(10),
	@pC1 FLOAT,
	@pCX FLOAT,
	@pC2 FLOAT
)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	
	DECLARE @id INT


	EXEC [ID_BT].[dbo].[ins_lg] @pLeague, @id OUT


	INSERT INTO [ID_BT].[dbo].[PARTIDO]
           ([cd_liga]
		   ,[temporada]
           ,[fecha]
           ,[equipo_local]
           ,[equipo_visitante]
           ,[goles_local]
           ,[goles_visitante]
           ,[resultado]
           ,[cuota1]
           ,[coutaX]
           ,[cuota2]
           ,[dif_goles_local]
           ,[dif_goles_visitante]
		   ,[diferencia])
     VALUES
           (@id
		   ,@pTemp
           ,CONVERT(SMALLDATETIME,@pFecha,120)
           ,@pEqL
           ,@pEqV
           ,@pGL
           ,@pGV
           ,@pRStr
           ,@pC1
           ,@pCX
           ,@pC2
           ,-1
           ,-1
		   ,-1)


END
GO
