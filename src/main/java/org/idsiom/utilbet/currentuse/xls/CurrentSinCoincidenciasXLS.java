package org.idsiom.utilbet.currentuse.xls;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.RUTA_ARCHIVO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.idsiom.utilbet.currentuse.ICurrentSinCoincidencias;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.comparator.ComparatorSimultaneos;
import org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent;


public class CurrentSinCoincidenciasXLS implements ICurrentSinCoincidencias {
	
	
public void writeExcelFile(List<CurrentPOddsPortal> listaPs) {
		
		List<CurrentPOddsPortal> listLocal50 = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listVisitante50 = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listLocalParejos = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listVisitanteParejos = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listLocal58 = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listLocalParejos2 = new ArrayList<CurrentPOddsPortal>();
		
		List<CurrentPOddsPortal> listVisitanteParejos2 = new ArrayList<CurrentPOddsPortal>();
		
		for (CurrentPOddsPortal item : listaPs) {

			if (item.getC1() != null) {
				if ((item.getC1() >= 1.7 && item.getC1() <=2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					listLocal50.add(item);
				}
				else if ((item.getC2() >= 1.7 && item.getC2() <=2) && (item.getcX() > item.getC2() && item.getcX() < item.getC1()) ) {
					
					listVisitante50.add(item);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() < item.getcX()) ) {
					
					listLocalParejos.add(item);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() < item.getcX()) ) {
					
					listVisitanteParejos.add(item);
				}
				else if ((item.getC1() >= 1.47 && item.getC1() <1.7) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					
					listLocal58.add(item);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() >= item.getcX()) ) {
									
					listLocalParejos2.add(item);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() >= item.getcX()) ) {
					
					listVisitanteParejos2.add(item);
				}
							
			}
		} // End for
		
		
		listLocal50 = eliminarCoincidencias(listLocal50, 1.85, 3.45, 4.15);
		listVisitante50 = eliminarCoincidencias(listVisitante50, 4.12, 3.53, 1.83);
		listLocalParejos = eliminarCoincidencias(listLocalParejos, 2.35,	3.27,	2.94);	
		listVisitanteParejos = eliminarCoincidencias(listVisitanteParejos, 2.92, 3.29, 2.36);
		listLocal58 = eliminarCoincidencias(listLocal58, 1.59, 3.88, 5.5);
		listLocalParejos2 = eliminarCoincidencias(listLocalParejos2, 2.18, 3.10, 	3.41);
		listVisitanteParejos2 = eliminarCoincidencias(listVisitanteParejos2, 3.32,3.06,	2.25);
		
		List<CurrentPOddsPortal> listSinCoincidencias = new ArrayList<CurrentPOddsPortal>();
		listSinCoincidencias.addAll(listLocal50);
		listSinCoincidencias.addAll(listVisitante50);
		listSinCoincidencias.addAll(listLocalParejos);
		listSinCoincidencias.addAll(listVisitanteParejos);
		listSinCoincidencias.addAll(listLocal58);
		listSinCoincidencias.addAll(listLocalParejos2); 
		listSinCoincidencias.addAll(listVisitanteParejos2);
		
		writeExcelFile_Final(listSinCoincidencias);
		
	}
	
	
private List<CurrentPOddsPortal> eliminarCoincidencias(List<CurrentPOddsPortal> list, double c1, double cX, double c2) {
	List<CurrentPOddsPortal> listSinCoincidencias = new ArrayList<CurrentPOddsPortal>();
	List<CurrentPOddsPortal> listSimultaneas = new ArrayList<CurrentPOddsPortal>();
	CurrentPOddsPortal pInicial;
	GregorianCalendar fechaFinHolgura = null;
	Boolean primeraVez = true;
	while(list.size() > 0) {
		pInicial = list.get(0);
		list.remove(0);
		
		listSimultaneas = getSimultaneas(pInicial, list, primeraVez, fechaFinHolgura);
		fechaFinHolgura = pInicial.getFechaGC();
		fechaFinHolgura.add(GregorianCalendar.MINUTE, 120 + ConstantesCurrent.MINS_PROXIMIDAD);
		
		if(listSimultaneas.size() > 0) {
			Collections.sort(listSimultaneas, new ComparatorSimultaneos(c1, cX, c2));
			
			listSinCoincidencias.add(listSimultaneas.get(0));
			
			list.removeAll(listSimultaneas);
		}
		
		primeraVez = false;
	}
	
	
	return listSinCoincidencias;
}


private List<CurrentPOddsPortal> getSimultaneas(CurrentPOddsPortal pInicial, List<CurrentPOddsPortal> list, boolean primeraVez, GregorianCalendar gc) {
	List<CurrentPOddsPortal> result = new ArrayList<CurrentPOddsPortal>();
	
	if( empiezaDespues(pInicial, gc, primeraVez) ) {
		result.add(pInicial);
		
		for(CurrentPOddsPortal p : list) {
			if(pInicial.getFecha().equals(p.getFecha())) {
				result.add(p);
			}
		}
	}
	
	return result;
}

public static boolean empiezaDespues(CurrentPOddsPortal p, GregorianCalendar fechaFinHolgura, Boolean primeraVez) {
	
	if(primeraVez) {
		return true;
	}
		
	
	return p.getFechaGC().after(fechaFinHolgura);
}


public void writeExcelFile_Final(List<CurrentPOddsPortal> listaPs) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheetLocal50 = workbook.createSheet("Local 50%");
		
		HSSFSheet sheetVisitante50 = workbook.createSheet("Visitante 50%");
		
		HSSFSheet sheetLocalParejos = workbook.createSheet("Local Parejos");
		
		HSSFSheet sheetVisitanteParejos = workbook.createSheet("Visitante Parejos");
		
		HSSFSheet sheetLocal58 = workbook.createSheet("Local 58%");
		
		HSSFSheet sheetLocalParejos2 = workbook.createSheet("Lo Parejos 2");
		
		HSSFSheet sheetVisitanteParejos2 = workbook.createSheet("Vi Parejos 2");
		
		int lineInicial = 3;
		int rowLocal50 = lineInicial, rowVisitante50 = lineInicial, rowLocalParejos = lineInicial,
				rowVisitanteParejos = lineInicial, rowLocal58 = lineInicial, 
						rowLoParejos2 = lineInicial, rowVisParejos2 = lineInicial;
		
		
		
		
		
		for (CurrentPOddsPortal item : listaPs) {

			int cellnum = 0;
			Row row = null;
			
			if (item.getC1() != null) {
				if ((item.getC1() >= 1.7 && item.getC1() <=2) && (item.getcX() > item.getC1() && item.getcX() < item.getC2()) ) {
					
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
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC2() > item.getC1() && item.getC2() >= item.getcX()) ) {
									
						row = sheetLocalParejos2.createRow(rowLoParejos2++);
				}
				else if ((item.getC1() > 2 && item.getcX() > 2 && item.getC2() > 2) && (item.getC1() > item.getC2() && item.getC1() >= item.getcX()) ) {
					
					row = sheetVisitanteParejos2.createRow(rowVisParejos2++);
				}
							
				Object[] datos = new Object[] {item.getFecha(), item.getCountry(), item.getLeague(), 
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
		calcularDesviacion(sheetLocalParejos2, rowLoParejos2, 0.35, 0.32, 0.33);
		calcularDesviacion(sheetVisitanteParejos2, rowVisParejos2, 0.33, 0.32, 0.35);
		
		// Pone cada Columna AutoSize
		for(int i = 1; i <= 9; i++) {
			sheetLocal50.autoSizeColumn(i);
			sheetVisitante50.autoSizeColumn(i);
			sheetLocalParejos.autoSizeColumn(i);
			sheetVisitanteParejos.autoSizeColumn(i);
			sheetLocal58.autoSizeColumn(i);
			sheetLocalParejos2.autoSizeColumn(i);
			sheetVisitanteParejos2.autoSizeColumn(i);
		}
		
		
		
		
		int rowCount = 0;
		
		
		
		FileOutputStream out;
		try {
		    out = 
		            new FileOutputStream(new File(RUTA_ARCHIVO+"\\PartidosCurrent-SinCoincidencias.xls"));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		    
		 } catch (FileNotFoundException e) {
		    e.printStackTrace();
		    
		    System.out.println("Sos boludo o sos de Racing???????? CIERRA EL ARCHIVO Y PULSA 1...");
		    
		    Scanner sAux = new Scanner(System.in);
		    Integer iAux = sAux.nextInt();
		    System.out.println("Usted respondio " + iAux);
		    sAux.close();
		    
		    
			try {
				out = new FileOutputStream(new File(RUTA_ARCHIVO+"\\PartidosCurrent-SinCoincidencias.xls"));
				
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
	
    private void calcularDesviacion(HSSFSheet sheet, int rowCount, double difOverL, double difOverX, double difOverV) {
		int cellNum = 0;
		int cantJuegos = rowCount;
		Row row = sheet.createRow(rowCount++);
		
		if (cantJuegos > 0) {
			Cell cellA = row.createCell(cellNum);
			cellA.setCellFormula("SUM(A4:A"+cantJuegos+")");
			
			cellNum = 6;
			Cell cellF = row.createCell(cellNum);
			cellF.setCellFormula("AVERAGE(G4:G"+cantJuegos+")");
			
			cellNum = 7;
			Cell cellG = row.createCell(cellNum);
			cellG.setCellFormula("AVERAGE(H4:H"+cantJuegos+")");
			
			cellNum = 8;
			Cell cellH = row.createCell(cellNum);
			cellH.setCellFormula("AVERAGE(I4:I"+cantJuegos+")");
			
			cellNum = 10;
			Cell cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("SUM(K4:K"+cantJuegos+")");
			
			cellNum = 11;
			Cell cellK = row.createCell(cellNum);
			cellK.setCellFormula("SUM(L4:L"+cantJuegos+")");
			
			cellNum = 12;
			Cell cellL = row.createCell(cellNum);
			cellL.setCellFormula("SUM(M4:M"+cantJuegos+")");
			
			
			//----------------------->>>>>
			// Se calculan las inversas de los promedios
			row = sheet.createRow(rowCount++);
			
			cellNum = 6;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("1/G"+(cantJuegos+1));
			
			cellNum = 7;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("1/H"+(cantJuegos+1));
			
			cellNum = 8;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("1/I"+(cantJuegos+1));
			
			cellNum = 4;
			Cell cellD = row.createCell(cellNum);
			cellD.setCellFormula("G"+rowCount+" + H"+rowCount + " + I"+(rowCount));
			
			cellNum = 3;
			Cell cellC = row.createCell(cellNum);
			cellC.setCellFormula("E"+rowCount+" - 1");
			
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
			
			cellNum = 6;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("A"+rowCount+" * D"+(rowCount-1) );
			
			cellNum = 7;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("B"+rowCount+" * D"+(rowCount-1) );
			
			cellNum = 8;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("C"+rowCount+" * D"+(rowCount-1) );
			
			
			//----------------------->>>>>
			// Se calcula la Expectativa
			row = sheet.createRow(rowCount++); // Se deja intencionalmente una linea intermedio en blanco
			row = sheet.createRow(rowCount++); 
			
			cellNum = 3;
			cellC = row.createCell(cellNum);
			cellC.setCellFormula("I"+rowCount+" + G"+rowCount+" + H"+rowCount);
			
			cellNum = 4;
			cellD = row.createCell(cellNum);
			cellD.setCellValue("Expectativa");
			
			cellNum = 6;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("G"+(rowCount-3)+" - G"+(rowCount-2) );
			
			cellNum = 7;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("H"+(rowCount-3)+" - H"+(rowCount-2) );
			
			cellNum = 8;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("I"+(rowCount-3)+" - I"+(rowCount-2) );
			
			cellNum = 10;
			cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("G"+(rowCount)+"*A"+(cantJuegos+1));
			
			cellNum = 11;
			cellK = row.createCell(cellNum);
			cellK.setCellFormula("H"+(rowCount)+"*A"+(cantJuegos+1));
			
			cellNum = 12;
			cellL = row.createCell(cellNum);
			cellL.setCellFormula("I"+(rowCount)+"*A"+(cantJuegos+1));
			
			
			//--------------------->>>>
			//-- Se coloca la realidad
			row = sheet.createRow(rowCount++);
			
			cellNum = 3;
			cellC = row.createCell(cellNum);
			cellC.setCellFormula("I"+rowCount+" + G"+rowCount+" + H"+rowCount);
			
			cellNum = 4;
			cellD = row.createCell(cellNum);
			cellD.setCellValue("Realidad");
			
			cellNum = 6;
			cellF = row.createCell(cellNum);
			cellF.setCellFormula("K"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 7;
			cellG = row.createCell(cellNum);
			cellG.setCellFormula("L"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 8;
			cellH = row.createCell(cellNum);
			cellH.setCellFormula("M"+(cantJuegos+1)+"/A"+(cantJuegos+1));
			
			cellNum = 10;
			cellJ = row.createCell(cellNum);
			cellJ.setCellFormula("K"+(cantJuegos+1));
			
			cellNum = 11;
			cellK = row.createCell(cellNum);
			cellK.setCellFormula("L"+(cantJuegos+1));
			
			cellNum = 12;
			cellL = row.createCell(cellNum);
			cellL.setCellFormula("M"+(cantJuegos+1));
			
			//-------------------------->>>>>>>>>>>>>
			// --- Se Calcula la Desviacion
			row = sheet.createRow(rowCount++);
			agregarDesviacion(row, rowCount);
			
			row = sheet.createRow(0);
			agregarDesviacion(row, rowCount);
			
			cellA = row.createCell(0);
			cellA.setCellFormula("SUM(A4:A"+cantJuegos+")");

		}
	}

	
	private void agregarDesviacion(Row row, int rowCount) {
		int cellNum = 0;
		
		cellNum = 4;
		Cell cellD = row.createCell(cellNum);
		cellD.setCellValue("Desviacion");
		
		cellNum = 6;
		Cell cellF = row.createCell(cellNum);
		cellF.setCellFormula("(K"+(rowCount)+" * 100)/K"+(rowCount-2));
		
		cellNum = 7;
		Cell cellG = row.createCell(cellNum);
		cellG.setCellFormula("(L"+(rowCount)+" * 100)/L"+(rowCount-2));
		
		cellNum = 8;
		Cell cellH = row.createCell(cellNum);
		cellH.setCellFormula("(M"+(rowCount)+" * 100)/M"+(rowCount-2));
		
		cellNum = 10;
		Cell cellJ = row.createCell(cellNum);
		cellJ.setCellFormula("K"+(rowCount-1)+" - K"+(rowCount-2));
		
		cellNum = 11;
		Cell cellK = row.createCell(cellNum);
		cellK.setCellFormula("L"+(rowCount-1)+" - L"+(rowCount-2));
		
		cellNum = 12;
		Cell cellL = row.createCell(cellNum);
		cellL.setCellFormula("M"+(rowCount-1)+" - M"+(rowCount-2));		
	}
	
}
