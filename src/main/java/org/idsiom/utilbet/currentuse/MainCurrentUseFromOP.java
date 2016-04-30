package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.xls.CurrentDesviacionXLS;
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.RUTA_ARCHIVO;


/*
 * MainCurrentUseFromOP
 * */
public class MainCurrentUseFromOP {
	
	static Logger logger = Logger.getLogger(MainCurrentUseFromOP.class);
	
	

	public static void main(String[] args) throws IOException {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		IOddsPortalCurrentUseInterlocutor interlocutor = OddsPortalInterCurrentUseImpl.getInstance();
		ListPartidosSerializable listaPs = new ListPartidosSerializable();
		
		
		
		try {
 			listaPs = interlocutor.getPs(false);
		} catch(Exception ex) {
			logger.error("No fue posible obtener ps ", ex);
		}
		
			
		if(listaPs != null) {
			for(CurrentPOddsPortal p : listaPs.getPartidosHistory()) {
				System.out.println("**********HISTORIA******");
				System.out.println(p);
				System.out.println("****************");
			}
			
			for(CurrentPOddsPortal p : listaPs.getListaPsHoyFuturo()) {
				System.out.println("**********HOY Y FUTURO******");
				System.out.println(p);
				System.out.println("****************");
			}
			
			System.out.println("Cant de Historia " + listaPs.getPartidosHistory().size());
			
			System.out.println("Cant de Futuro " + listaPs.getListaPsHoyFuturo().size());
			
			try {
				if(listaPs.getPartidosHistory().size() > 0 && listaPs.getListaPsHoyFuturo().size() >= 0 ) {
					
					File dir = new File(RUTA_ARCHIVO);
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
					oos.writeObject(listaPs);
					System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
					
					//Se escribe directamente en el archivo excel
					ICurrentDesviacionPersistencia currentDesvPersistencia = new CurrentDesviacionXLS(); 
					currentDesvPersistencia.writeExcelFile(listaPs.getListaPs());
					
					oos.close();
					oos = null;
					
				}	
			} catch(Exception ex) {
				logger.error("No fue posible crear el archivo srv "+ "/PartidosCurrent.srz",ex);
			}
		}
		
		System.out.println("  ----------------          >>>>>>>>>>>>>>>      <<<<<<<<<<<<<<<   ------------");
		System.out.println("  ----------------          >>>>>>>>>>>>>>>   Se inicializará el seguimiento...   <<<<<<<<<<<<<<<   ------------");
		System.out.println("  ----------------          >>>>>>>>>>>>>>>      <<<<<<<<<<<<<<<   ------------");
		MainFromFileCurrentP.main(args);
	}

	
	
}
