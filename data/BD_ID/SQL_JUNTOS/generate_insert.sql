select 
	'INSERT INTO [ID_BT].[dbo].[PARTIDO]([cd_liga],[temporada],[fecha],[equipo_local],[equipo_visitante],[goles_local],[goles_visitante],[resultado],[cuota1],[coutaX],[cuota2],[dif_goles_local],[dif_goles_visitante],[diferencia]) VALUES ('
			+ convert(varchar,cd_liga)
           +','''+temporada
           +''',convert(datetime,'''+convert(varchar,fecha,113)+''',113) '
           +','''+equipo_local
           +''','''+equipo_visitante
           +''','+convert(varchar,goles_local)
           +','+convert(varchar,goles_visitante)
           +','''+resultado
           +''','+convert(varchar,cuota1)
           +','+convert(varchar,coutaX)
           +','+convert(varchar,cuota2)
           +','+convert(varchar,dif_goles_local)
           +','+convert(varchar,dif_goles_visitante)
           +','+convert(varchar,diferencia)
			+')'
from partido
