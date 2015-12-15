USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ins_lg]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ins_lg]
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
CREATE PROCEDURE ins_lg(
	@pLeague VARCHAR(50),
	@id INT OUTPUT
)

AS
BEGIN
	SET NOCOUNT ON;
	
	IF(EXISTS(
		SELECT CODIGO FROM LIGA WHERE DESCRIPCION = @pLeague
	)) 
	BEGIN

		SELECT @id = CODIGO FROM LIGA WHERE DESCRIPCION = @pLeague

	END
	ELSE
	BEGIN
		SELECT @id = COUNT(1) + 1
		FROM LIGA

		INSERT INTO [ID_BT].[dbo].[LIGA]
			   ([codigo]
			   ,[descripcion])
		 VALUES
			   (@id
			   ,@pLeague)

	END
	

END
GO
