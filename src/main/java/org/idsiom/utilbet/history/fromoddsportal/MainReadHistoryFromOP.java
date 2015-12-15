package org.idsiom.utilbet.history.fromoddsportal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.idsiom.utilbet.history.fromoddsportal.bo.ListaDeListas;
import org.idsiom.utilbet.history.fromoddsportal.bo.ListaPs;
import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;
import org.idsiom.utilbet.history.fromoddsportal.interlocutor.IOddsPortalSource;
import org.idsiom.utilbet.history.fromoddsportal.interlocutor.OddsPortalInterlocutorImpl;
import org.idsiom.utilbet.history.fromoddsportal.objutil.LgTempObjUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MainReadHistoryFromOP {
	
	static Logger logger = Logger.getLogger(MainReadHistoryFromOP.class);

	public static void main(String[] args) {
		Boolean pobtenidos = false;
		List<POddsPortalBO> listaPs = null;
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		// TODO Auto-generated method stub
		IOddsPortalSource interlocutor = new OddsPortalInterlocutorImpl();
		
		ListaDeListas listaDeListas = new ListaDeListas();
		List<LgTempObjUtil> lts = Cons.getLgTemps();
		
		for(LgTempObjUtil lt : lts) {
			listaDeListas = new ListaDeListas();
			pobtenidos = false;
			
			try {
				listaPs = interlocutor.getPs(lt.getCountry(),lt.getLg(), lt.getTemp());
				pobtenidos = true;
			} catch(Exception ex) {
				logger.error("No fue posible obtener ps para " + lt.getCountry() +"-"+ lt.getLg() +"-"+ lt.getTemp(), ex);
			}
			
			
			if(pobtenidos && listaPs != null) {
				for(POddsPortalBO p : listaPs) {
					System.out.println("****************");
					System.out.println(p);
					System.out.println("****************");
				}
				
				try {
					if(listaPs.size() > 0) {
						ListaPs lAux = new ListaPs();
						lAux.setListaPs(listaPs);
						
						listaDeListas.getList().add(lAux);
						File dir = new File("../datos");
						if(!dir.exists()) {
							dir.mkdir();
						}
						
						File fichero = new File(dir.getAbsolutePath() + "/Partidos-"+ lt.getCountry() +"-"+ lt.getLg() +"-"+ lt.getTemp() + ".srz");
						
						if(!fichero.exists()) {
							fichero.createNewFile();
							System.out.println("Archivo creado vacio exitosamente :: " + fichero.getAbsolutePath());
						} 

						
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
						
						oos.writeObject(listaDeListas);
						
						System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
						
						oos.close();
						
						oos = null;
						
						listaDeListas = null;
						lAux = null;
						
					}	
				} catch(Exception ex) {
					logger.error("No fue posible crear el archivo srv "+ "/Partidos-"+ lt.getCountry() +"-"+ lt.getLg() +"-"+ lt.getTemp() + ".srz",ex);
				}

				
			}
			
		}
		
	}

}
