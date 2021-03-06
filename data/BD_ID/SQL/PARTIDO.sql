USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_PARTIDO_LIGA]') AND parent_object_id = OBJECT_ID(N'[dbo].[PARTIDO]'))
ALTER TABLE [dbo].[PARTIDO] DROP CONSTRAINT [FK_PARTIDO_LIGA]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PARTIDO]') AND type in (N'U'))
DROP TABLE [dbo].[PARTIDO]
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PARTIDO](
	[cd_liga] [int] NOT NULL,
	[temporada] [varchar](20) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL,
	[fecha_str] [varchar](10) NOT NULL,
	[fecha] [datetime] NOT NULL,
	[equipo_local] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL,
	[equipo_visitante] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AI NOT NULL,
	[goles_local] [int] NOT NULL,
	[goles_visitante] [int] NOT NULL,
	[resultado] [nchar](10) COLLATE SQL_Latin1_General_CP1_CI_AI NULL,
	[cuota1] [float] NULL,
	[coutaX] [float] NULL,
	[cuota2] [float] NULL,
	[dif_goles_local] [int] NULL,
	[dif_goles_visitante] [int] NULL,
	[diferencia] [int] NULL,
 CONSTRAINT [PK_PARTIDO] PRIMARY KEY CLUSTERED 
(
	[cd_liga] ASC,
	[temporada] ASC,
	[fecha_str] ASC,
	[equipo_local] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1=Gana local 2=Gana Visitante X=Empate' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'resultado'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Diferencia de goles del local en 6 partidos previos' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'dif_goles_local'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Diferencia de goles del visitante en 6 partidos previos' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'dif_goles_visitante'
GO

ALTER TABLE [dbo].[PARTIDO]  WITH NOCHECK ADD  CONSTRAINT [FK_PARTIDO_LIGA] FOREIGN KEY([cd_liga])
REFERENCES [dbo].[LIGA] ([codigo])
GO

ALTER TABLE [dbo].[PARTIDO] CHECK CONSTRAINT [FK_PARTIDO_LIGA]