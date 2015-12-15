USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RESULT_MPF]') AND type in (N'U'))
DROP TABLE [dbo].[RESULT_MPF]


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RESULT_MPF](
	[Fecha] [Smalldatetime] NOT NULL,
	[Escenario] [int] NOT NULL,  -- 1-Random; 2-Fallidos juntos al principio
    [PorcResultante] [decimal](18,3) NOT NULL,-- (100%-Significa duplicar disponible; 200%-Dos veces el disponible;
--                                     -100%-Bancarota; -50%-Perdida de la mitad del disponible)
    [PorcMaxUsado] [decimal](18,3) NOT NULL, -- ; (100%-En algún momento se puso en riesgo completamente;
--                                    25%-Solo una cuarta parte del disponible se puso en riesgo)
	[CantTotaJuegos] [int] NOT NULL,-- ; (Cantidad total de los juegos seleccionados para la fecha)
	[CantJuegosParticipados] [int] NOT NULL,-- ; (De los juegos necesarios, cuantos fue necesario jugar)
	[CantTotalDesaciertos] [int] NOT NULL,-- ;  (Cantidad total de desaciertos en todos los seleccionados)
	[CantTotalDesaciertosParticipados] [int] NOT NULL-- ; (Cantidad de desaciertos participados)
 CONSTRAINT [PK_RESULT_MPF] PRIMARY KEY CLUSTERED 
(
	[Fecha] ASC,
	[Escenario] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]


