package org.idsiom.utilbet.currentuse;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PyckBO;

public interface ICurrentDesviacionPersistencia {
	public void writeExcelFile(List<CurrentPOddsPortal> listaPs);
	
}
