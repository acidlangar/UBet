package org.idsiom.utilbet.interlocutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.idsiom.utilbet.exception.InteraccionException;

import org.idsiom.utilbet.bo.DefApuestaCombinadaBO;
import org.idsiom.utilbet.bo.PartidoParaApostarBO;

public interface IInterlocutorCasaDeBets {
	public List<PartidoParaApostarBO> consultarApuestasDisponibles(Boolean paraHoy) throws InteraccionException;

	public void montarApuestaCombinada(DefApuestaCombinadaBO apCombinada, HashSet<String> ligas) throws InteraccionException;
}
