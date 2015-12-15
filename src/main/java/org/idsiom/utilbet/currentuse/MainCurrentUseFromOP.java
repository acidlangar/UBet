package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.interlocutor.IOddsPortalCurrentUseInterlocutor;
import org.idsiom.utilbet.currentuse.interlocutor.OddsPortalInterCurrentUseImpl;
import org.idsiom.utilbet.currentuse.bo.ListaPartSeri;

public class MainCurrentUseFromOP {
	
	static Logger logger = Logger.getLogger(MainCurrentUseFromOP.class);
	
	//public static String RUTA_ARCHIVO = "../datos";
	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";

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
					oos.writeObject(lAux);
					System.out.println(" Archivo creado exitosamente :: " + fichero.getAbsolutePath());
					
					//Se escribe directamente en el archivo excel
					writeExcelFile(listaPs);
					
					oos.close();
					oos = null;
					lAux = null;
				}	
			} catch(Exception ex) {
				logger.error("No fue posible crear el archivo srv "+ "/PartidosCurrent.srz",ex);
			}
		}
	}

	public static void writeExcelFile(List<CurrentPOddsPortal> listaPs) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet("PartidosCurrentSheet");
		 
		Map<Long, Object[]> data = new HashMap<Long, Object[]>();
		Long clave = 1L;
		
		for (CurrentPOddsPortal item : listaPs) {
			
			data.put(clave++, new Object[] {item.getFecha(), item.getCountry(), 
					item.getEquipos(), item.getgL()+"-"+item.getgV(), 
					item.getResultFinal(), item.getC1(), item.getcX(), item.getC2()});
		}
		
		Set<Long> keyset = data.keySet();
		int rownum = 0;
		for (Long key : keyset) {
		    Row row = sheet.createRow(rownum++);
		    Object [] objArr = data.get(key);
		    int cellnum = 0;
		    for (Object obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        if(obj instanceof Date) 
		            cell.setCellValue((Date)obj);
		        else if(obj instanceof Boolean)
		            cell.setCellValue((Boolean)obj);
		        else if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Double)
		            cell.setCellValue((Double)obj);
		    }
		}
		 
		try {
		    FileOutputStream out = 
		            new FileOutputStream(new File(RUTA_ARCHIVO+"\\PartidosCurrent.xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
}
