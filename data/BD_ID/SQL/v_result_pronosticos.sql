USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[v_result_pronosticos]'))
DROP VIEW [dbo].[v_result_pronosticos]
GO

CREATE VIEW v_result_pronosticos AS
	SELECT 
		case
			when SQ.ResultadoReal = '1' then SQ.PagoPaginas_1
			when SQ.ResultadoReal = 'X' then SQ.PagoPaginas_X
			when SQ.ResultadoReal = '2' then SQ.PagoPaginas_2
		end PagoResultado,
		SQ.*
	FROM 
	(
	SELECT 
		case
		when acierto = 1 and pronostico = '1' then '1'
		when acierto = 1 and pronostico = '2' then '2'
		when acierto = 1 and pronostico = 'X' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = '1' and pronosticodobles = '12' then 'X'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = '1' and pronosticodobles = '1X' then '2'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '1' and pronosticodobles = '12' then '2'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '1' and pronosticodobles = '1X' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = '2' and pronosticodobles = '12' then 'X'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = '2' and pronosticodobles = 'X2' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '2' and pronosticodobles = '12' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = '2' and pronosticodobles = 'X2' then 'X'

		when acierto = 0 and aciertodobles = 0 
			and pronostico = 'X' and pronosticodobles = '1X' then '2'
		when acierto = 0 and aciertodobles = 0 
			and pronostico = 'X' and pronosticodobles = 'X2' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = 'X' and pronosticodobles = '1X' then '1'
		when acierto = 0 and aciertodobles = 1 
			and pronostico = 'X' and pronosticodobles = 'X2' then '2'

		else 'problema' end ResultadoReal,
		Liga,
		Fecha,
		NomEqL,
		NomEqV,
		Pronostico,
		PorcConfianza,
		PagoPaginas,
		PagoCalculado,
		PagoPaginas_1,
		PorcConfianza_1,
		PagoPaginas_2,
		PorcConfianza_2,
		PagoPaginas_X,
		PorcConfianza_X,
		Acierto,
		PronosticoDobles,
		PorcConfianzaDobles,
		PagoPaginasDobles,
		PagoCalculadoDobles,
		AciertoDobles
	
	FROM pronostico_m
	) SQ

--SP_HELP PRONOSTICO_M
