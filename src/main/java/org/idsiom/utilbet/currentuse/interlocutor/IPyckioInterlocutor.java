package org.idsiom.utilbet.currentuse.interlocutor;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;

public interface IPyckioInterlocutor {
	/* 1) iniciarSession*/
	
	public void iniciarSesion(String usuario, String clave);

	public void montarPick(CurrentPOddsPortal pOP, ResultadoPartidoBO local, int i);
	
	public List<PartidoPyckioBO> getPartidosPorHora(Long momento);
	
	/*
     2) traducirA_PickIO(DefPO_OddsPortal)
     3) montarPick(DefPO_PyckIO, Resultado, Stake)
     4) validarPerdidas(Desde)
     5) consultarPicksActivos()
     */
}
