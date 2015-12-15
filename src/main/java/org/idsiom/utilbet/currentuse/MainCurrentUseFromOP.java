package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.bo.ListaPartSeri;

public class MainCurrentUseFromOP {
	
	static Logger logger = Logger.getLogger(MainCurrentUseFromOP.class);

	public static void main(String[] args) {
		List<CurrentPOddsPortal> listaPs = null;
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		IOddsPortalCurrentUseInterlocutor interlocutor = new OddsPortalInterCurrentUseImpl();
		
		try {
			listaPs = interlocutor.getPs(3);
		} catch(Exception ex) {
			logger.error("No fue posible obtener ps ", ex);
		}
		
			
		if(listaPs != null) {
			for(CurrentPOddsPortal p : listaPs) {
				System.out.println("****************");
				System.out.println(p);
				System.out.println("****************");
			}
			
			try {
				if(listaPs.size() > 0) {
					ListaPartSeri lAux = new ListaPartSeri();
					lAux.setListaPs(listaPs);
					
					File dir = new File("../datos");
					if(!dir.exists()) {
						dir.mkdir();
					}
					
					File fichero = new File(dir.getAbsolutePath() + "/PartidosCurrent.srz");
					if(fichero.exists()) {
						fichero.delete();
					}
					
					if(!fichero.exists()) {
						fichero.createNewFile();
						System.out.println("Archivo creado vacio exitosamente :: " + fichero.getAbsolutePath());
					} 

					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
					oos.writeObject(lAux);
					System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
					
					oos.close();
					oos = null;
					lAux = null;
				}	
			} catch(Exception ex) {
				logger.error("No fue posible crear el archivo srv "+ "/PartidosCurrent.srz",ex);
			}
		}
	}
}
