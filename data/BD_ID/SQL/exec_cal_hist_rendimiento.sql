DECLARE @RC int
DECLARE @pCd_Lig int
DECLARE @pCd_Met int

-- TODO: Set parameter values here.

EXECUTE @RC = [ID_BT].[dbo].[cal_hist_rendimiento] 
   @pCd_Lig = null
  ,@pCd_Met = 1

SELECT * FROM HIST_RENDIMIENTO