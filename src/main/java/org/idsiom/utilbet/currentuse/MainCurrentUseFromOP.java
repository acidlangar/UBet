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
import org.idsiom.utilbet.currentuse.bo.ListPartidosSerializable;

/*
 * MainCurrentUseFromOP
 * */
public class MainCurrentUseFromOP {
	
	static Logger logger = Logger.getLogger(MainCurrentUseFromOP.class);
	
	public static String RUTA_ARCHIVO = "C:\\DEVTOOLS";

	public static void main(String[] args) {
		DOMConfigurator.configure("./src/main/java/conf/log4j-config.xml");
		IOddsPortalCurrentUseInterlocutor interlocutor = new OddsPortalInterCurrentUseImpl();
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
				if(listaPs.getPartidosHistory().size() > 0 && listaPs.getListaPsHoyFuturo().size() > 0 ) {
					
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
					writeExcelFile(listaPs.getListaPs());
					
					oos.close();
					oos = null;
					
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
		
		HSSFSheet sheetLocal58 = workbook.createSheet("Local 58%");
		
		HSSFSheet sheetVisitante58 = workbook.createSheet("Visitante 58%");

		HSSFSheet sheetLocalParejos2 = workbook.createSheet("Lo Parejos 2");
		
		HSSFSheet sheetVisitanteParejos2 = workbook.createSheet("Vi Parejos 2");
		
		HSSFSheet sheetLocSuperFav = workbook.createSheet("LoSuperFav");
		
		HSSFSheet sheetLocMuyFav = workbook.createSheet("LoMuyFav");
		
		HSSFSheet sheetLocFav = workbook.createSheet("LoFav");
		
		HSSFSheet sheetOtros = workbook.createSheet("Otros");

		int rowLocal50 = 0, rowVisitante50 = 0, rowLocalParejos = 0,
				rowVisitanteParejos = 0, rowOtros = 0, rowLocal58 = 0, rowVisitante58 = 0,
						rowLoParejos2 = 0, rowVisParejos2 = 0, rowLoSuperFav = 0,
						    rowLocMuyFav = 0, rowLocFav = 0;
		
		for (CurrentPOddsPortal item : listaPs) {

			int cellnum = 0;
			Row row = null;
			
			if (item.getC1() != null) {
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
				else if ((item.getC1() >= 1.47 && item.getC1() <1.7) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					
					row = sheetLocal58.createRow(rowLocal58++);
				}
				else if ((item.getC2() >= 1.47 && item.getC2() <1.7) && (item.getcX() > item.getC2() && item.getcX() < item.getC1()) ) {
					
					row = sheetVisitante58.createRow(rowVisitante58++);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() >= item.getcX()) ) {
									
						row = sheetLocalParejos2.createRow(rowLoParejos2++);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() >= item.getcX()) ) {
					
					row = sheetVisitanteParejos2.createRow(rowVisParejos2++);
				}
				else if ((item.getC1() > 1 && item.getC1() <=1.2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					
					row = sheetLocSuperFav.createRow(rowLoSuperFav++);
				}
				else if ((item.getC1() > 1.2 && item.getC1() <=1.4) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					
					row = sheetLocMuyFav.createRow(rowLocMuyFav++);
				}
				else if ((item.getC1() > 1.4 && item.getC1() <1.7) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
	
					row = sheetLocFav.createRow(rowLocFav++);
				}
				
				else {
					
					row = sheetOtros.createRow(rowOtros++);
				}
				
				Object[] datos = new Object[] {item.getFecha(), item.getLeague(), 
						item.getEquipos(), item.getrStr(), item.getC1(), 
						item.getcX(), item.getC2(), item.getResultFinal()};
	
				// Columna para partido ocurrido o no
				Cell cellA = row.createCell(cellnum++);
				if (item.getResultFinal() == '1' || item.getResultFinal() == '2' || item.getResultFinal() == 'X') {
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
		}
		
		//------>>>
		// -- Calcular la desviacion
		calcularDesviacion(sheetLocal50, rowLocal50, 0.38, 0.33, 0.29);
		calcularDesviacion(sheetVisitante50, rowVisitante50, 0.29, 0.33, 0.38);
		calcularDesviacion(sheetLocalParejos, rowLocalParejos, 0.35, 0.32, 0.33);
		calcularDesviacion(sheetVisitanteParejos, rowVisitanteParejos, 0.33, 0.32, 0.35);
		calcularDesviacion(sheetLocal58, rowLocal58, 0.38, 0.33, 0.29);
		calcularDesviacion(sheetVisitante58, rowVisitante58, 0.29, 0.33, 0.38);
		calcularDesviacion(sheetLocalParejos2, rowLoParejos2, 0.35, 0.32, 0.33);
		calcularDesviacion(sheetVisitanteParejos2, rowVisParejos2, 0.33, 0.32, 0.35);
		calcularDesviacion(sheetLocSuperFav, rowLoSuperFav, 0.60, 0.30, 0.10);
		calcularDesviacion(sheetLocMuyFav, rowLocMuyFav, 0.60, 0.30, 0.10);
		calcularDesviacion(sheetLocFav, rowLocFav, 0.60, 0.30, 0.10);
		
		
		FileOutputStream out;
		try {
		    out = 
		            new FileOutputStream(new File(RUTA_ARCHIVO+"\\PartidosCurrent.xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    
		    System.out.println("Sos boludo o sos de Racing???????? CIERRA EL ARCHIVO Y PULSA 1...");
		    
		    (new Scanner(System.in)).nextInt();
		    
		    
			try {
				out = new FileOutputStream(new File(RUTA_ARCHIVO+"\\PartidosCurrent.xls"));
				
				workbook.write(out);
			    out.close();
			    System.out.println("Excel written successfully..");
			    
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
		    } catch (IOException e1) {
			    e1.printStackTrace();
			}
		    
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	
	
	private static  void calcularDesviacion(HSSFSheet sheet, int rowCount, double difOverL, double difOverX, double difOverV) {
		int cellNum = 0;
		int cantJuegos = rowCount;
		Row row = sheet.createRow(rowCount++);
		
		if (cantJuegos > 0) {
			Cell cellA = row.createCell(cellNum);
			cellA.setCellFormula("SUM(A1:A"+cantJuegos+")");
			
			cellNum = 5;
			Cell cellF = row.createCell(cellNum);
			cellF.setCellFormula("AVERAGE(F1:F"+cantJuegos+")");
			
			cellNum = 6;
			Cell cellG = row.createCell(cellNum);
			cellG.setCellFormula("AVERAGE(G1:G"+cantJuegos+")");
			
			cellNum = 7;
			Cell cellH = row.createCell(cellNum);
			cellH.setCellFormula("AVERAGE(H1:H"+cantJuegos+")");
			
			cellNum = 9;
			Cell cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("SUM(J1:J"+cantJuegos+")");
			
			cellNum = 10;
			Cell cellK = row.createCell(cellNum);
			cellK.setCellFormula("SUM(K1:K"+cantJuegos+")");
			
			cellNum = 11;
			Cell cellL = row.createCell(cellNum);
			cellL.setCellFormula("SUM(L1:L"+cantJuegos+")");
			
			
			//----------------------->>>>>
			// Se calculan las inversas de los promedios
			row = sheet.createRow(rowCount++);
			
			cellNum = 5;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("1/F"+(cantJuegos+1));
			
			cellNum = 6;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("1/G"+(cantJuegos+1));
			
			cellNum = 7;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("1/H"+(cantJuegos+1));
			
			cellNum = 3;
			Cell cellD = row.createCell(cellNum);
			cellD.setCellFormula("F"+rowCount+" + G"+rowCount + " + H"+(rowCount));
			
			cellNum = 2;
			Cell cellC = row.createCell(cellNum);
			cellC.setCellFormula("D"+rowCount+" - 1");
			
			//----------------------->>>>>
			// Se estima el overround usado
			row = sheet.createRow(rowCount++);
			
			cellNum = 0;
			cellA = row.createCell(cellNum);
			cellA.setCellValue(difOverL);
			
			cellNum = 1;
			Cell cellB = row.createCell(cellNum);
			cellB.setCellValue(difOverX);
			
			cellNum = 2;
			cellC = row.createCell(cellNum);
			cellC.setCellValue(difOverV);
			
			cellNum = 5;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("A"+rowCount+" * C"+(rowCount-1) );
			
			cellNum = 6;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("B"+rowCount+" * C"+(rowCount-1) );
			
			cellNum = 7;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("C"+rowCount+" * C"+(rowCount-1) );
			
			
			//----------------------->>>>>
			// Se calcula la Expectativa
			row = sheet.createRow(rowCount++); // Se deja intencionalmente una linea intermedio en blanco
			row = sheet.createRow(rowCount++); 
			
			cellNum = 2;
			cellC = row.createCell(cellNum);
			cellC.setCellFormula("F"+rowCount+" + G"+rowCount+" + H"+rowCount);
			
			cellNum = 3;
			cellD = row.createCell(cellNum);
			cellD.setCellValue("Expectativa");
			
			cellNum = 5;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("F"+(rowCount-3)+" - F"+(rowCount-2) );
			
			cellNum = 6;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("G"+(rowCount-3)+" - G"+(rowCount-2) );
			
			cellNum = 7;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("H"+(rowCount-3)+" - H"+(rowCount-2) );
			
			cellNum = 9;
			cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("F"+(rowCount)+"*A"+(cantJuegos+1));
			
			cellNum = 10;
			cellK = row.createCell(cellNum);
			cellK.setCellFormula("G"+(rowCount)+"*A"+(cantJuegos+1));
			
			cellNum = 11;
			cellL = row.createCell(cellNum);
			cellL.setCellFormula("H"+(rowCount)+"*A"+(cantJuegos+1));
			
			
			//--------------------->>>>
			//-- Se coloca la realidad
			row = sheet.createRow(rowCount++);
			
			cellNum = 2;
			cellC = row.createCell(cellNum);
			cellC.setCellFormula("F"+rowCount+" + G"+rowCount+" + H"+rowCount);
			
			cellNum = 3;
			cellD = row.createCell(cellNum);
			cellD.setCellValue("Realidad");
			
			cellNum = 5;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("J"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 6;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("K"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 7;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("L"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 9;
			cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("J"+(cantJuegos+1));
			
			cellNum = 10;
			cellK = row.createCell(cellNum);
			cellK.setCellFormula("K"+(cantJuegos+1));
			
			cellNum = 11;
			cellL = row.createCell(cellNum);
			cellL.setCellFormula("L"+(cantJuegos+1));
			
			//-------------------------->>>>>>>>>>>>>
			// --- Se Calcula la Desviacion
			row = sheet.createRow(rowCount++);
			
			cellNum = 3;
			cellD = row.createCell(cellNum);
			cellD.setCellValue("Desviacion");
			
			cellNum = 5;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("F"+(rowCount-1)+" - F"+(rowCount-2));
			
			cellNum = 6;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("G"+(rowCount-1)+" - G"+(rowCount-2));
			
			cellNum = 7;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("H"+(rowCount-1)+" - H"+(rowCount-2));
			
			cellNum = 9;
			cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("J"+(rowCount-1)+" - J"+(rowCount-2));
			
			cellNum = 10;
			cellK = row.createCell(cellNum);
			cellK.setCellFormula("K"+(rowCount-1)+" - K"+(rowCount-2));
			
			cellNum = 11;
			cellL = row.createCell(cellNum);
			cellL.setCellFormula("L"+(rowCount-1)+" - L"+(rowCount-2));
		}
	}
	
}
