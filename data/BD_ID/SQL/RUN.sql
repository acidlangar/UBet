USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RUN]') AND type in (N'U'))
DROP TABLE [dbo].[RUN]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RUN](
	[idFecha] [int] NOT NULL,
	[idRun] [int] NOT NULL,
	[Fecha] [varchar](50) NOT NULL,
	[cd_liga] [int] NOT NULL,
	[NomEqL]  [varchar](50) NOT NULL,
	[Acierto] [smallint] DEFAULT ((NULL)),
	[Acumulado] [int] DEFAULT ((NULL)),
	 CONSTRAINT [PK_RUN] PRIMARY KEY CLUSTERED 
	(
		[idFecha] ASC,
		[idRun] ASC
	)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


