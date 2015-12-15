USE [ID_BT]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HIST_RENDIMIENTO_PRONOSTICO]') AND parent_object_id = OBJECT_ID(N'[dbo].[HIST_RENDIMIENTO]'))
ALTER TABLE [dbo].[HIST_RENDIMIENTO] DROP CONSTRAINT [FK_HIST_RENDIMIENTO_PRONOSTICO]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[HIST_RENDIMIENTO]') AND type in (N'U'))
DROP TABLE [dbo].[HIST_RENDIMIENTO]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[HIST_RENDIMIENTO](
	[cd_Metodo] [int] NOT NULL,
	[cd_Pronostico] [int] NOT NULL,
	[cd_liga] [int] NULL,
	[acierto] [decimal](5, 2) NOT NULL,
	[rend_stake] [decimal](10,3) NOT NULL,
	[yield] [decimal](5,2) NOT NULL,
	[yield_ls] [decimal](5,2) NOT NULL,
	[monto_dinero] [decimal](10,3) NOT NULL,
 CONSTRAINT [PK_HIST_RENDIMIENTO] PRIMARY KEY CLUSTERED 
(
	[cd_Metodo] ASC,
	[cd_Pronostico] ASC
)WITH (PAD_INDEX  = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[HIST_RENDIMIENTO]  WITH NOCHECK ADD  CONSTRAINT [FK_HIST_RENDIMIENTO_PRONOSTICO] FOREIGN KEY([cd_Metodo], [cd_Pronostico])
REFERENCES [dbo].[PRONOSTICO] ([cd_Metodo],	[codigo])
GO
ALTER TABLE [dbo].[HIST_RENDIMIENTO] CHECK CONSTRAINT [FK_HIST_RENDIMIENTO_PRONOSTICO]