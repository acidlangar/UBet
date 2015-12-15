USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[METODO_AP]') AND type in (N'U'))
DROP TABLE [dbo].[METODO_AP]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[METODO_AP](
	[Codigo] [INT] NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[DescMetodoResults] [varchar](200) NOT NULL,
	[DescMetodoStake] [varchar](200) NOT NULL
 CONSTRAINT [PK_METODO_AP] PRIMARY KEY CLUSTERED 
(
	[Codigo] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


