USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[METODO]') AND type in (N'U'))
DROP TABLE [dbo].[METODO]

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[METODO](
	[codigo] [int] NOT NULL,
	[abrev] [varchar](5) NOT NULL,
	[nombre] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL,
	[descripcion] [varchar](200) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL
 CONSTRAINT [PK_METODO] PRIMARY KEY NONCLUSTERED 
(
	[codigo] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF


INSERT INTO [ID_BT].[dbo].[METODO]
           ([codigo]
           ,[abrev]
           ,[nombre]
           ,[descripcion])
     VALUES
           (1
           ,'MPF'
           ,'Martingala por Favoritos'
           ,'Martingala haciendo buena selección de favoritos, y parando al lograr una meta')
GO

INSERT INTO [ID_BT].[dbo].[METODO]
           ([codigo]
           ,[abrev]
           ,[nombre]
           ,[descripcion])
     VALUES
           (2
           ,'ACPF'
           ,'Apuesta constante por Favoritos'
           ,'Consiste en mantener un monto de apuesta constante y seleccionando favoritos')
GO