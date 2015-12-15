use [ID_BT]
go

sp_help pronostico_m

INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto],[PronosticoDobles],[PorcConfianzaDobles],[PagoPaginasDobles],[PagoCalculadoDobles],[AciertoDobles],[PagoPaginas_1],[PorcConfianza_1],[PagoPaginas_2],[PorcConfianza_2],[PagoPaginas_X],[PorcConfianza_X]) 
VALUES ('espana-2b1', '14/03/2015', 'Oviedo', 'Mar.Luanco', '1', 75,  
CONVERT(NUMERIC(3,2),REPLACE('1,14', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,33', ',', '.')), 1, '1X', 93,  
CONVERT(NUMERIC(3,2),REPLACE('1,09', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,08', ',', '.')), 1,  
CONVERT(NUMERIC(3,2),REPLACE('1,14', ',', '.')), 75,  CONVERT(NUMERIC(3,2),REPLACE('10,66', ',', '.')), 7,  
CONVERT(NUMERIC(3,2),REPLACE('5,55', ',', '.')), 18)


select CONVERT(NUMERIC(3,2),REPLACE('10,66', ',', '.'))
select REPLACE('10,66', ',', '.')
select convert(decimal(18,3), '10.66')


select 
	CONVERT(NUMERIC(3,2),REPLACE('1,14', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,33', ',', '.')), 1, '1X', 93,  
CONVERT(NUMERIC(3,2),REPLACE('1,09', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,08', ',', '.')), 1,  
CONVERT(NUMERIC(3,2),REPLACE('1,14', ',', '.')), 75
,  CONVERT(DECIMAL(18,3),REPLACE('10,66', ',', '.'))
, 7,  
CONVERT(NUMERIC(3,2),REPLACE('5,55', ',', '.')), 18


select *
from pronostico_m
where
	liga = 'espana-2b1'
	and fecha = '14/03/2015'
	and nomeql = 'Oviedo'
	and nomeqv = 'Mar.Luanco'

DELETE PRONOSTICO_M


select count(*) from pronostico_m