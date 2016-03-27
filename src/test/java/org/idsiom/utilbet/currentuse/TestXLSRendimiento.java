package org.idsiom.utilbet.currentuse;

import java.util.ArrayList;
import java.util.List;

import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.EstadoPyck;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;
import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.idsiom.utilbet.currentuse.xls.SeguimientoPyckXLS;
import org.idsiom.utilbet.currentuse.xls.XLSException;

import java.io.File;
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
		} catch (XLSException e) {
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

	@Test
	public void testCompleto() {
		// Asegurarme que no exista archivo, por tanto en caso de existir, eliminar 
		File fileRend = new File(RUTA_ARCHIVO + "\\Rendimiento.xls");
		
		if(fileRend.exists()) {
			if(fileRend.delete()) {
				System.out.println("delete sucessfull");
			} else {
				System.out.println("Imposible to delete");				
			}
		}
		
		ISeguimientoPyckPersistencia seg = new SeguimientoPyckXLS();
		// Imprimir el Rendimiento, los pycks pendientes, Revisar el Excel
		imprimirStatus(seg);
		
		try {
			//CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck
			CurrentPOddsPortal partidoOP = new CurrentPOddsPortal();
			PartidoPyckioBO partidoPIO = new PartidoPyckioBO(); 
			PyckBO pyck = new PyckBO();
			
			partidoOP.setEquipos("Argentina - Chile");
			partidoOP.setFecha("20160325 20:00");
			partidoOP.setCountry("Mundial");
			partidoOP.setLeague("Eliminatorias Suramericanas");
			partidoOP.setC1(3.2);
			partidoOP.setC2(3.1);
			partidoOP.setcX(3.4);
			
			partidoPIO.setEquipoLocal("Argentina");
			partidoPIO.setEquipoVisitante("Chile");
			
			pyck.setEstado(EstadoPyck.POR_DEFINIR);
			pyck.setPyck(ResultadoPartidoBO.VISITANTE);
			pyck.setStake(1);
			pyck.setPartido(partidoOP);
			
			seg.guardarApuesta(partidoOP, partidoPIO, pyck);
			
			
			
			List<PyckBO> listResultados = new ArrayList<PyckBO>();
			
			pyck.setAcierto(false);
			pyck.setEstado(EstadoPyck.FINALIZADO);
			listResultados.add(pyck);
			
			seg.guardarResultadosPycks(listResultados);
		
			System.out.println("Fin actualizacion resultado 1");
			imprimirStatus(seg);
			
		} catch (XLSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			//CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck
			CurrentPOddsPortal partidoOP = new CurrentPOddsPortal();
			PartidoPyckioBO partidoPIO = new PartidoPyckioBO(); 
			PyckBO pyck = new PyckBO();
			
			partidoOP.setEquipos("Peru - Venezuela");
			partidoOP.setFecha("20160325 22:00");
			partidoOP.setCountry("Mundial");
			partidoOP.setLeague("Eliminatorias Suramericanas");
			partidoOP.setC1(3.2);
			partidoOP.setC2(3.1);
			partidoOP.setcX(3.4);
			
			partidoPIO.setEquipoLocal("Peru");
			partidoPIO.setEquipoVisitante("Venezuela");
			
			pyck.setEstado(EstadoPyck.POR_DEFINIR);
			pyck.setPyck(ResultadoPartidoBO.VISITANTE);
			pyck.setStake(1);
			pyck.setPartido(partidoOP);
			
			seg.guardarApuesta(partidoOP, partidoPIO, pyck);
			
			System.out.println("Apuesta nuevo juego");
			
			
			List<PyckBO> listResultados = new ArrayList<PyckBO>();
			
			pyck.setAcierto(false);
			pyck.setEstado(EstadoPyck.FINALIZADO);
			listResultados.add(pyck);
			
			seg.guardarResultadosPycks(listResultados);
			
			
		} catch (XLSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			//CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck
			CurrentPOddsPortal partidoOP = new CurrentPOddsPortal();
			PartidoPyckioBO partidoPIO = new PartidoPyckioBO(); 
			PyckBO pyck = new PyckBO();
			
			partidoOP.setEquipos("Brasil - Uruguay");
			partidoOP.setFecha("20160326 08:00");
			partidoOP.setCountry("Mundial");
			partidoOP.setLeague("Eliminatorias Suramericanas");
			partidoOP.setC1(2.3);
			partidoOP.setC2(3.5);
			partidoOP.setcX(3.4);
			
			partidoPIO.setEquipoLocal("Brasil");
			partidoPIO.setEquipoVisitante("Uruguay");
			
			pyck.setEstado(EstadoPyck.POR_DEFINIR);
			pyck.setPyck(ResultadoPartidoBO.VISITANTE);
			pyck.setStake(1);
			pyck.setPartido(partidoOP);
			
			seg.guardarApuesta(partidoOP, partidoPIO, pyck);
			
			System.out.println("Apuesta nuevo juego");
			
			
			List<PyckBO> listResultados = new ArrayList<PyckBO>();
			
			pyck.setAcierto(false);
			pyck.setEstado(EstadoPyck.FINALIZADO);
			listResultados.add(pyck);
			
			seg.guardarResultadosPycks(listResultados);
			
			
		} catch (XLSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			//CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck
			CurrentPOddsPortal partidoOP = new CurrentPOddsPortal();
			PartidoPyckioBO partidoPIO = new PartidoPyckioBO(); 
			PyckBO pyck = new PyckBO();
			
			partidoOP.setEquipos("Barcelona - Villareal");
			partidoOP.setFecha("20160326 10:00");
			partidoOP.setCountry("España");
			partidoOP.setLeague("La Liga");
			partidoOP.setC1(2.0);
			partidoOP.setC2(3.8);
			partidoOP.setcX(3.1);
			
			partidoPIO.setEquipoLocal("Barcelona");
			partidoPIO.setEquipoVisitante("Villareal");
			
			pyck.setEstado(EstadoPyck.POR_DEFINIR);
			pyck.setPyck(ResultadoPartidoBO.VISITANTE);
			pyck.setStake(2);
			pyck.setPartido(partidoOP);
			
			seg.guardarApuesta(partidoOP, partidoPIO, pyck);
			
			System.out.println("Apuesta nuevo juego");
			
			
			List<PyckBO> listResultados = new ArrayList<PyckBO>();
			
			pyck.setAcierto(true);
			pyck.setEstado(EstadoPyck.FINALIZADO);
			listResultados.add(pyck);
			
			seg.guardarResultadosPycks(listResultados);
			
			
		} catch (XLSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	private void imprimirStatus(ISeguimientoPyckPersistencia seg)  {
		
		try {
			System.out.println("Rendimiento :: " + seg.getRendimientoAcumulado());
		
			List<PyckBO> listPendientes = seg.getPyckPorDefinir();
			
			if(listPendientes != null) {
				System.out.println("Cant Pycks Pendientes :: " + listPendientes.size());
				
				for(PyckBO p : listPendientes) {
					System.out.println(p);
				}
			} else {
				System.out.println("Pendientes NULL" );
			}
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println(" ---  ");
		System.out.println(" ---  ");
		System.out.println("   ");
	}
	
	
	
	
	
	
}
