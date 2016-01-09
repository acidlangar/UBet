package org.idsiom.utilbet.currentuse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.idsiom.utilbet.currentuse.bo.ListaPartSeri;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MainFromFileCurrentP {

	static Logger logger = Logger.getLogger(MainFromFileCurrentP.class);
	
	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		System.out.println("Procederemos a leer el archivo serializado!!!");
		
		File fichero = new File(RUTA_ARCHIVO + "/PartidosCurrent.srz");
		if(!fichero.exists()) {
			System.out.println("El archivo no existe... " + fichero.getAbsolutePath());
			return;
		}
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));
			
			// Se lee el primer objeto
			Object aux;
			aux = ois.readObject();
			           
			/*
			try {
				ConexionBaseDatos.abrirConexion("WKS0316", "1433", "sa", "Accusys123*");
			} catch (Exception e1) {
				logger.error(e1,e1);
				return;
			}
			*/
			
		    if (aux instanceof ListaPartSeri) {
		    	
		    	ListaPartSeri lAux = (ListaPartSeri)aux;
		    	
		    	System.out.println("Cant Partidos = " + lAux.getListaPs().size());
		    	
		    	
		    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
				oos.writeObject(lAux);
				System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
				
				//Se escribe directamente en el archivo excel
				MainCurrentUseFromOP.writeExcelFile(lAux.getListaPs());
				
				oos.close();
				oos = null;
				lAux = null;
		    		
		    } else {
		    	System.out.println("No corresponde a instancia esperada");
		    }
		    
			ois.close();
			
			/*
			try {
				ConexionBaseDatos.cerrarConexion();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e,e);
			}
			*/
			
		} catch (ClassNotFoundException e) {
			logger.error(e,e);
		} catch (FileNotFoundException e) {
			logger.error(e,e);
		} catch (IOException e) {
			logger.error(e,e);
		}
		
	}


}
