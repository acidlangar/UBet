USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TIPO_PRONOSTICO]') AND type in (N'U'))
DROP TABLE [dbo].[TIPO_PRONOSTICO]

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TIPO_PRONOSTICO](
	[codigo] [tinyint] NOT NULL,
	[descripcion] [varchar](40) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL,
 CONSTRAINT [PK_SIST_TIPO_PRONOSTICO] PRIMARY KEY NONCLUSTERED 
(
	[codigo] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF


INSERT INTO [ID_BT].[dbo].[TIPO_PRONOSTICO]
           ([codigo]
           ,[descripcion])
     VALUES
           (1
           ,'WIN EQ LOCAL')
GO

INSERT INTO [ID_BT].[dbo].[TIPO_PRONOSTICO]
           ([codigo]
           ,[descripcion])
     VALUES
           (2
           ,'WIN EQ VISIT')
GO

INSERT INTO [ID_BT].[dbo].[TIPO_PRONOSTICO]
           ([codigo]
           ,[descripcion])
     VALUES
           (0
           ,'DRAW')
GO
