USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RESULT_ACPF]') AND type in (N'U'))
DROP TABLE [dbo].[RESULT_ACPF]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RESULT_ACPF](
	[Fecha] [Smalldatetime] NOT NULL,
	[PorcResultante] [decimal](18,3) NOT NULL,-- (100%-Significa duplicar disponible; 200%-Dos veces el disponible;
--                                     -100%-Bancarota; -50%-Perdida de la mitad del disponible)
	[CantTotaJuegos] [int] NOT NULL,-- ; (Cantidad total de los juegos seleccionados para la fecha)
	[CantTotalDesaciertos] [int] NOT NULL,-- ;  (Cantidad total de desaciertos en todos los seleccionados)
	[PorcDesaciertos] [decimal](18,3) NOT NULL,-- ;  (Porcentaje de desaciertos)
 CONSTRAINT [PK_RESULT_ACPF] PRIMARY KEY CLUSTERED 
(
	[Fecha] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


