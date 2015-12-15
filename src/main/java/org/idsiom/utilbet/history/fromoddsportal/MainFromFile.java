package org.idsiom.utilbet.history.fromoddsportal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.history.fromoddsportal.bo.ListaPs;
import org.idsiom.utilbet.history.fromoddsportal.bo.POddsPortalBO;
import org.idsiom.utilbet.history.fromoddsportal.bo.ListaDeListas;
import org.idsiom.utilbet.history.fromoddsportal.dao.ConexionBaseDatos;
import org.idsiom.utilbet.history.fromoddsportal.dao.IOddsPortalDAO;
import org.idsiom.utilbet.history.fromoddsportal.dao.OddsPortalDAOImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class MainFromFile {

	static Logger logger = Logger.getLogger(MainFromFile.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		
		System.out.println("Procederemos a leer el archivo serializado!!!");
		
		File directorio = new File("../datos/");
		if(!directorio.exists()) {
			System.out.println("El directorio no existe... " + directorio.getAbsolutePath());
			return;
		}
		
		List<File> filesToProcess = new ArrayList<File>();
		File auxF;
		for(String f : directorio.list()) {
			auxF = new File( directorio.getAbsoluteFile() + "/" + f );
			
			if(auxF.isFile() && f.endsWith(".srz")) {
				filesToProcess.add(auxF);
			}
		}
		
		for(File fichero : filesToProcess) {
			
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));
			
			// Se lee el primer objeto
			Object aux;
			aux = ois.readObject();
			           
			IOddsPortalDAO dao = new OddsPortalDAOImpl();
			/*
			try {
				ConexionBaseDatos.abrirConexion("WKS0316", "1433", "sa", "Accusys123*");
			} catch (Exception e1) {
				logger.error(e1,e1);
				return;
			}
			*/
			
		    if (aux instanceof ListaDeListas) {
		    	
		    	File fileOut = crearFileOut(directorio, fichero, dao);
		    	
		    	ListaDeListas lista = (ListaDeListas)aux;
		    	
		    	System.out.println("Cant de listas = " + lista.getList().size());
		    	
		    	for(ListaPs l : lista.getList()) {
		    		System.out.println("lista de Partidos, size = " + l.getListaPs().size());
		    		for(POddsPortalBO p : l.getListaPs()) {
		    			
		    			try {
							dao.insertar(p, fileOut);
						} catch (SQLException e) {
							logger.error(e,e);
						} catch (Exception e) {
							logger.error(e,e);
						}
		    			
		    			System.out.println(" << INSERTADO >>" + p);
		    			
		    		}
		    	}
		    	
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

	private static File crearFileOut(File directorio, File fileInput, IOddsPortalDAO dao) throws IOException {
		
		File dirSalida = new File(directorio.getAbsolutePath() + "/sql/");
		
		if(!dirSalida.exists()) {
			dirSalida.mkdir();
		}
		
		String nameInput = fileInput.getName();
		
		System.out.println(nameInput);
		System.out.println(fileInput.getAbsolutePath());
		
		String nameOutPut = nameInput.split("\\.")[0] + ".sql";
		
		File fOut = new File(dirSalida.getAbsolutePath() + "/" + nameOutPut);
		fOut.createNewFile();
		
		dao.escribirEncabezado(fOut);
		
		return fOut;
	}

}
