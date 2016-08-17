package org.idsiom.utilbet.currentuse;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;

public interface ICurrentSinCoincidencias {
	public void writeExcelFile(List<CurrentPOddsPortal> listaPs);
}
