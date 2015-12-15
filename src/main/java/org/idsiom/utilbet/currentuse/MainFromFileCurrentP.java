package org.idsiom.utilbet.currentuse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.idsiom.utilbet.currentuse.bo.ListaPartSeri;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MainFromFileCurrentP {

	static Logger logger = Logger.getLogger(MainFromFileCurrentP.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		System.out.println("Procederemos a leer el archivo serializado!!!");
		
		File fichero = new File("../datos/PartidosCurrent.srz");
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
		    	
		    	ListaPartSeri lista = (ListaPartSeri)aux;
		    	
		    	System.out.println("Cant Partidos = " + lista.getListaPs().size());
		    	
		    	
		    	File ficheroCSV = new File("../datos/PartidosCurrent.csv");
		    	
		    	if(ficheroCSV.exists()) {
		    		ficheroCSV.delete();
		    	}
		    	
				if(!ficheroCSV.exists()) {
					ficheroCSV.createNewFile();
					System.out.println("Archivo creado vacio exitosamente :: " + ficheroCSV.getAbsolutePath());
				}
		    	
			    BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroCSV));
		    	
		    	
		    		for(CurrentPOddsPortal p : lista.getListaPs()) {
		    			
		    			/*
		    			try {
							dao.insertar(p, fileOut);
						} catch (SQLException e) {
							logger.error(e,e);
						} catch (Exception e) {
							logger.error(e,e);
						}
		    			*/
		    			
		    			bw.write( p.getCSVFormat() );
		    			bw.write( "\n" );
		    			
		    			System.out.println(" << INSERTADO >>" + p);
		    			
		    		}
		    		
		    	bw.close();	
		    		
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
