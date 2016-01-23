package org.idsiom.utilbet.currentuse.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListPartidosSerializable implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private List<CurrentPOddsPortal> partidosHistory = new ArrayList<CurrentPOddsPortal>();
		
		private List<CurrentPOddsPortal> listaPsHoyFuturo = new ArrayList<CurrentPOddsPortal>();

		public List<CurrentPOddsPortal> getPartidosHistory() {
			return partidosHistory;
		}

		public void setPartidosHistory(List<CurrentPOddsPortal> partidosHistory) {
			this.partidosHistory = partidosHistory;
		}

		public List<CurrentPOddsPortal> getListaPsHoyFuturo() {
			return listaPsHoyFuturo;
		}

		public void setListaPsHoyFuturo(List<CurrentPOddsPortal> listaPsHoyFuturo) {
			this.listaPsHoyFuturo = listaPsHoyFuturo;
		}

		public List<CurrentPOddsPortal> getListaPs() {
			List<CurrentPOddsPortal> listaTodos = new ArrayList<CurrentPOddsPortal>();
			
			listaTodos.addAll( this.getPartidosHistory() );
			listaTodos.addAll( this.getListaPsHoyFuturo() );
			
			return listaTodos;
		}
		
		
		
}
