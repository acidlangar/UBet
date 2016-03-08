package org.idsiom.utilbet.currentuse.interlocutor;

public interface IPyckioInterlocutor {
	/* 1) iniciarSession*/
	
	public void iniciarSesion(String usuario, String clave);
	
	/*
     2) traducirA_PickIO(DefPO_OddsPortal)
     3) montarPick(DefPO_PyckIO, Resultado, Stake)
     4) validarPerdidas(Desde)
     5) consultarPicksActivos()
     */
}
