package org.idsiom.utilbet.currentuse.bo;

import java.io.Serializable;
import java.util.List;

public class ListaPartSeri implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private List<CurrentPOddsPortal> listaPs;

		public List<CurrentPOddsPortal> getListaPs() {
			return listaPs;
		}

		public void setListaPs(List<CurrentPOddsPortal> listaPs) {
			this.listaPs = listaPs;
		}
		
		
}
