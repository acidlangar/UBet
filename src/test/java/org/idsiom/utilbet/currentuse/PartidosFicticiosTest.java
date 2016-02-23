/**
 * 
 */
package org.idsiom.utilbet.currentuse;

import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.idsiom.utilbet.currentuse.interlocutor.InterlocutorPruebaProximosJuegosImpl;
import org.junit.Test;

/**
 * @author Edgar.Rodriguez
 *
 */
public class PartidosFicticiosTest {
	
	@Test
	public void testListaPartidosFicticios() {
		
		InterlocutorPruebaProximosJuegosImpl inte = new InterlocutorPruebaProximosJuegosImpl();
		try {
			ListPartidosSerializable lista = inte.getPs(true);
			
			List<CurrentPOddsPortal> listaNew = lista.getListaPs();
			
			for(CurrentPOddsPortal p : listaNew) {
				System.out.println(p);
				System.out.println("empiezaEnProxMins = " + MainFromFileCurrentP.empiezaEnProxMins(p));
				System.out.println("     ");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
