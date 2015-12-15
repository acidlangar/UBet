USE ID_BT
GO

--insert into ...
--1 Atl.Madrid  -  R.Vallecano 1 70% 1,25 1,43 1X 87% 1,02 1,15
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Atl.Madrid', 'R.Vallecano', '1', 70,  CONVERT(NUMERIC(3,2),REPLACE('1,25', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,43', ',', '.')), 1)
--insert into ...
--2 Valencia  -  Sevilla 1 67% 2,17 1,49 12 93% 1,32 1,08
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Valencia', 'Sevilla', '1', 67,  CONVERT(NUMERIC(3,2),REPLACE('2,17', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,49', ',', '.')), 1)
--insert into ...
--3 Villarreal  -  Levante 1 66% 1,25 1,52 1X 90% 1,01 1,11
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Villarreal', 'Levante', '1', 66,  CONVERT(NUMERIC(3,2),REPLACE('1,25', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,52', ',', '.')), 1)
--insert into ...
--4 Espanyol  -  Almer�a 1 62% 1,71 1,61 12 88% 1,27 1,14
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Espanyol', 'Almer�a', '1', 62,  CONVERT(NUMERIC(3,2),REPLACE('1,71', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,61', ',', '.')), 1)
--insert into ...
--5 Elche  -  Barcelona 2 59% 1,11 1,69 X2 84% 1,10 1,19
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Elche', 'Barcelona', '2', 59,  CONVERT(NUMERIC(3,2),REPLACE('1,11', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,69', ',', '.')), 1)
--insert into ...
--6 Dep.Coru�a  -  Granada 1 58% 2,19 1,72 1X 87% 1,29 1,15
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Dep.Coru�a', 'Granada', '1', 58,  CONVERT(NUMERIC(3,2),REPLACE('2,19', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,72', ',', '.')), 0)
--insert into ...
--7 Ath.Bilbao  -  M�laga 1 55% 2,12 1,82 12 87% 1,36 1,15
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Ath.Bilbao', 'M�laga', '1', 55,  CONVERT(NUMERIC(3,2),REPLACE('2,12', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,82', ',', '.')), 0)
--insert into ...
--8 C�rdoba  -  Real Madrid 2 52% 1,08 1,92 X2 87% 1,10 1,15
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'C�rdoba', 'Real Madrid', '2', 52,  CONVERT(NUMERIC(3,2),REPLACE('1,08', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,92', ',', '.')), 1)
--insert into ...
--9 Getafe  -  Celta 1 50% 2,85 2,00 12 85% 1,35 1,18
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'Getafe', 'Celta', '1', 50,  CONVERT(NUMERIC(3,2),REPLACE('2,85', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('2,00', ',', '.')), 1)
--insert into ...
--10 R.Sociedad  -  Eibar 1 47% 1,70 2,13 12 80% 1,27 1,25
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-1', '24/01/2015', 'R.Sociedad', 'Eibar', '1', 47,  CONVERT(NUMERIC(3,2),REPLACE('1,70', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('2,13', ',', '.')), 1)
