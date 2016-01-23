use id_bt
go

Select sum(sq.RendimientoX), sum(
 case 
	when sq.RendimientoX > 0 then 1
	else 0
 end
) Aciertos, sum(
 case 
	when sq.RendimientoX < 0 then 1
	else 0
 end
) Desaciertos/*, sq.**/
from
(
select RendimientoX, ExpectativaX, RealidadX, ((RealidadX * 100)/ExpectativaX) - 100 Desv
from rep_rendimiento_vs_desviacion
) sq
where
	sq.Desv = -100
