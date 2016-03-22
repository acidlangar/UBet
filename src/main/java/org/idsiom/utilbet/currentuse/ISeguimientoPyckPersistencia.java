package org.idsiom.utilbet.currentuse;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;


public interface ISeguimientoPyckPersistencia {
	public void guardarApuesta(CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck);
	
	public List<PyckBO> getPyckPorDefinir();
	
	public double getRendimientoAcumulado();
	
	public void guardarResultadosPycks(List<PyckBO> pycks);
}
