USE ID_BT
GO

TRUNCATE TABLE CONF_METODO

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L'
		   ,'Ap siempre a LOCAL'
           ,'1'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X'
			,'Ap siempre a EMPATE'
           ,'X'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V'
			,'Ap siempre a VISITANTE'
           ,'2'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L-N1'
		   ,'Ap a LOCAL con 0 a 20 prob de ganar'
           ,'1'
           ,0.0 --<ProbLimInfL, numeric,>
           ,0.20 --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L-N2'
		   ,'Ap a LOCAL con 20 a 40 prob de ganar'
           ,'1'
           ,0.20 --<ProbLimInfL, numeric,>
           ,0.40 --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L-N3'
			,'Ap a LOCAL con 40 a 60 prob de ganar'
           ,'1'
           ,0.40 --<ProbLimInfL, numeric,>
           ,0.60 --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L-N4'
			,'Ap a LOCAL con 60 a 80 prob de ganar'
           ,'1'
           ,0.60 --<ProbLimInfL, numeric,>
           ,0.80 --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-L-N5'
			,'Ap a LOCAL con 80 a 100 prob de ganar'
           ,'1'
           ,0.80 --<ProbLimInfL, numeric,>
           ,1.0 --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)


---->>>>

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X-N1'
			,'Ap a EMPATE con 0 a 20 prob de ocurrir'
           ,'X'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,0.0 --<ProbLimInfX, numeric,>
           ,0.20 --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X-N2'
			,'Ap a EMPATE con 20 a 40 prob de ocurrir'
           ,'X'
           ,NULL  --<ProbLimInfL, numeric,>
           ,NULL  --<ProbLimSupL, numeric,>
           ,0.20 --<ProbLimInfX, numeric,>
           ,0.40 --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X-N3'
			,'Ap a EMPATE con 40 a 60 prob de ocurrir'
           ,'X'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,0.40 --<ProbLimInfX, numeric,>
           ,0.60 --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X-N4'
			,'Ap a EMPATE con 60 a 80 prob de ocurrir'
           ,'X'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,0.60 --<ProbLimInfX, numeric,>
           ,0.80 --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-X-N5'
			,'Ap a EMPATE con 80 a 100 prob de ocurrir'
           ,'X'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,0.80 --<ProbLimInfX, numeric,>
           ,1.0 --<ProbLimSupX, numeric,>
           ,NULL --<ProbLimInfV, numeric,>
           ,NULL --<ProbLimSupV, numeric,>
	)

----->>>>>

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V-N1'
			,'Ap a VISITANTE con 0 a 20 prob de ganar'
           ,'2'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,0.0 --<ProbLimInfV, numeric,>
           ,0.20 --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V-N2'
			,'Ap a EMPATE con 20 a 40 prob de ganar'
           ,'2'
           ,NULL  --<ProbLimInfL, numeric,>
           ,NULL  --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,0.20 --<ProbLimInfV, numeric,>
           ,0.40 --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V-N3'
			,'Ap a EMPATE con 40 a 60 prob de ganar'
           ,'2'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,0.40 --<ProbLimInfV, numeric,>
           ,0.60 --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V-N4'
			,'Ap a EMPATE con 60 a 80 prob de ganar'
           ,'2'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL --<ProbLimSupX, numeric,>
           ,0.60 --<ProbLimInfV, numeric,>
           ,0.80 --<ProbLimSupV, numeric,>
	)

INSERT INTO [ID_BT].[dbo].[CONF_METODO]
           ([Metodo]
		   ,[Descripcion] 	
           ,[Pronostico]
           ,[ProbLimInfL]
           ,[ProbLimSupL]
           ,[ProbLimInfX]
           ,[ProbLimSupX]
           ,[ProbLimInfV]
           ,[ProbLimSupV])
     VALUES
           ('M-V-N5'
			,'Ap a EMPATE con 80 a 100 prob de ganar'
           ,'2'
           ,NULL --<ProbLimInfL, numeric,>
           ,NULL --<ProbLimSupL, numeric,>
           ,NULL --<ProbLimInfX, numeric,>
           ,NULL--<ProbLimSupX, numeric,>
           ,0.80 --<ProbLimInfV, numeric,>
           ,1.0  --<ProbLimSupV, numeric,>
	)



SELECT *
FROM CONF_METODO