USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PRONOSTICO_M]') AND type in (N'U'))
DROP TABLE [dbo].[PRONOSTICO_M]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PRONOSTICO_M](
	[Liga] [varchar](50) NOT NULL,
	[Fecha] [Smalldatetime] NOT NULL,
	[NomEqL] [varchar](50) NOT NULL,
	[NomEqV] [varchar](50) NOT NULL,
	[Pronostico] [varchar](1) NOT NULL,
	[PorcConfianza] [int] NOT NULL,
	[PagoPaginas] [decimal](18, 3) NOT NULL,
	[PagoCalculado] [decimal](18, 3) NOT NULL,
	[PagoPaginas_1] [decimal](18, 3) NOT NULL,
	[PorcConfianza_1] [int] NOT NULL,
	[PagoPaginas_2] [decimal](18, 3) NOT NULL,
	[PorcConfianza_2] [int] NOT NULL,
	[PagoPaginas_X] [decimal](18, 3) NOT NULL,
	[PorcConfianza_X] [int] NOT NULL,
	[Acierto] [smallint] DEFAULT ((NULL)),
	[PronosticoDobles] [varchar](2) NOT NULL,
	[PorcConfianzaDobles] [int] NOT NULL,
	[PagoPaginasDobles] [decimal](18, 3) NOT NULL,
	[PagoCalculadoDobles] [decimal](18, 3) NOT NULL,
	[AciertoDobles] [smallint] DEFAULT ((NULL))
 CONSTRAINT [PK_PRONOSTICO_M] PRIMARY KEY CLUSTERED 
(
	[NomEqL] ASC,
	[NomEqV] ASC,
	[Liga] ASC,
	[Fecha] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


