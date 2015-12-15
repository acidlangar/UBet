USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[v_result_pronosticos]'))
DROP VIEW [dbo].[v_result_pronosticos]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[v_result_pronosticos] AS
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
			when p.goles_local > p.goles_visitante then '1'
			when p.goles_local < p.goles_visitante then '2'
			when p.goles_local = p.goles_visitante then 'X'
		else 'problema' end ResultadoReal,
		l.descripcion + '-' + convert(varchar,datepart(year,p.fecha)) Liga,
		p.fecha Fecha,
		p.equipo_local NomEqL,
		p.equipo_visitante NomEqV,
		p.cuota1 PagoPaginas_1,
		p.cuota2 PagoPaginas_2,
		p.coutaX PagoPaginas_X
	
	FROM partido p
	INNER JOIN liga l ON l.codigo = p.cd_liga
	WHERE
		p.cd_liga in (1,3)
	) SQ

--SP_HELP PRONOSTICO_M
