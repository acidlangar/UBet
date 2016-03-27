/**
 * 
 */
package org.idsiom.utilbet.currentuse;

import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.junit.Test;

/**
 * @author Edgar.Rodriguez
 *
 */
public class MainCurrentUseTest {
	
	public List<CurrentPOddsPortal> createMockList() {
		
		List<CurrentPOddsPortal> partidosResult = new ArrayList<CurrentPOddsPortal>();
		
		for (int i=0; i<3; i++) {
			
			CurrentPOddsPortal bo = new CurrentPOddsPortal();
			
			bo.setCountry("Espania");
			bo.setLeague("Bbva");
			bo.setFecha( "2015-12-15" );
			bo.setEquipos( "Equipo1 - Equipo2" );
			bo.setC1( 2.1D );
			bo.setcX( 3.2D );
			bo.setC2( 2.5D );
			try {
				if (i==1) bo.setrStr("postp.");
				else if (i==2) bo.setrStr("3:2");
				else bo.setrStr("0:0");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			partidosResult.add(bo);
		}
		
		return partidosResult;
		
	}
	
	/*
	public void testWriteExcel() {
		
		List<CurrentPOddsPortal> lista = createMockList();
		
		MainCurrentUseFromOP.writeExcelFile(lista);
	}
*/
}
