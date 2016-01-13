use id_bt
go


-- if exist drop

-- create proc

-- PARAM ENTRADA:
--    @CantHistoria
--    @TipoPartido
--    @DesviacionRegistrada
--    @TipoResultado

-- PARAM SALIDA:
--     TotalRendimiento
--     CantPartidos   Cantidad de Partidos en la BD en los que ocurrio el caso
--     CantResultadoEsperado
--     CantResultadoContrario
--     Acierto
--     CuotaPromedio
--     Ganancias
--     Perdidas 

-- CUERPO DEL PROCEDIMIENTO
-- Hacemos un cursor sobre todos los partidos de la BD ordenamos desde mas viejo a mas nuevo

-- Para cada partido llenamos la tabla REP_RENDIMIENTO_VS_DESVIACION
      -- Realizamos otro cursor, que tega los Ultimos 100 partidos, anteriores al partido actual, esta vez ordenados de más reciente a mas viejo
	  
		  -- Con un contador, controlamos si debemos salir del cursor o no.
		  
		  -- Hacemos los insert en una tabla temporal, del partido.
	  
	  -- Con la historia reciente del partido en la tabla temporal, procedemos a:
	  
	  -- Calcular las cuotas promedios y los porcentajes promedios
	  
	  -- Eliminamos el overround
	  
	  -- Calculamos la Expectativa de Resultados, Y la realidad de Resultados.
	  
	  -- Calculamos las desviaciones. 
	  
	  -- Insertamos en la tabla REP_RENDIMIENTO_VS_DESVIACION, las cuotas promedios, los porcentajes, las expectativas, las realidades, y las desviaciones
	  
-- Luego de calculada la tabla, llamamos al procedimiento de la consuta de los resultados cons_rendimiento_vs_desviacion	  