USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CONF_METODO]') AND type in (N'U'))
DROP TABLE [dbo].[CONF_METODO]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CONF_METODO](
	[Metodo] [varchar](50) NOT NULL,	
	[Descripcion] [varchar](50) NOT NULL,
	[Pronostico] [varchar](1) NOT NULL,
	[ProbLimInfL] DECIMAL(5,3) DEFAULT ((NULL)),
	[ProbLimSupL] DECIMAL(5,3) DEFAULT ((NULL)),
	[ProbLimInfX] DECIMAL(5,3) DEFAULT ((NULL)),
	[ProbLimSupX] DECIMAL(5,3) DEFAULT ((NULL)),
	[ProbLimInfV] DECIMAL(5,3) DEFAULT ((NULL)),
	[ProbLimSupV] DECIMAL(5,3) DEFAULT ((NULL))
 CONSTRAINT [PK_CONF_METODO] PRIMARY KEY CLUSTERED 
(
	[Metodo] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


