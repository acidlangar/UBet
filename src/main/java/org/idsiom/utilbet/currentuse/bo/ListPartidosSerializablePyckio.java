package  org.idsiom.utilbet.currentuse.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListPartidosSerializablePyckio implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private List<PartidoPyckioBO> partidos = new ArrayList<PartidoPyckioBO>();
		
		public List<PartidoPyckioBO> getPartidos() {
			return partidos;
		}

		public void setPartidos(List<PartidoPyckioBO> partidos) {
			this.partidos = partidos;
		}

				
}
