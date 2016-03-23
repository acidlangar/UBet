package org.idsiom.utilbet.currentuse;

import java.io.IOException;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;
import org.idsiom.utilbet.currentuse.xls.SeguimientoPyckXLS;
import org.idsiom.utilbet.currentuse.xls.XLSException;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.*;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.RUTA_ARCHIVO;

import org.junit.Test;

public class TestXLSRendimiento {
	
	public void testReadExcel() {
		ISeguimientoPyckPersistencia s = new SeguimientoPyckXLS();
		List<PyckBO> list = null;
		try {
			list = s.getPyckPorDefinir();
		} catch (IOException e) {
			System.out.println("Ocurrio un problema inesperado");
			e.printStackTrace();
		}
		
		System.out.println("list.size = " + list.size());
		for(PyckBO p : list) {
			System.out.println(p);
		}
		
	}
	
	
	public void testGuardarApuesta() {
		ISeguimientoPyckPersistencia s = new SeguimientoPyckXLS();
		CurrentPOddsPortal partidoOP = null; 
		PartidoPyckioBO partidoPIO = null; 
		PyckBO pyck = null;
		try {
			
			s.guardarApuesta(partidoOP, partidoPIO, pyck);
		} catch (XLSException e) {
			System.out.println("Ocurrio un problema inesperado");
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	

	@Test
	public void testShiftRows() throws Exception {
	        Workbook wb = new HSSFWorkbook();
	        Sheet sheet = wb.createSheet("Sheet1");

	        Row row1 = sheet.createRow(1);
	        row1.createCell(0).setCellValue(1);

	        Row row2 = sheet.createRow(4);
	        row2.createCell(1).setCellValue(2);

	        Row row3 = sheet.createRow(5);
	        row3.createCell(2).setCellValue(3);

	        Row row4 = sheet.createRow(6);
	        row4.createCell(3).setCellValue(4);

	        Row row5 = sheet.createRow(9);
	        row5.createCell(4).setCellValue(5);

	        // Shift rows 6 - 11 on the spreadsheet to the top (rows 0 - 5)
	        //sheet.shiftRows(5, 10, -4);
	        sheet.shiftRows(5, 10, 1);

	        FileOutputStream fileOut = new FileOutputStream(RUTA_ARCHIVO + "\\shiftRows.xls");
	        wb.write(fileOut);
	        fileOut.close();

	    }


	
	
	
	
	
	
}
