package org.idsiom.utilbet.core;

import java.util.GregorianCalendar;
import java.util.List;

import org.idsiom.utilbet.bo.PartidoParaApostarBO;

public interface IIdentificadorDeOportunidad {
	public List<PartidoParaApostarBO> ordenarApuestasPorDiferencia(List<PartidoParaApostarBO> apuestasDisponibles);
	
	public List<PartidoParaApostarBO> primerasApuestasPorDiferencia(List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha, Integer cantidad);
	
	public List<PartidoParaApostarBO> primerasApuestasPorDiferenciaMayorA(List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha, Integer cantidad, Double minimoGanancia);
	
	public List<PartidoParaApostarBO> apuestasMasParejas(List<PartidoParaApostarBO> apuestasDisponibles, GregorianCalendar fecha, Integer cantidad, Double minimoGanancia);
}
