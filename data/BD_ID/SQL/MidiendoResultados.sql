use id_bt

select 
	pr.resultado, pr.stake, p.resultado real, p.cuota1, 
	case 
		when pr.resultado = p.resultado then (p.cuota1 * pr.stake) - pr.stake
		else -1 * pr.stake
	end gp
from pronostico pr
join partido p on p.cd_liga = pr.cd_liga and convert(varchar(10),p.fecha,101) = pr.fecha_str and p.equipo_local = pr.equipo_local



select 
	sum(case 
		when pr.resultado = p.resultado then (p.cuota1 * pr.stake) - pr.stake
		else -1 * pr.stake
	end) total,
	

	-- Yield = (Beneficios netos obtenidos / Cantidad apostada) x 100
    -- http://www.marca.com/blogs/charla-de-apuestas/2010/12/15/el-yield-en-apuestas-deportivas.html
	(( sum(case 
		when pr.resultado = p.resultado then (p.cuota1 * pr.stake) - pr.stake
		else -1 * pr.stake
	end) ) / sum(stake)) * 100 Yield
from pronostico pr
join partido p on p.cd_liga = pr.cd_liga and convert(varchar(10),p.fecha,101) = pr.fecha_str and p.equipo_local = pr.equipo_local






select 
	sum(case 
		when pr.resultado = p.resultado then (p.cuota1 * pr.stake) - pr.stake
		else -1 * pr.stake
	end) total,
	(( sum(case 
		when pr.resultado = p.resultado then (p.cuota1 * pr.stake) - pr.stake
		else -1 * pr.stake
	end) ) / sum(stake)) * 100 Yield
from pronostico pr
join partido p on p.cd_liga = pr.cd_liga and convert(varchar(10),p.fecha,101) = pr.fecha_str and p.equipo_local = pr.equipo_local
where
	pr.codigo <= 24






/*
INSERT INTO [ID_BT].[dbo].[HIST_RENDIMIENTO]
           ([cd_Metodo]
           ,[cd_Pronostico]
           ,[cd_liga]
           ,[acierto]
           ,[rend_stake]
           ,[yield]
           ,[yield_ls]
           ,[real_stake]
           ,[monto_dinero])
     SELECT
           	1
           ,pr.codigo
           ,null
		   ,(sum(case 
				when pr.resultado = p.resultado then 1
				else 0
			end) * 100 )/count(1) Acierto,
           ,<rend_stake, decimal(10,3),>
           ,<yield, decimal(5,2),>
           ,<yield_ls, decimal(5,2),>
           ,<real_stake, decimal(10,3),>
           ,<monto_dinero, decimal(10,3),>)
*/