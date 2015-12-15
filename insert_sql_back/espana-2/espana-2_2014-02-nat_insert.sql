--insert into ...
--1 Barcelona B  -  Sabadell 1 69% 1,92 1,45 12 90% 1,28 1,11
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Barcelona B', 'Sabadell', '1', 69,  CONVERT(NUMERIC(3,2),REPLACE('1,92', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,45', ',', '.')), 1)
--insert into ...
--2 Lugo  -  Valladolid 1 67% 2,36 1,49 1X 89% 1,34 1,12
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Lugo', 'Valladolid', '1', 67,  CONVERT(NUMERIC(3,2),REPLACE('2,36', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,49', ',', '.')), 1)
--insert into ...
--3 R.Santander  -  Mirand�s 1 67% 1,97 1,49 1X 93% 1,21 1,08
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'R.Santander', 'Mirand�s', '1', 67,  CONVERT(NUMERIC(3,2),REPLACE('1,97', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,49', ',', '.')), 0)
--insert into ...
--4 Tenerife  -  Albacete 2 59% 3,86 1,69 12 92% 1,31 1,09
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Tenerife', 'Albacete', '2', 59,  CONVERT(NUMERIC(3,2),REPLACE('3,86', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,69', ',', '.')), 0)
--insert into ...
--5 Llagostera  -  Legan�s 1 58% 2,56 1,72 1X 85% 1,38 1,18
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Llagostera', 'Legan�s', '1', 58,  CONVERT(NUMERIC(3,2),REPLACE('2,56', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,72', ',', '.')), 1)
--insert into ...
--6 Alcorc�n  -  Girona 1 58% 1,92 1,72 1X 85% 1,22 1,18
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Alcorc�n', 'Girona', '1', 58,  CONVERT(NUMERIC(3,2),REPLACE('1,92', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,72', ',', '.')), 0)
--insert into ...
--7 Sp.Gij�n  -  Ponferradina 1 57% 1,75 1,75 1X 88% 1,16 1,14
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Sp.Gij�n', 'Ponferradina', '1', 57,  CONVERT(NUMERIC(3,2),REPLACE('1,75', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,75', ',', '.')), 1)
--insert into ...
--8 Zaragoza  -  Osasuna 1 55% 2,25 1,82 1X 81% 1,29 1,23
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Zaragoza', 'Osasuna', '1', 55,  CONVERT(NUMERIC(3,2),REPLACE('2,25', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,82', ',', '.')), 0)
--insert into ...
--9 Mallorca  -  Las Palmas 1 53% 2,20 1,89 12 85% 1,30 1,18
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Mallorca', 'Las Palmas', '1', 53,  CONVERT(NUMERIC(3,2),REPLACE('2,20', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('1,89', ',', '.')), 0)
--insert into ...
--10 Betis  -  Numancia X 50% 3,74 2,00 X2 85% 2,22 1,18
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Betis', 'Numancia', 'X', 50,  CONVERT(NUMERIC(3,2),REPLACE('3,74', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('2,00', ',', '.')), 0)
--insert into ...
--11 Alav�s  -  Rec.Huelva X 49% 3,07 2,04 1X 80% 1,31 1,25
INSERT INTO [ID_BT].[dbo].[PRONOSTICO_M] ([Liga],[Fecha],[NomEqL],[NomEqV],[Pronostico],[PorcConfianza],[PagoPaginas],[PagoCalculado],[Acierto]) VALUES ('espana-2', '30/08/2014', 'Alav�s', 'Rec.Huelva', 'X', 49,  CONVERT(NUMERIC(3,2),REPLACE('3,07', ',', '.')),  CONVERT(NUMERIC(3,2),REPLACE('2,04', ',', '.')), 1)
