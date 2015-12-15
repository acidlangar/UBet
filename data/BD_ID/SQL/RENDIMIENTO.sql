USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RENDIMIENTO]') AND type in (N'U'))
DROP TABLE [dbo].[RENDIMIENTO]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RENDIMIENTO](
	[MetodoBasico] [varchar](50) NOT NULL,	
	[Metodo] [varchar](50) NOT NULL,
	[Rendimiento] [decimal](18, 3) NOT NULL,
	[PorcAciertos] [decimal](7, 3) NOT NULL,
	[CantTotalJuegos] [int] NOT NULL
 CONSTRAINT [PK_RENDIMIENTO] PRIMARY KEY CLUSTERED 
(
	[Metodo] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


