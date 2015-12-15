USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_PARTIDO_LIGA]') AND parent_object_id = OBJECT_ID(N'[dbo].[PARTIDO]'))
ALTER TABLE [dbo].[PARTIDO] DROP CONSTRAINT [FK_PARTIDO_LIGA]
GO
USE [ID_BT]
GO
/****** Object:  Table [dbo].[PARTIDO]    Script Date: 07/10/2015 13:33:23 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PARTIDO]') AND type in (N'U'))
DROP TABLE [dbo].[PARTIDO]

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[PARTIDO](
	[cd_liga] [int] NOT NULL,
	[fecha] [datetime] NOT NULL,
	[equipo_local] [varchar](50) NOT NULL,
	[equipo_visitante] [varchar](50) NOT NULL,
	[goles_local] [int] NOT NULL,
	[goles_visitante] [int] NOT NULL,
	[resultado] [nchar](10) NULL,
	[cuota1] [float] NULL,
	[coutaX] [float] NULL,
	[cuota2] [float] NULL,
	[dif_goles_local] [int] NULL,
	[dif_goles_visitante] [int] NULL,
 CONSTRAINT [PK_PARTIDO_1] PRIMARY KEY CLUSTERED 
(
	[cd_liga] ASC,
	[fecha] ASC,
	[equipo_local] ASC,
	[equipo_visitante] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[PARTIDO]  WITH CHECK ADD  CONSTRAINT [FK_PARTIDO_LIGA] FOREIGN KEY([cd_liga])
REFERENCES [dbo].[LIGA] ([codigo])
GO

ALTER TABLE [dbo].[PARTIDO] CHECK CONSTRAINT [FK_PARTIDO_LIGA]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1=Gana local 2=Gana Visitante X=Empate' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'resultado'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Diferencia de goles del local en 6 partidos previos' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'dif_goles_local'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Diferencia de goles del visitante en 6 partidos previos' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PARTIDO', @level2type=N'COLUMN',@level2name=N'dif_goles_visitante'
GO

