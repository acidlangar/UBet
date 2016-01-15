USE ID_BT
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[REP_RENDIMIENTO_VS_DESVIACION]') AND type in (N'U'))
DROP TABLE [dbo].[REP_RENDIMIENTO_VS_DESVIACION]
GO 

-- Nombre de tabla: REP_RENDIMIENTO_VS_DESVIACION
-- Descripcion: Tabla Auxiliar, la misma se limpiara y llenara  cada vez que se ejecute el procedimiento
--             cal_rendimiento_vs_desviacion. 
--              Para cada partido que aplique, la tabla tendrá el rendimiento generado, para al final poder sumarizar.
CREATE TABLE REP_RENDIMIENTO_VS_DESVIACION(
	cd_liga INT,
	fecha DATETIME,
	equipo_local VARCHAR(50),
	Rendimiento1 NUMERIC(18,3),
    RendimientoX NUMERIC(18,3),
    Rendimiento2 NUMERIC(18,3),
	Desviacion1 NUMERIC(18,3),
    DesviacionX NUMERIC(18,3),
    Desviacion2 NUMERIC(18,3),
	Expectativa1 NUMERIC(18,3),
    ExpectativaX NUMERIC(18,3),
    Expectativa2 NUMERIC(18,3),
	Realidad1 INT,
	RealidadX INT,
	Realidad2 INT
)