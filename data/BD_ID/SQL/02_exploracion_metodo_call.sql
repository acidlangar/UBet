/*DECLARE @RC int
DECLARE @pFecha varchar(10)
DECLARE @pLiga varchar(50)
DECLARE @pLimInfPPg decimal(18,0)
DECLARE @pLimSupPPg decimal(18,0)
DECLARE @pRendObj int
DECLARE @pCantMinJuegosRespaldo decimal(18,0)

-- TODO: Set parameter values here.
-- select distinct liga from pronostico_m order by 1 desc
/*
2015-03-25 00:00:00
2015-03-21 00:00:00
2015-03-18 00:00:00
2015-03-14 00:00:00
2015-03-07 00:00:00
2015-03-04 00:00:00
2015-02-28 00:00:00
2015-02-25 00:00:00
2015-02-21 00:00:00
' 2015-02-14 00:00:00
*/

EXECUTE @RC = [ID_BT].[dbo].[cal_result_mpf_x_fecha] 
   @pFecha = '14/02/2015'
  ,@pLimInfPPg = 1.3
  ,@pLimSupPPg = 1.99
  ,@pRendObj = 5
  ,@pCantMinJuegosRespaldo = 3



*/
-------------------------------

DECLARE @RC int
DECLARE @pFecha varchar(10)
DECLARE @pLimInfPPg decimal(18,3)
DECLARE @pLimSupPPg decimal(18,3)

/*
2015-03-25 00:00:00
2015-03-21 00:00:00  2015-03-18 00:00:00  2015-03-14 00:00:00
2015-03-07 00:00:00  ' 2015-03-04 00:00:00  ' 2015-02-28 00:00:00
' 2015-02-25 00:00:00  ' 2015-02-21 00:00:00   ' 2015-02-14 00:00:00
*/

EXECUTE @RC = [ID_BT].[dbo].[cal_result_acpf_x_fecha] 
   @pFecha = '25/03/2015'
  ,@pLimInfPPg = 1.3
  ,@pLimSupPPg = 1.99

select * from result_acpf order by porcresultante

/*
select pagopaginas, acierto from pronostico_m
	where 
		fecha = '25/02/2015'
		and pagopaginas >= 1.3
		and pagopaginas <= 1.99
		and porcconfianza >= 70
*/