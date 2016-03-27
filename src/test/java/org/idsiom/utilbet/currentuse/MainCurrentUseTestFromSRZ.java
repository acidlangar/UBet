/**
 * 
 */
package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;
import org.junit.Test;

/**
 * @author Edgar.Rodriguez
 *
 */
public class MainCurrentUseTestFromSRZ {
	
	private ObjectInputStream ois;

	public List<CurrentPOddsPortal> createMockList() {
		
		List<CurrentPOddsPortal> partidosResult = new ArrayList<CurrentPOddsPortal>();
		
		File fichero = new File("C:/DEVTOOLS/PartidosCurrent.srz");
		if(!fichero.exists()) {
			System.out.println("El archivo no existe... " + fichero.getAbsolutePath());
			return null;
		}
		
		try {
			ois = new ObjectInputStream(new FileInputStream(fichero));
			
			// Se lee el primer objeto
			Object aux;
			aux = ois.readObject();
			           
			if (aux instanceof ListPartidosSerializable) {
				ListPartidosSerializable lista = (ListPartidosSerializable)aux;
				//partidosResult = lista.getListaPs();
		    }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
