package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;
import org.idsiom.utilbet.currentuse.xls.XLSException;


public interface ISeguimientoPyckPersistencia {
	public void guardarApuesta(CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck) throws XLSException;
	
	public List<PyckBO> getPyckPorDefinir() throws IOException;
	
	public double getRendimientoAcumulado();
	
	public void guardarResultadosPycks(List<PyckBO> pycks);
}
