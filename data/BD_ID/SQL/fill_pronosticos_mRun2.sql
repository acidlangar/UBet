-- cursor de runs en base a los ranks

por cada run
	crear nuevo cursor, Caux
		select
			idFecha,
			idRun,
			AF
		from runs
		where
			rank = vIdRun
		order by
			idFecha
		
	
	vAcumulado = 0
	ciclar sobre Caux
		fAnterior = fBuscarFAnterior(idFecha, idRun)
		
		if AF = 'A'
			update runs set acumulado = 0
			where	
				idFecha = idFecha
				and idRun = idRun
				
		else 
			if fAnterior is NULL
				vAcumAnterior = 0
			else 
				select 
					vAcumAnterior = Acumulado
				from runs
				where	
					idFecha = idFechaAnterior
					and idRun = idRun
				
			update runs set acumulado = vAcumAnterior + 1
			where	
				idFecha = idFecha
				and idRun = idRun
				
