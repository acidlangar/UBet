package org.idsiom.utilbet.currentuse.interlocutor;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;

public interface IOddsPortalCurrentUseInterlocutor {

	public ListPartidosSerializable getPs() throws Exception;
	
}
