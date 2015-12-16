package org.idsiom.utilbet.currentuse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
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

/*
 * MainCurrentUseFromOP
 * */
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
		
		HSSFSheet sheetLocal50 = workbook.createSheet("Local 50%");
		
		HSSFSheet sheetVisitante50 = workbook.createSheet("Visitante 50%");
		
		HSSFSheet sheetLocalParejos = workbook.createSheet("Local Parejos");
		
		HSSFSheet sheetVisitanteParejos = workbook.createSheet("Visitante Parejos");
		
		HSSFSheet sheetOtros = workbook.createSheet("Otros");

		int rowLocal50 = 0, rowVisitante50 = 0, rowLocalParejos = 0,
				rowVisitanteParejos = 0, rowOtros = 0;
		
		for (CurrentPOddsPortal item : listaPs) {

			int cellnum = 0;
			Row row = null;
			
			if ("O".equals(item.getResultFinal())) {
				
				row = sheetOtros.createRow(rowOtros++);
			}
			else if ((item.getC1() >= 1.7 && item.getC1() <=2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
				
				row = sheetLocal50.createRow(rowLocal50++);
			}
			else if ((item.getC2() >= 1.7 && item.getC2() <=2) && (item.getcX() > item.getC2() && item.getcX() < item.getC1()) ) {
				
				row = sheetVisitante50.createRow(rowVisitante50++);
			}
			else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() < item.getcX()) ) {
				
				row = sheetLocalParejos.createRow(rowLocalParejos++);
			}
			else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() < item.getcX()) ) {
				
				row = sheetVisitanteParejos.createRow(rowVisitanteParejos++);
			}
			else {
				
				row = sheetOtros.createRow(rowOtros++);
			}
			
			Object[] datos = new Object[] {item.getFecha(), item.getCountry(), 
					item.getEquipos(), item.getrStr(), item.getC1(), 
					item.getcX(), item.getC2(), item.getResultFinal()};

			// Columna para partido ocurrido o no
			Cell cellA = row.createCell(cellnum++);
			if (item.getResultFinal() != 'O') {
				cellA.setCellValue(1);
			}
			else {				
				cellA.setCellValue(0);
			}

			// Columnas para datos de los partidos
			for (int i=0; i < datos.length; i++) {

				Cell cell = row.createCell(cellnum++);
				
		        if(datos[i] instanceof Date) {
		            cell.setCellValue((Date)datos[i]);
		        }
		        else if(datos[i] instanceof Boolean) {
		            cell.setCellValue((Boolean)datos[i]);
		        }
		        else if(datos[i] instanceof String) {
		            cell.setCellValue((String)datos[i]);
		        }
		        else if(datos[i] instanceof Character) {
		            cell.setCellValue(String.valueOf(datos[i]));
		        }
		        else if(datos[i] instanceof Double) {
		            cell.setCellValue((Double)datos[i]);
		        }
			}
			
			// Columnas para resultado de partido
			if (item.getResultFinal() != 'O') {
				
				Cell cellK = row.createCell(cellnum++);
				Cell cellL = row.createCell(cellnum++);
				Cell cellM = row.createCell(cellnum++);
				
				cellK.setCellValue(item.getResultFinal() == '1' ? 1 : 0);
				cellL.setCellValue(item.getResultFinal() == 'X' ? 1 : 0);
				cellM.setCellValue(item.getResultFinal() == '2' ? 1 : 0);
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
