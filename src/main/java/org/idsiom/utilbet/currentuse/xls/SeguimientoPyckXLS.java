package org.idsiom.utilbet.currentuse.xls;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.idsiom.utilbet.currentuse.ISeguimientoPyckPersistencia;
import org.idsiom.utilbet.currentuse.bo.CurrentPOddsPortal;
import org.idsiom.utilbet.currentuse.bo.PartidoPyckioBO;
import org.idsiom.utilbet.currentuse.bo.PyckBO;


import org.idsiom.utilbet.currentuse.bo.ResultadoPartidoBO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import static org.idsiom.utilbet.currentuse.constantes.ConstantesCurrent.RUTA_ARCHIVO;

public class SeguimientoPyckXLS implements ISeguimientoPyckPersistencia {

	public void guardarApuesta(CurrentPOddsPortal partidoOP, PartidoPyckioBO partidoPIO, PyckBO pyck) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * http://www.avajava.com/tutorials/lessons/how-do-i-read-from-an-excel-file-using-poi.html
	 * @throws IOException 
	 */
	public List<PyckBO> getPyckPorDefinir() throws IOException {
		List<PyckBO> result = new ArrayList<PyckBO>();
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(RUTA_ARCHIVO + "\\Rendimiento.xls");
		} catch (FileNotFoundException e) {
			return result;
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook.getSheet("Seguimiento");
		
		System.out.println("worksheet = " + worksheet);
		
		
		
		HSSFRow row = worksheet.getRow(1);
		HSSFCell celdaF2 = row.getCell(convert('g'));
		
		// Buscar en F2, la cantidad total de Pycks
		Integer cantidadPycks = ((Double)celdaF2.getNumericCellValue()).intValue();
		
		String estadoPyck;
		String resultBuscado;
		PyckBO bo;
		ResultadoPartidoBO resulP;
		CurrentPOddsPortal parOP;
		HSSFRow rowPicks;
		int stake;
		String gameOP;
		String country;
		String liga;
		String fecha;
		
		// Leer el Excel que contiene los juegos pendientes
		// Proceder a leer cada registro
		for(int i = 0; i < cantidadPycks; i++) {
			rowPicks = worksheet.getRow(4+i);
			
			estadoPyck = rowPicks.getCell(convert('f')).getStringCellValue();
			
			// Si el registro está por Definir
			if(estadoPyck.equals("PDF")) {
				
				resultBuscado = rowPicks.getCell(convert('e')).getStringCellValue();
			    stake = ((Double) rowPicks.getCell(convert('c')).getNumericCellValue()).intValue();
			    gameOP = rowPicks.getCell(convert('i')).getStringCellValue();
			    country = rowPicks.getCell(convert('h')).getStringCellValue();
				liga =	rowPicks.getCell(convert('k')).getStringCellValue();	
				fecha = rowPicks.getCell(convert('g')).getStringCellValue();
				
				// Crear un objeto PyckBO equivalente
				bo = new PyckBO();
				parOP = new CurrentPOddsPortal();
				
				parOP.setEquipos(gameOP);
				parOP.setCountry(country);
				parOP.setLeague(liga);
				parOP.setFecha(fecha);
				
				resulP = ResultadoPartidoBO.getObjFromAbrev(resultBuscado);
				
				bo.setAcierto(null);
				bo.setPyck(resulP);
				bo.setStake(stake);
				bo.setPartido(parOP);
				
			     // Agregarlo a la lista
				result.add(bo);
			}
		}
		
		// Devolver la lista
		return result;
	}

	private int convert(char ch) {
		int pos = ch - 'a';
		return pos;
	}
	
	public double getRendimientoAcumulado() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void guardarResultadosPycks(List<PyckBO> pycks) {
		// TODO Auto-generated method stub
		
	}

}
