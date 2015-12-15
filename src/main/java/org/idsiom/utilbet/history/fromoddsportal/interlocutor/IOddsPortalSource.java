package org.idsiom.utilbet.history.fromoddsportal.interlocutor;

import java.util.List;

import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;

public interface IOddsPortalSource {
	public List<POddsPortalBO> getPs(String country, String lig, String temp) throws Exception;
	
}
