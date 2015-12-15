package org.idsiom.utilbet.history.fromoddsportal.bo;

import java.io.Serializable;
import java.util.List;

public class ListaPs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private List<POddsPortalBO> listaPs;

	public List<POddsPortalBO> getListaPs() {
		return listaPs;
	}

	public void setListaPs(List<POddsPortalBO> listaPs) {
		this.listaPs = listaPs;
	}
	
	
}
